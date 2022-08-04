package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserServiceImp userService;
    private RoleServiceImp roleService;

    @Autowired
    public void setUserService(UserServiceImp userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleServiceImp roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String userList(Model model, Principal principal) {
        User admin = userService.getUserByUsername(principal.getName());
        model.addAttribute("adminUsername", admin.getUsername());
        model.addAttribute("adminRoles", admin.getRoleName());
        model.addAttribute("userList", userService.allUsers());
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @GetMapping("/admin-page")
    public String adminPage(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "about-admin";
    }

    @PostMapping("/user-create")
    public String createUser(User user, @RequestParam(value = "role_id") String role) {
        user.setRoles(roleService.findRolesByName(role));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/user-delete/{id}")
    public String removeUser(@PathVariable("id") Long Id) {
        userService.deleteUser(Id);
        return "redirect:/admin";
    }

    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "role_id") String role) {
        user.setRoles(roleService.findRolesByName(role));
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
