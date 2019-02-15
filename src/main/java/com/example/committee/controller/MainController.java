package com.example.committee.controller;

import com.example.committee.dao.RoleDAO;
import com.example.committee.dao.UserDAO;
import com.example.committee.domain.AppRole;
import com.example.committee.domain.AppUser;
import com.example.committee.utils.FormCommand;
import com.example.committee.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcomePage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "adminPage";
    }

    @RequestMapping(value = "/usersListPage", method = RequestMethod.GET)
    public String userListPage(@ModelAttribute("formCommand") FormCommand formCommand, Model model) {
        List<AppUser> usersList = userDAO.getUsersList();
        model.addAttribute("usersList", usersList);
        List<AppRole> rolesList = roleDAO.getAllRoles();
        model.addAttribute("rolesList", rolesList);

        return "usersListPage";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestParam(name = "userLogin") String userLogin,
                          @RequestParam(name = "userPassword") String userPassword,
                          @ModelAttribute("formCommand") FormCommand formCommand) {
        AppUser user = new AppUser(userLogin, new BCryptPasswordEncoder().encode(userPassword), false);
        userDAO.addUser(user);

        List<Long> roleIdList = formCommand.getMultiCheckboxSelectedValues();
        Long userId = userDAO.getUserByLogin(userLogin).getUserId();
        roleDAO.setRolesToUser(userId, roleIdList);

        return "redirect:usersListPage";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        return "loginPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
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
