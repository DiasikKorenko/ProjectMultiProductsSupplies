package com.exampl.service;


import com.exampl.DTO.MonthlyDataDTO;
import com.exampl.domain.Order;
import com.exampl.domain.OrderProduct;
import com.exampl.domain.Product;
import com.exampl.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    public Integer getTotalOrdersForManufacturer(int manufacturerId) {
        return orderProductRepository.countByManufacturerId(manufacturerId);
    }

    public Integer getTotalQuantityForManufacturer(int manufacturerId) {
        return orderProductRepository.sumQuantityByManufacturerId(manufacturerId);
    }

    public Integer getSumTotalCostOrdersForManufacturer(int manufacturerId) {
        return orderProductRepository.sumTotalCostByManufacturerId(manufacturerId);
    }


    public Integer getTotalProfitForManufacturerLastWeek(int manufacturerId) {
        // Получаем текущую дату и вычитаем из нее 7 дней для получения даты недели назад
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        // Вызываем метод репозитория для получения общей прибыли за последнюю неделю
        return orderProductRepository.sumTotalCostForManufacturerLastWeek(manufacturerId, Timestamp.valueOf(weekAgo));
    }


    public Integer getTotalProfitForManufacturerLastMonth(int manufacturerId) {
        // Получаем текущую дату и вычитаем из нее 7 дней для получения даты недели назад
        LocalDateTime monthAgo = LocalDateTime.now().minusMonths(1);
        // Вызываем метод репозитория для получения общей прибыли за последнюю неделю
        return orderProductRepository.sumTotalCostForManufacturerLastMonth(manufacturerId, Timestamp.valueOf(monthAgo));
    }

    public Integer getTotalCostForManufacturerLastSixMonth(int manufacturerId) {
        // Получаем текущую дату и вычитаем из нее 7 дней для получения даты недели назад
        LocalDateTime sixMonthAgo = LocalDateTime.now().minusMonths(6);
        // Вызываем метод репозитория для получения общей прибыли за последнюю неделю
        return orderProductRepository.sumTotalCostForManufacturerLastMonth(manufacturerId, Timestamp.valueOf(sixMonthAgo));
    }


    public List<MonthlyDataDTO> getMonthlyDataForManufacturer(int manufacturerId) {
        List<Object[]> rawData = orderProductRepository.getMonthlyDataForManufacturer(manufacturerId);
        List<MonthlyDataDTO> monthlyDataList = new ArrayList<>();

        for (int monthNumber = 1; monthNumber <= 12; monthNumber++) {
            int profit = 0;
            int orders = 0;

            for (Object[] row : rawData) {
                int rowMonthNumber = ((Number) row[0]).intValue();
                if (rowMonthNumber == monthNumber) {
                    profit = ((Number) row[1]).intValue();
                    orders = ((Number) row[2]).intValue();
                    break;
                }
            }


            System.out.println("Month: " + monthNumber + ", Profit: " + profit + ", Orders: " + orders);

            monthlyDataList.add(new MonthlyDataDTO(Collections.singletonList(profit), Collections.singletonList(orders)));
        }

        return monthlyDataList;
    }
}
