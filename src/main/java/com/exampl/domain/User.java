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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "fio")
    private String fio;
    @Column(name = "email")
    private String email;
    @Column(name = "tel")
    private String tel;
    @Column(name = "adress")
    private String adress;
    @Column(name = "password_user")
    private String passwordUser;
    /*    @Column(name = "nameOrganization")
        private String nameOrganization;*/
    @Column(name = "role")
    private String role = "USER";


}
