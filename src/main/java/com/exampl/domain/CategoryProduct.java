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
@Table(name = "categoryProduct")
public class CategoryProduct {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoryProduct_id_seq")
   @SequenceGenerator(name = "categoryProduct_id_seq", sequenceName = "categoryProduct_id_seq", allocationSize = 1)
   private int id;
   @Column(name = "nameCategory")
   private int nameCategory;
}
