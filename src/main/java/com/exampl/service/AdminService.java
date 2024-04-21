package com.exampl.service;

import com.exampl.domain.Admin;
import com.exampl.domain.User;
import com.exampl.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean checkPassword(Admin admin, String password) {
        return passwordEncoder.matches(password, admin.getPasswordAdmin());
    }

    public boolean validatePassword(String password) {
        // Реализуйте логику проверки пароля, если требуется
        return password.length() >= 3;
    }

    public boolean changePassword(Admin admin, String currentPassword, String newPassword) {
        // Проверяем корректность нового пароля и совпадение текущего пароля
        if (!validatePassword(newPassword) || !checkPassword(admin, currentPassword)) {
            return false; // Некорректный новый пароль или текущий пароль не совпадает
        }

        try {
            // Шифруем новый пароль перед сохранением
            String encryptedNewPassword = passwordEncoder.encode(newPassword);
            admin.setPasswordAdmin(encryptedNewPassword);
            adminRepository.save(admin);
            return true; // Пароль успешно обновлен
        } catch (Exception e) {
            e.printStackTrace(); // Вывести информацию об ошибке в консоль
            return false; // Ошибка при обновлении пароля
        }
    }



































    public Admin createAdmin(Admin admin) {
        admin.setPasswordAdmin(passwordEncoder.encode(admin.getPasswordAdmin()));
        return adminRepository.save(admin);
    }

    public boolean loginExist(String login) {
        boolean b = adminRepository.findAdminByEmail(login) != null;
        return b;
    }

    public Admin getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String email = authentication.getName();
            return adminRepository.findAdminByEmail(email);
        }
        return null;
    }


    public Admin updateAdmin(Admin updatedAdmin) {
        Admin currentAdmin = getCurrentAdmin();

        if (currentAdmin == null) {
            // Администратор не авторизован
            return null;
        }
        // Проверяем, изменились ли данные
        if (!currentAdmin.getFioAdmin().equals(updatedAdmin.getFioAdmin())) {
            currentAdmin.setFioAdmin(updatedAdmin.getFioAdmin());
        }
        if (!currentAdmin.getEmail().equals(updatedAdmin.getEmail())) {
            currentAdmin.setEmail(updatedAdmin.getEmail());
        }
        if (!currentAdmin.getNameOrganizationAdmin().equals(updatedAdmin.getNameOrganizationAdmin())) {
            currentAdmin.setNameOrganizationAdmin(updatedAdmin.getNameOrganizationAdmin());
        }
        if (!currentAdmin.getAdressAdmin().equals(updatedAdmin.getAdressAdmin())) {
            currentAdmin.setAdressAdmin(updatedAdmin.getAdressAdmin());
        }
        // Сохранение обновленных данных
        return adminRepository.save(currentAdmin);
    }


    public List<String> getAllOrganizationNames() {
        List<Admin> admins = adminRepository.findAll();
        List<String> organizationNames = new ArrayList<>();

        for (Admin admin : admins) {
            organizationNames.add(admin.getNameOrganizationAdmin());
        }

        return organizationNames;
    }
}
