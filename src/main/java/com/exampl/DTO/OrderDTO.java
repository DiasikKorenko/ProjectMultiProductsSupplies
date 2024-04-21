package com.exampl.DTO;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Data
public class OrderDTO {
    private int id;
    private Timestamp dateOrder;
    private int sumAllProduct;
    private List<OrderProductDTO> orderProducts = new ArrayList<>();

    @Override
    public String toString() {
        return "OrderDTO(id=" + id +
                ", dateOrder='" + dateOrder + '\'' +
                ", sumAllProduct=" + sumAllProduct +
                ", orderProducts=" + orderProducts +
                ')';
    }
}

