package com.exampl.controller;

import com.exampl.domain.Stock;
import com.exampl.domain.Task;
import com.exampl.service.AdminService;
import com.exampl.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Component
@RequestMapping("/stock")
public class StockController {


    AdminService adminService;
    StockService stockService;

    @Autowired
    public StockController(AdminService adminService, StockService stockService) {
        this.adminService = adminService;
        this.stockService = stockService;
    }

    @GetMapping("/allStockAdmin")
    public String allStockAdmin(Model model) {
        List<Stock> stocks = stockService.getAllStockByAdmin(adminService.getCurrentAdmin().getId());
        model.addAttribute("stocks", stocks);
        return "allStockAdmin.html";
    }


    @PostMapping("/addStock")
    public String addStock(Stock stock) {
        stockService.adminAddStock(adminService.getCurrentAdmin().getId(), stock);
        return "indexAdmin(creatStock).html"; // Перенаправляем на страницу с сообщением об успешном добавлении
    }

    @PostMapping("/delete/{stockId}")
    public ResponseEntity<?> deleteStock(@PathVariable int stockId, Model model) {
        boolean isDeleted = stockService.adminDeleteStock(adminService.getCurrentAdmin().getId(), stockId);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/update/{stockId}")
    public ResponseEntity<?> updateStock(@PathVariable int stockId, @RequestBody Stock updatedStock) {
        boolean isUpdated = stockService.updateStock(stockId, adminService.getCurrentAdmin().getId(), updatedStock);
        if (isUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
   /* @GetMapping("/addStock")
    public String showStockForm(Model model) {
        // Создаем объект Stock и передаем его в модель
        model.addAttribute("stock", new Stock());
        return "indexAdmin(creatStock).html";
    }*/