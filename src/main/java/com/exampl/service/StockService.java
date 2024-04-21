package com.exampl.service;

import com.exampl.domain.Stock;
import com.exampl.domain.Task;
import com.exampl.repository.StockRepository;
import com.exampl.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {


    private final StockRepository stockRepository;
    private final AdminService adminService;

    @Autowired
    public StockService(StockRepository stockRepository, AdminService adminService) {
        this.stockRepository = stockRepository;
        this.adminService = adminService;
    }


    public List<Stock> getAllStockByAdmin(int adminId) {
        List<Stock> stocks = stockRepository.findAllByAdminId(adminId);
        return stocks;
    }

    public void adminAddStock(int adminId, Stock stock) {
        stock.setAdminId(adminId);
        stockRepository.saveAndFlush(stock);
    }

    public boolean adminDeleteStock(Integer stockId, Integer adminId) {
        Stock stockDBEntity = stockRepository.findById(stockId).orElse(null);
        if (stockDBEntity == null || stockDBEntity.getAdminId() != adminId)
            return false;
        stockRepository.deleteById(stockId);
        stockRepository.flush();
        return true;
    }

    public boolean updateStock(Integer stockId, Integer currentAdminId, Stock updatedStock) {
        Stock stockDBEntity = stockRepository.findById(stockId).orElse(null);
        if (stockDBEntity == null || !currentAdminId.equals(stockDBEntity.getAdminId())) {
            return false;
        }
        // Проверяем, изменились ли данные задачи
        if (updatedStock.getNameStock() != null) {
            stockDBEntity.setNameStock(updatedStock.getNameStock());
        }
        if (updatedStock.getAdressStock() != null) {
            stockDBEntity.setAdressStock(updatedStock.getAdressStock());
        }
        // Сохранение обновленных данных задачи
        stockRepository.save(stockDBEntity);
        return true;
    }

    public boolean adminDeleteStock(int adminId, int  stockId) {
        Stock stockDBEntity = stockRepository.findById(stockId).orElse(null);
        if (stockDBEntity == null || stockDBEntity.getAdminId() != adminId)
            return false;

        stockRepository.deleteById(stockId);
        stockRepository.flush();
        return true;
    }

    public String getStorageAddressByProductId(int id) {
        Optional<Stock> storageOptional = stockRepository.findById(id);
        return storageOptional.map(Stock::getAdressStock).orElse(null);
    }


}
