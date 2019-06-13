package ru.military.committee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.military.committee.domain.personal.Recruit;
import ru.military.committee.domain.request.Faculty;
import ru.military.committee.domain.request.Request;
import ru.military.committee.domain.request.RequestStatus;
import ru.military.committee.domain.request.Specialty;
import ru.military.committee.service.FacultyService;
import ru.military.committee.service.RequestService;
import ru.military.committee.service.RequestStatusService;
import ru.military.committee.service.SpecialtyService;
import ru.military.committee.utils.CascadingSelectHelper;
import ru.military.committee.utils.DateWorker;
import ru.military.committee.utils.RequestAndScore;
import ru.military.committee.utils.Sorter;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс отвечает за формирование результатов приемной комиссии.
 */
@Controller
public class EnrollmentController {
    @Autowired
    private RequestService requestService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private RequestStatusService requestStatusService;
    @Autowired
    private FacultyService facultyService;

    /**
     * Формирует данные модели и возвращает имя html-страницы с формированием документации.
     *
     * @param model - модель хранит информацию, которая отображается в представлении (html-странице).
     * @return - имя html-страницы с формированием документации.
     */
    @GetMapping(value = "/user/enrollmentPage")
    public String getEnrollmentPage(Model model) {
        List<Request> waitingRequsts = requestService.getRequestsByStatusIdAndRequestYear((byte) 0, DateWorker.getFirstDateInCurrentYear());
        model.addAttribute("waitingRequsts", waitingRequsts);

        List<Request> requests = requestService.getRequestsByStatusIdAndRequestYear((byte) 1, DateWorker.getFirstDateInCurrentYear());
        List<RequestAndScore> requestAndScoreList = transformRequestsListToRequestAndScoreList(requests);
        model.addAttribute("preliminaryList", requestAndScoreList);

        List<Faculty> facultyList = facultyService.getAllFaculties();
        model.addAttribute("facultyList", facultyList);

        if (facultyList.size() != 0) {
            List<Specialty> firstFacultySpecialtiesList = specialtyService.getAllSpecialtiesInFaculty(facultyList.get(0));
            model.addAttribute("firstFacultySpecialtiesList", firstFacultySpecialtiesList);
        }
        model.addAttribute("cascadingSelectHelper", new CascadingSelectHelper());

        model.addAttribute("validYears", DateWorker.getValidYears());

        return "enrollmentPage";
    }

    //Возвращает список специальностей по выбранному факультету.
    @RequestMapping(value = "/user/specialtiesNew", method = RequestMethod.GET)
    public @ResponseBody
    List<Specialty> findSpecialtiesByFaculty(@RequestParam(value = "facultyId", required = true) short facultyId) {
        Faculty faculty = facultyService.getFacultyById(facultyId);
        List<Specialty> specialtiesList = specialtyService.getAllSpecialtiesInFaculty(faculty);
        return specialtiesList;
    }

    /**
     * Переводит заявления абитуриентов в статус "На рассмотрении".
     *
     * @return - перенаправляет на html-страницу с формированием документации.
     */
    @GetMapping(value = "/user/clearPreliminaryLists")
    public String clearPreliminaryLists() {
        List<Request> requests = requestService.getRequestsByRequestYear(DateWorker.getFirstDateInCurrentYear());
        for (int i = 0; i < requests.size(); i++) {
            Request request = requests.get(i);
            markRequestWithStatus(request, (byte) 0);
        }

        return "redirect:/user/enrollmentPage";
    }

    /**
     * Обрабатывает заявления абитуриентов, формируя тем самым куонкурсные списки.
     *
     * @return - перенаправляет на html-страницу с формированием документации.
     */
    @GetMapping(value = "/user/getPreliminaryLists")
    public String getPreliminaryLists() {
        List<Specialty> specialtiesList = specialtyService.getAllSpecialties();
        List<ArrayList<RequestAndScore>> preliminaryLists = getEmptyPreliminaryLists(specialtiesList);

        List<Request> requests = requestService.getRequestsByPriorityAndRequestYear((short) 1, DateWorker.getFirstDateInCurrentYear());
        List<RequestAndScore> requestAndScoreList = transformRequestsListToRequestAndScoreList(requests);
        Sorter.sortRequestAndScoreListByPriorityAndScore(requestAndScoreList);

        for (int i = 0; i < requestAndScoreList.size(); i++) {
            int specialtyId = requestAndScoreList.get(i).getRequest().getSpecialty().getSpecialtyId();

            List<RequestAndScore> preliminarySpecialtyList = preliminaryLists.get(specialtyId - 1);//TODO: 5 заменить на значение из базы по количеству мест в специальности
            RequestAndScore currentReqAndScore = requestAndScoreList.get(i);

            if (preliminarySpecialtyList.size() < 5) {
                preliminarySpecialtyList.add(currentReqAndScore);
                Sorter.sortRequestAndScoreListByScore(preliminarySpecialtyList);
            } else {
                //Если места на этой специальности заняты, то проверяем вытеснится ли нижний реквест новым (по количеству баллов)
                RequestAndScore lowestReqAndScore = preliminarySpecialtyList.get(preliminarySpecialtyList.size() - 1);
                if (lowestReqAndScore.getScore() <= currentReqAndScore.getScore()) {
                    //Баллы без учета ФИЗО (Требуются, если общие баллы у двух абитуриентов равны)
                    int lowestExamScore = lowestReqAndScore.getRequest().getRecruit().sumExamOrCertificateScoreByFaculty(lowestReqAndScore.getRequest().getSpecialty().getFaculty());
                    int currentExamScore = currentReqAndScore.getRequest().getRecruit().sumExamOrCertificateScoreByFaculty((currentReqAndScore.getRequest().getSpecialty().getFaculty()));

                    if ((lowestReqAndScore.getScore() < currentReqAndScore.getScore()) || (lowestExamScore < currentExamScore)) {
                        //Ставим вытесненному реквесту в базе статус "Не рекомендован к зачислению" (по конкретно этому приоритету!!!)
                        Request rejectRequest = lowestReqAndScore.getRequest();
                        markRequestWithStatus(rejectRequest, (byte) 3);

                        //Заменяем реквест с меньшим количеством баллов на реквест с большим количеством баллов
                        preliminarySpecialtyList.remove(preliminarySpecialtyList.size() - 1);
                        preliminarySpecialtyList.add(currentReqAndScore);
                        Sorter.sortRequestAndScoreListByScore(preliminarySpecialtyList);

                        //Добавляем в конец списка нераспределенных реквестов реквест абитуриента, но с приоритетом ниже на один (но не ниже 3)
                        addLowerPriorityRequestToList(rejectRequest, requestAndScoreList);
                    }
                } else {
                    //Если места на этой специальности заняты, но новый реквест не вытесняет нижний, то ставим новому реквесту статус "Не рекомендован к зачислению" (по конкретно этому приоритету!!!)
                    Request currentRequest = currentReqAndScore.getRequest();
                    markRequestWithStatus(currentRequest, (byte) 3);

                    //Добавляем в конец списка нераспределенных реквестов реквест абитуриента, но с приоритетом ниже на один (но не ниже 3)
                    addLowerPriorityRequestToList(currentRequest, requestAndScoreList);
                }
            }
        }

        //"Победившие" реквесты отмечаем как рекомендованные
        for (int i = 0; i < preliminaryLists.size(); i++) {
            for (int j = 0; j < preliminaryLists.get(i).size(); j++) {
                Request request = preliminaryLists.get(i).get(j).getRequest();
                if (request.getPriority() == 1) {
                    Request secondPriorityRequest = requestService.getRequestByRecruitIdAndPriority(request.getRecruit().getRecruitId(), (short) 2);
                    Request thirdPriorityRequest = requestService.getRequestByRecruitIdAndPriority(request.getRecruit().getRecruitId(), (short) 3);
                    markRequestWithStatus(secondPriorityRequest, (byte) 2);
                    markRequestWithStatus(thirdPriorityRequest, (byte) 2);
                } else {
                    if (request.getPriority() == 2) {
                        Request thirdPriorityRequest = requestService.getRequestByRecruitIdAndPriority(request.getRecruit().getRecruitId(), (short) 3);
                        markRequestWithStatus(thirdPriorityRequest, (byte) 2);
                    }
                }
                markRequestWithStatus(request, (byte) 1);
            }
        }
        return "redirect:/user/enrollmentPage";
    }

    /**
     * Добавляет в список заявление приоритетом ниже.
     *
     * @param higherPriorityRequest - объект заявления абитуриента.
     * @param requestAndScoreList   - список объектов, состоящих из заявления абитуриента и суммарного количества баллов по требуемым предметам.
     */
    private void addLowerPriorityRequestToList(Request higherPriorityRequest, List<RequestAndScore> requestAndScoreList) {
        if (higherPriorityRequest.getPriority() < 3) {
            Request lowerPriorityRequest = requestService.getRequestByRecruitIdAndPriority(higherPriorityRequest.getRecruit().getRecruitId(), (short) (higherPriorityRequest.getPriority() + 1));
            if (lowerPriorityRequest != null) {
                requestAndScoreList.add(transformRequestToRequestAndScore(lowerPriorityRequest));
            }
        }
    }

    /**
     * Устанавливает заданнный статус заявлению абитуриента.
     *
     * @param request  - заявление абитуриента.
     * @param statusId - статус заявления.
     */
    private void markRequestWithStatus(Request request, byte statusId) {
        RequestStatus requestStatus = requestStatusService.getRequestStatusById(statusId);
        request.setRequestStatus(requestStatus);
        requestService.addRequest(request);
    }

    /**
     * Формирование пустых конкурсных списков
     *
     * @param specialtiesList - список специальностей.
     * @return - пустые конкурсные списки.
     */
    private List<ArrayList<RequestAndScore>> getEmptyPreliminaryLists(List<Specialty> specialtiesList) {
        List<ArrayList<RequestAndScore>> preliminaryLists = new ArrayList<>();

        for (int i = 0; i < specialtiesList.size(); i++) {
            preliminaryLists.add(new ArrayList<>());
        }
        return preliminaryLists;
    }

    /**
     * Формирует список объектов, состоящих из заявления и количества баллов по предметам, необходимым для подачи заявления на специальность.
     *
     * @param requestsList - список заявлений абитуриентов.
     * @return - список объектов, состоящих из заявления и баллов абитуриента.
     */
    private List<RequestAndScore> transformRequestsListToRequestAndScoreList(List<Request> requestsList) {
        List<RequestAndScore> requestAndScoreList = new ArrayList<>();
        for (Request aRequestsList : requestsList) {
            requestAndScoreList.add(transformRequestToRequestAndScore(aRequestsList));
        }
        return requestAndScoreList;
    }

    /**
     * Формирует объект состоящий из заявления и количества баллов абитуриента.
     *
     * @param request - заявление абитурента.
     * @return - объект, состоящий из заявления и количества баллов абитуриента.
     */
    private RequestAndScore transformRequestToRequestAndScore(Request request) {
        Recruit recruit = request.getRecruit();
        int recruitScore = recruit.sumTotalRecruitScore(request.getSpecialty().getFaculty());
        return new RequestAndScore(request, recruitScore);
    }
}
