package com.exampl.repository;

import com.exampl.domain.Basket;
import com.exampl.domain.BasketAll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketAllRepository extends JpaRepository<BasketAll, Integer> {
    List<BasketAll> findByUserId(int userId);

    void deleteAllByUserId(int userId);
    /*void deleteByProductId(int productId);*/
}
