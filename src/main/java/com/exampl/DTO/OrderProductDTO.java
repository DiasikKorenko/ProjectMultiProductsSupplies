package com.exampl.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderProductDTO {
    private int id;
    private String productName; // Предположим, что это поле вам нужно для графика
    private int quantity; // Предположим, что это поле вам нужно для графика
    // Другие необходимые поля...
    private int sum;
    @Override
    public String toString() {
        return "OrderProductDTO(id=" + id +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", sum=" + sum +
                ')';
    }
}
