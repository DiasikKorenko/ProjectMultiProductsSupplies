package com.exampl.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_id_seq")
    @SequenceGenerator(name = "admin_id_seq", sequenceName = "admins_id_seq", allocationSize = 1)
    private int id;
    @Column(name = "fio")
    private String fioAdmin;
    @Column(name = "name_organization")
    private String nameOrganizationAdmin;
    @Column(name = "email")
    private String email;
    @Column(name = "password_admin")
    private String passwordAdmin;
    @Column(name = "adress")
    private String adressAdmin;
    @Column(name = "role")
    private String role = "ADMIN";
}
