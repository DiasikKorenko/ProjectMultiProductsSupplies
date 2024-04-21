package com.exampl.repository;

import com.exampl.domain.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o " +
            "JOIN OrderProduct op ON o.id = op.order.id " +
            "WHERE o.id IS NOT NULL AND op.manufacturerId = :manufacturerId")
    List<Order> findOrdersByManufacturerId(@Param("manufacturerId") int manufacturerId);

    List<Order>findAllByUserId(int userId);

/*
    @Query("SELECT o FROM Order o WHERE o.manufacturer.id = :manufacturerId AND o.status = :status")
    List<Order> findOrdersByManufacturerIdAndStatus(
            @Param("manufacturerId") int manufacturerId,
            @Param("status") String status
    );
*/

    @Query("SELECT o FROM Order o " +
            "JOIN OrderProduct op ON o.id = op.order.id " +
            "WHERE o.id IS NOT NULL AND op.manufacturerId = :manufacturerId AND o.status = :status")
    List<Order> findOrdersByManufacturerIdAndStatus(@Param("manufacturerId") int manufacturerId,
                                                    @Param("status") String status);


    @Query("SELECT o FROM Order o " +
            "JOIN OrderProduct op ON o.id = op.order.id " +
            "WHERE o.id = :orderId AND op.manufacturerId = :manufacturerId")
    Order findOrderByIdAndManufacturerId(@Param("orderId") int orderId,
                                         @Param("manufacturerId") int manufacturerId);


}
