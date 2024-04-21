package com.exampl.domain;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "category")
    private String category;

    @Column(name = "subcategory")
    private String subcategory;

    @Column(name = "chapter")
    private String chapter;

    @Column(name = "id_stock")
    private Integer stockId;

    @Column(name = "id_admin")
    private int adminId;

    @Column(name = "name_product")
    private String nameProduct;

    @Column(name = "price")
    private Integer totalPrice; // стоимость за единицу

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "purchase_price")
    private Integer purchasePrice; // себестоимость(затраченная на изготовление)(расходы)

    @Column(name = "count")
    private Integer count;


    @Column(name = "description")
    private String description;



    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageEntity> images = new ArrayList<>();
    @Override
    public String toString() {
        return "Product [id=" + id + ", nameProduct=" + nameProduct + ", category=" + category + "]";
    }
}
