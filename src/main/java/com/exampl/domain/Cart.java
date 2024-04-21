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
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_id_seq")
    @SequenceGenerator(name = "cart_id_seq", sequenceName = "cart_id_seq", allocationSize = 1)
    private int id;


    @Column(name = "number")
    private String number;

    @Column(name = "validity")
    private String validity;

    @Column(name = "cvv")
    private int cvv;

    @Column(name = "id_user")
    private int userId;

    @Column(name = "sum")
    private int sumCart= 10000;
}
