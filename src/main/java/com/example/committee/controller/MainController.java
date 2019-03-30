package com.example.committee.controller;

import com.example.committee.domain.employee.AppRole;
import com.example.committee.domain.employee.AppUser;
import com.example.committee.service.AppRoleService;
import com.example.committee.service.AppUserService;
import com.example.committee.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppRoleService appRoleService;

    @GetMapping(value = "/userInfo")
    public String userInfoPage(Model model, Principal principal) {
        if (principal == null) {
            return "login";
        }
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfo";
    }

    @GetMapping(value = "/admin")
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "adminPage";
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
        user.setOnline(false);
        appUserService.addUser(user);

        return "redirect:/admin/usersListPage";
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

    @GetMapping(value = "/main")
    public String userInfo(Model model, Principal principal) {
        return "main";
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
