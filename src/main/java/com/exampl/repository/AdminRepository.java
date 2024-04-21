package com.exampl.repository;


import com.exampl.domain.Admin;
import com.exampl.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findAdminByEmail(String emailAdmin);



}
