package com.exampl.repository;

import com.exampl.domain.ImageEntity;
import com.exampl.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

    Integer countByManufacturerId(int manufacturerId);

    @Query("SELECT SUM(op.quantity) FROM OrderProduct op WHERE op.manufacturerId = :manufacturerId")
    Integer sumQuantityByManufacturerId(int manufacturerId);

    @Query("SELECT SUM(op.sum) FROM OrderProduct op WHERE op.manufacturerId = :manufacturerId")
    Integer sumTotalCostByManufacturerId(int manufacturerId);


    @Query("SELECT SUM(op.sum) FROM OrderProduct op WHERE op.manufacturerId = :manufacturerId AND op.date >= :weekAgo")
    Integer sumTotalCostForManufacturerLastWeek(@Param("manufacturerId") int manufacturerId, @Param("weekAgo") Timestamp weekAgo);

    @Query("SELECT SUM(op.sum) FROM OrderProduct op WHERE op.manufacturerId = :manufacturerId AND op.date >= :monthAgo")
    Integer sumTotalCostForManufacturerLastMonth(@Param("manufacturerId") int manufacturerId, @Param("monthAgo") Timestamp monthAgo);


    @Query("SELECT SUM(op.sum) FROM OrderProduct op WHERE op.manufacturerId = :manufacturerId AND op.date >= :sixMonthAgo")
    Integer sumTotalCostForManufacturerLastSixMonth(@Param("manufacturerId") int manufacturerId, @Param("sixMonthAgo") Timestamp sixMonthAgo);


    @Query(value = "SELECT " +
            "  EXTRACT(MONTH FROM op.date) AS month_number, " +
            "  COALESCE(SUM(op.total_cost), 0) AS profit, " + // Заменяем SUM(op.sum) на SUM(op.total_cost) в соответствии с вашей структурой таблицы
            "  COALESCE(COUNT(op.id), 0) AS orders " +
            "FROM orders_product op " + // Используем имя таблицы "orders_product" вместо "orderproduct"
            "WHERE op.manufacturer_id = :manufacturerId " + // Заменяем "op.manufacturerId" на "op.manufacturer_id" в соответствии с вашей структурой таблицы
            "GROUP BY month_number " +
            "ORDER BY month_number", nativeQuery = true)
    List<Object[]> getMonthlyDataForManufacturer(int manufacturerId);



}
