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
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "delivery_id_seq")
    @SequenceGenerator(name = "delivery_id_seq", sequenceName = "delivery_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "route")
    private String route;

    @Column(name = "expenses")
    private double expenses;

    @Column(name = "id_order")
    private int orderId;

    @Column(name = "id_admin")
    private int adminId;

}
