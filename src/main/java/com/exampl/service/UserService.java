package com.exampl.service;


import com.exampl.domain.User;
import com.exampl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean checkPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPasswordUser());
    }

        public boolean validatePassword(String password) {
        // Реализуйте логику проверки пароля, если требуется
        return password.length() >= 3;
    }

    public boolean changePassword(User user, String currentPassword, String newPassword) {
        // Проверяем корректность нового пароля и совпадение текущего пароля
        if (!validatePassword(newPassword) || !checkPassword(user, currentPassword)) {
            return false; // Некорректный новый пароль или текущий пароль не совпадает
        }

        try {
            // Шифруем новый пароль перед сохранением
            String encryptedNewPassword = passwordEncoder.encode(newPassword);
            user.setPasswordUser(encryptedNewPassword);
            userRepository.save(user);
            return true; // Пароль успешно обновлен
        } catch (Exception e) {
            e.printStackTrace(); // Вывести информацию об ошибке в консоль
            return false; // Ошибка при обновлении пароля
        }
    }




    public User createUser(User user) {
        user.setPasswordUser(passwordEncoder.encode(user.getPasswordUser()));
        return userRepository.save(user);
    }

    public boolean loginExist(String login) {
        boolean b = userRepository.findUserByEmail(login) != null;
        return b;
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String email = authentication.getName();
            return userRepository.findUserByEmail(email);
        }
        return null;
    }

    public User updateUser(User updatedUser) {
        User currentUser = getCurrentUser();

        if (currentUser == null) {
            // Пользователь не авторизован
            return null;
        }
        // Проверяем, изменились ли данные
        if (!currentUser.getFio().equals(updatedUser.getFio())) {
            currentUser.setFio(updatedUser.getFio());
        }
        if (!currentUser.getEmail().equals(updatedUser.getEmail())) {
            currentUser.setEmail(updatedUser.getEmail());
        }
        if (!currentUser.getTel().equals(updatedUser.getTel())) {
            currentUser.setTel(updatedUser.getTel());
        }
        if (!currentUser.getAdress().equals(updatedUser.getAdress())) {
            currentUser.setAdress(updatedUser.getAdress());
        }
        // Сохранение обновленных данных
        return userRepository.save(currentUser);
    }


    public void deleteUser() {
        User currentUser = getCurrentUser();

        if (currentUser != null) {
            userRepository.deleteById(currentUser.getId());
        }
    }

}

   /* @PostMapping("/user/delete")
    public String deleteUser() {
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            userService.deleteUser(currentUser.getId());
            // Выход пользователя из системы (если используется аутентификация)
            // Произведите дополнительные действия, такие как очистка сеанса и другие действия безопасности

            return "redirect:/logout"; // Может потребоваться разлогинить пользователя
        }*/
/*    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User currentUser = null;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



   *//* public boolean loginExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }*//*

    public User getCurrentUser() {
        if (this.currentUser != null)
            return this.currentUser;
        else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.findUserByEmail(email);
            this.currentUser = user;
            return user;
        }
    }
    public Integer getCurrentUserId() {
        if (currentUser != null)
            return currentUser.getId();
        return null;
    }



    public User updateUser(User user) {
        // Поиск пользователя по ID или по email, в зависимости от того, что вы используете
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser == null) {
            // Если пользователя не существует, вернуть null или сгенерировать ошибку
            return null;
        }

        // Проверка, изменились ли данные
        if (!user.getFio().equals(existingUser.getFio())) {
            existingUser.setFio(user.getFio());
        }
        if (!user.getEmail().equals(existingUser.getEmail())) {
            existingUser.setEmail(user.getEmail());
        }
        if (!user.getTel().equals(existingUser.getTel())) {
            existingUser.setTel(user.getTel());
        }
        if (!user.getAdress().equals(existingUser.getAdress())) {
            existingUser.setAdress(user.getAdress());
        }

        // Сохранение обновленных данных
        User updatedUser = userRepository.save(existingUser);
        return updatedUser;
    }*/