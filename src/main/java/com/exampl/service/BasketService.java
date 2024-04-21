package com.exampl.service;

import com.exampl.domain.Basket;
import com.exampl.domain.Product;
import com.exampl.repository.BasketRepository;
import com.exampl.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final ProductRepository productRepository;

    @Autowired
    public BasketService(BasketRepository basketRepository, ProductRepository productRepository) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
    }

    public void addProduct(int userId, Product productId) {
        Basket basket = new Basket();
        basket.setUserId(userId);
        basket.setProductId(productId);
        basketRepository.saveAndFlush(basket);
    }

  /*  public void deleteById(int productId) {
        basketRepository.deleteByProductId(productId);
    }*/

    public void deleteProductById(int id) {
        basketRepository.deleteById(id);
    }



    public List<Basket> findAllByUserId(int id) {
        return basketRepository.findByUserId(id);
    }

}
