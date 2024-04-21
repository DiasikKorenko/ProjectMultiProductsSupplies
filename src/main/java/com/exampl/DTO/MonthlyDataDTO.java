package com.exampl.DTO;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyDataDTO {

    private List<Integer> profitData;
    private List<Integer> ordersData;

    public MonthlyDataDTO(List<Integer> profitData, List<Integer> ordersData) {
        this.profitData = profitData;
        this.ordersData = ordersData;
    }

    @Override
    public String toString() {
        return "MonthlyDataDTO{" +
                "profitData=" + profitData +
                ", ordersData=" + ordersData +
                '}';
    }
    // Конструктор, геттеры и сеттеры
}