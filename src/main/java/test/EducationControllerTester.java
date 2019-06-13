package test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Класс предназначен для автоматизированного тестирования графического интерфейса
 * модуля программы, связанного с результатами ЕГЭ и аттестатом абитуриента.
 */
public class EducationControllerTester {
    private static final String PATH_TO_APP = "http://localhost:8010";

    /**
     * Предварительная авторизация пользователя в системе.
     */
    @Before
    public void authorize() {
        String loginPagePath = PATH_TO_APP + "/login";
        Configuration.baseUrl = loginPagePath;

        Selenide.open(loginPagePath);
        $(By.name("username")).setValue("jeka");
        $(By.name("password")).setValue("1234");
        $("#submitBtn").click();
    }

    /**
     * Тестирование возможности добавления/редактирования результатов ЕГЭ абтуриента при вводе корректных данных.
     */
    @Test
    public void testCorrectExamData() {
        String loginPagePath = PATH_TO_APP + "/user/educationPage/1";
        Configuration.baseUrl = loginPagePath;

        Selenide.open(loginPagePath);
        $("#scoreMath").setValue("100");
        $("#scoreRusLang").setValue("100");
        $("#scorePhysics").setValue("100");
        $("#scoreForeignLang").setValue("100");
        $("#scoreHistory").setValue("100");
        $("#scoreSocial").setValue("100");
        $("#scoreLiterature").setValue("100");
        $("#examYear").setValue("2019");
        $("#submitBtn").click();
        sleep(2000);
        $("#examMessage").shouldHave(Condition.text("Данные успешно изменены!"));
    }

    /**
     * Тестирование возможности добавления/редактирования результатов ЕГЭ абтуриента при вводе некорректных данных.
     */
    @Test
    public void testIncorrectExamData() {
        String loginPagePath = PATH_TO_APP + "/user/educationPage/1";
        Configuration.baseUrl = loginPagePath;

        Selenide.open(loginPagePath);
        $("#scoreMath").setValue("500");
        $("#scoreRusLang").setValue("100");
        $("#scorePhysics").setValue("100");
        $("#scoreForeignLang").setValue("100");
        $("#scoreHistory").setValue("100");
        $("#scoreSocial").setValue("100");
        $("#scoreLiterature").setValue("100");
        $("#examYear").setValue("2019");
        $("#submitBtn").click();
        sleep(2000);
        $("#submitBtn").should(Condition.disabled);
    }
}
