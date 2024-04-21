package com.exampl.controller;

import com.exampl.domain.Admin;

import com.exampl.domain.User;
import com.exampl.service.AdminService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Component
@RequestMapping("/admin")
public class AdminController {

    AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }




    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword, Model model) {

        Admin admin = adminService.getCurrentAdmin();
        model.addAttribute("currentAdmin", admin);

        // Проверяем, определен ли пользователь
        if (admin == null) {
            model.addAttribute("error", "Пользователь не определен.");
            return "my-accountAdmin.html";
        }

        // Проверяем соответствие текущего пароля
        if (!adminService.checkPassword(admin, oldPassword)) {
            model.addAttribute("error", "Текущий пароль не совпадает.");
            return "my-accountAdmin.html";
        }

        // Проверяем валидность нового пароля
        if (!adminService.validatePassword(newPassword)) {
            model.addAttribute("error", "Некорректный новый пароль.");
            return "my-accountAdmin.html";
        }

        // Если все проверки пройдены успешно, меняем пароль
        if (adminService.changePassword(admin, oldPassword, newPassword)) {
            model.addAttribute("message", "Пароль успешно обновлен.");
        } else {
            model.addAttribute("error", "Ошибка при обновлении пароля. Повторите попытку.");
        }

        return "my-accountAdmin.html";
    }


    @GetMapping("/changePassword")
    public String changePassword (Model model) {
        /*model.addAttribute("title", "Главная страница");*/
        return "ChangePasswordAdmin.html";
    }



















    @GetMapping("/registration")
    public String registrationAdmin( Model model) {
        model.addAttribute("admin", new Admin());


        return "created";
    }

    @GetMapping("/adminHome")
    public String registration(Model model) {
        return "indexAdmin(creatStock).html";
    }


    @PostMapping("/registration")
    public String createAdmin(@ModelAttribute("admin") Admin admin, Model model) {

        if (adminService.loginExist(admin.getEmail())) {
            model.addAttribute("error", "error");
            return "redirect:/404.html";
        }

        model.addAttribute("admin", admin);

        Authentication authentication = new UsernamePasswordAuthenticationToken(admin.getEmail(), admin.getPasswordAdmin());

        // Устанавливаем аутентификацию для текущего потока выполнения
        SecurityContextHolder.getContext().setAuthentication(authentication);
        adminService.createAdmin(admin);
        return "redirect:/adminHome";
    }

    @GetMapping("/my-accountAdmin")
    public String updateAdmin(Model model) {
        Admin currentAdmin = adminService.getCurrentAdmin();
        model.addAttribute("currentAdmin", currentAdmin);
        return "my-accountAdmin.html";
    }

    @PostMapping("/updateAdminData")
    public String updateAdminData(@ModelAttribute Admin updatedAdmin, Model model) {
        Admin currentAdmin = adminService.getCurrentAdmin();


      /*  if (currentUser == null) {
            // Пользователь не авторизован, перенаправьте его на страницу входа или другую страницу
            return "redirect:/login"; // Замените на путь к странице входа
        }

        if (updatedUser.getId() != currentUser.getId()) {
            // Нельзя обновлять данные другого пользователя
            return "redirect:/my-account"; // Замените на путь к странице аккаунта
        }*/

        Admin updatedAdminResult = adminService.updateAdmin(updatedAdmin);
        model.addAttribute("success", true);

        if (updatedAdminResult != null) {
            // Обновление прошло успешно
            model.addAttribute("message", "Данные успешно обновлены.");
        } else {
            // Обновление не удалось, возможно, пользователь не найден
            model.addAttribute("error", "Не удалось обновить данные пользователя.");
        }

        return "my-accountAdmin.html"; // Замените на путь к странице аккаунта
    }



    @GetMapping("/organization-names")
    public String getAllOrganizationNames(Model model) {
        List<String> organizationNames = adminService.getAllOrganizationNames();
        model.addAttribute("organizationNames", organizationNames);
        return "shop.html"; // Имя Thymeleaf-шаблона
    }
}
