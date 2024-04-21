package com.exampl.service;

import com.exampl.domain.Basket;
import com.exampl.domain.BasketAll;
import com.exampl.domain.Product;
import com.exampl.repository.BasketAllRepository;
import com.exampl.repository.BasketRepository;
import com.exampl.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BasketAllService {


    private final BasketAllRepository basketAllRepository;
    private final ProductRepository productRepository;

    @Autowired
    public BasketAllService(BasketAllRepository basketAllRepository, ProductRepository productRepository) {
        this.basketAllRepository = basketAllRepository;
        this.productRepository = productRepository;
    }

    public void addProduct(int userId, Product productId) {
        BasketAll basketAll = new BasketAll();
        basketAll.setUserId(userId);
        basketAll.setProductId(productId);
        basketAllRepository.saveAndFlush(basketAll);
    }

  /*  public void deleteById(int productId) {
        basketRepository.deleteByProductId(productId);
    }*/

    public void deleteProductById(int id) {
        basketAllRepository.deleteById(id);
    }


@Transactional
    public void clearBasketByUserId(int userId) {
        basketAllRepository.deleteAllByUserId(userId);
    }


    public List<BasketAll> findAllByUserId(int id) {
        return basketAllRepository.findByUserId(id);
    }

}
