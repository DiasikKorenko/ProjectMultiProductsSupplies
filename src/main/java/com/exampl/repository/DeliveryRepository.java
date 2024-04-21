package com.exampl.repository;

import com.exampl.domain.Delivery;
import com.exampl.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {

    List<Delivery> findAllByAdminId(int id);
}
