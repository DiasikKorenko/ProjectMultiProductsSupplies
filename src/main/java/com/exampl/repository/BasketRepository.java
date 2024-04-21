package com.exampl.repository;

import com.exampl.domain.Basket;
import com.exampl.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Integer> {

    List<Basket> findByUserId(int userId);


    /*void deleteByProductId(int productId);*/
}
