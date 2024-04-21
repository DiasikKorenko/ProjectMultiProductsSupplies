package com.exampl.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "orders_product")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_product_id_seq")
    @SequenceGenerator(name = "orders_product_id_seq", sequenceName = "orders_product_id_seq", allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_order")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "id_product")

    private Product product;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_cost")
    private int sum;

    @Column(name = "manufacturer_id")
    private int manufacturerId;

    @Column(name = "date")
    private Timestamp date;

    @PrePersist
    public void prePersist() {
        if (product != null) {
            sum = product.getTotalPrice() * quantity;
        }
    }
}
