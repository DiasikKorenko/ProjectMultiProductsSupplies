package com.exampl.repository;

import com.exampl.domain.Product;
import com.exampl.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findById(Long id);
    List<Product> findAllByAdminId(int id);

    List<Product> findAllByNameProduct(String nameProduct);

    List<Product> findAllBySubcategory(String subcategory);

    List<Product> findAllByChapter(String chapter);


    List<Product> findAllByCategory(String category);
    List<Product> findAllByManufacturer(String manufacturer);
}
