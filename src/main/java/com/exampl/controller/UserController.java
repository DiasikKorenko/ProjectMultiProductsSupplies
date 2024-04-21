package com.exampl.controller;


import com.exampl.DTO.ChangePasswordRequest;
import com.exampl.domain.User;

import com.exampl.service.PasswordEncryptionService;
import com.exampl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Component
@RequestMapping("/user")
public class UserController {
    UserService userService;

    PasswordEncryptionService passwordEncryptionService;

    @Autowired
    public UserController(UserService userService, PasswordEncryptionService passwordEncryptionService) {
        this.userService = userService;
        this.passwordEncryptionService = passwordEncryptionService;
    }


    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword, Model model) {

        User user = userService.getCurrentUser();
        model.addAttribute("currentUser", user);

        // Проверяем, определен ли пользователь
        if (user == null) {
            model.addAttribute("error", "Пользователь не определен.");
            return "my-account-2.html";
        }

        // Проверяем соответствие текущего пароля
        if (!userService.checkPassword(user, oldPassword)) {
            model.addAttribute("error", "Текущий пароль не совпадает.");
            return "my-account-2.html";
        }

        // Проверяем валидность нового пароля
        if (!userService.validatePassword(newPassword)) {
            model.addAttribute("error", "Некорректный новый пароль.");
            return "my-account-2.html";
        }

        // Если все проверки пройдены успешно, меняем пароль
        if (userService.changePassword(user, oldPassword, newPassword)) {
            model.addAttribute("message", "Пароль успешно обновлен.");
        } else {
            model.addAttribute("error", "Ошибка при обновлении пароля. Повторите попытку.");
        }

        return "my-account-2.html";
    }




    @GetMapping("/changePassword")
    public String changePassword (Model model) {
        /*model.addAttribute("title", "Главная страница");*/
         return "ChangePasswordUser.html";
    }























    @GetMapping("/registration")
    public String registration(Model model) {
        /*model.addAttribute("title", "Главная страница");*/
        model.addAttribute("user", new User());
        return "created";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") User user, Model model) {

        if (userService.loginExist(user.getEmail())) {
            model.addAttribute("error", "Ошибка регистрации!!Такая почта уже существует. Пройдите повторную регистрацию!");
            return "404.html";
        }

        model.addAttribute("user", user);



        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPasswordUser());

        // Устанавливаем аутентификацию для текущего потока выполнения
        SecurityContextHolder.getContext().setAuthentication(authentication);

        userService.createUser(user);
        return "loginGood.html";
    }


    @GetMapping("/my-account-2")
    public String updateUser(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return "my-account-2";
    }

    @PostMapping("/updateUserData")
    public String updateUserData(@ModelAttribute User updatedUser, Model model) {
        User currentUser = userService.getCurrentUser();

        User updatedUserResult = userService.updateUser(updatedUser);
        model.addAttribute("success", true);

        if (updatedUserResult != null) {
            // Обновление прошло успешно
            model.addAttribute("message", "Данные успешно обновлены.");
        } else {
            // Обновление не удалось, возможно, пользователь не найден
            model.addAttribute("error", "Не удалось обновить данные пользователя.");
        }

        return "my-account-2"; // Замените на путь к странице аккаунта
    }

    @GetMapping("/delete")
    public String deleteUser() {
            userService.deleteUser();
            return "redirect:/logout";
        }
}


/*  if (currentUser == null) {
            // Пользователь не авторизован, перенаправьте его на страницу входа или другую страницу
            return "redirect:/login"; // Замените на путь к странице входа
        }

        if (updatedUser.getId() != currentUser.getId()) {
            // Нельзя обновлять данные другого пользователя
            return "redirect:/my-account"; // Замените на путь к странице аккаунта
        }*/