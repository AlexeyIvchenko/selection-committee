package ru.military.committee.controller;

import ru.military.committee.domain.employee.AppRole;
import ru.military.committee.domain.employee.AppUser;
import ru.military.committee.service.AppRoleService;
import ru.military.committee.service.AppUserService;
import ru.military.committee.utils.ChangePasswordHelper;
import ru.military.committee.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppRoleService appRoleService;

    @GetMapping(value = "/user/info")
    public String userInfoPage(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser user = appUserService.getUserByLogin(loginedUser.getUsername());

        String userInfo = new StringBuilder().append(user.getUserSurname()).append(" ").append(user.getUserName()).append(" ").append(user.getUserSecondName()).toString();
        model.addAttribute("fio", userInfo);
        model.addAttribute("login", user.getUserLogin());

        model.addAttribute("passwordChanged");

        return "userInfo";
    }

    @GetMapping(value = {"/admin/usersListPage"})
    public String getUsersListPage(@ModelAttribute("userForm") AppUser user, Model model) {
        List<AppUser> usersList = appUserService.getAllUsers();
        model.addAttribute("usersList", usersList);
        List<AppRole> rolesList = appRoleService.getAllRoles();
        model.addAttribute("rolesList", rolesList);

        return "usersListPage";
    }

    @GetMapping(value = "/admin/deleteUser")
    public String deleteUser(@RequestParam(name = "userId") Long userId) {
        appUserService.deleteUserById(userId);
        return "redirect:/admin/usersListPage";

    }

    @PostMapping(value = "/admin/addUser")
    public String addUser(@ModelAttribute("userForm") AppUser user) {
        user.setUserPassword(new BCryptPasswordEncoder().encode(user.getUserPassword()));
        appUserService.addUser(user);

        return "redirect:/admin/usersListPage";
    }

    @GetMapping(value = "/admin/editUserPage/{userId}")
    public String getEditUserPage(@PathVariable("userId") Long userId, Model model) {
        AppUser user = appUserService.getUserById(userId);
        model.addAttribute("appUser", user);
        model.addAttribute("changePassportHelper", new ChangePasswordHelper());
        model.addAttribute("passwordChanged");

        List<AppUser> usersList = appUserService.getAllUsers();
        model.addAttribute("usersList", usersList);
        List<AppRole> rolesList = appRoleService.getAllRoles();
        model.addAttribute("rolesList", rolesList);

        return "editUserPage";
    }

    @PostMapping("/admin/editUser/{userId}")
    public String editUserInfo(@PathVariable("userId") Long userId, @Valid AppUser editedUser) {
        AppUser userFromBase = appUserService.getUserById(userId);
        editedUser.setUserId(userFromBase.getUserId());
        editedUser.setUserPassword(userFromBase.getUserPassword());
        appUserService.addUser(editedUser);

        if (userFromBase.getRoles().contains(appRoleService.getRoleById(Long.valueOf(1)).get())) {
            return "redirect:/login";
        }
        return "redirect:/admin/usersListPage";
    }

    @PostMapping("/admin/dropPassword/{userId}")
    public String dropPassword(@PathVariable("userId") Long userId, @RequestParam("newPassword") String newPassword, RedirectAttributes redirectAttributes) {
        AppUser userFromBase = appUserService.getUserById(userId);
        userFromBase.setUserPassword(new BCryptPasswordEncoder().encode(newPassword));
        appUserService.addUser(userFromBase);
        redirectAttributes.addFlashAttribute("passwordChanged", "Пароль успешно изменен");

        return "redirect:/admin/editUserPage/" + userId;
    }

    @PostMapping("/user/editPassword")
    public String editPassport(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, RedirectAttributes redirectAttributes, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        AppUser user = appUserService.getUserByLogin(loginedUser.getUsername());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(oldPassword, user.getUserPassword())) {
            user.setUserPassword(encoder.encode(newPassword));
            appUserService.addUser(user);
            System.out.println("Пароль успешно изменен");
            redirectAttributes.addFlashAttribute("passwordChanged", "Пароль успешно изменен");
        } else {
            redirectAttributes.addFlashAttribute("passwordChanged", "Старый пароль введен неверно!");
            System.out.println("Старый пароль введен неверно!");
        }
        return "redirect:/user/info";
    }

    @GetMapping(value = {"/", "/login"})

    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping(value = "/logoutSuccessful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @GetMapping(value = "/403")
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }
        return "403Page";
    }
}
