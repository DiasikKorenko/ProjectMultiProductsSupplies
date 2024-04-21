/*
package com.exampl.service;

import com.exampl.domain.Cart;
import com.exampl.domain.Order;
import com.exampl.domain.Product;
import com.exampl.domain.User;
import com.exampl.exception.SomeCustomException;
import com.exampl.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class CartService {

    private final UserService userService;
    private final CartRepository cartRepository;

    public CartService(UserService userService, CartRepository cartRepository) {
        this.userService = userService;
        this.cartRepository = cartRepository;
    }


    public Cart createCart(Cart cart) {
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            // Проверяем, существует ли карта с таким номером
            Cart existingCart = cartRepository.findByNumber(cart.getNumber());

            if (existingCart == null) {
                // Карта с таким номером не существует, присваиваем текущему пользователю и сохраняем
                cart.setUserId(currentUser.getId());
                return cartRepository.save(cart);
            } else {
                // Карта с таким номером уже существует, бросаем пользовательское исключение
                throw new SomeCustomException(cart.getNumber());
            }
        }

        return null; // Возвращаем null, если текущий пользователь не авторизован
    }

    public List<Cart> getUserCards(int userId) {

        // Получаем список карт пользователя по его идентификатору
        return cartRepository.findByUserId(userId);
    }

    public Cart getCartByNumber(int cardNumber) {
        return cartRepository.findByNumber(cardNumber);
    }
}








*/
package com.exampl.service;

import com.exampl.domain.Cart;
import com.exampl.domain.User;
import com.exampl.exception.SomeCustomException;
import com.exampl.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final UserService userService;
    private final CartRepository cartRepository;

    public CartService(UserService userService, CartRepository cartRepository) {
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    public Cart createCart(Cart cart) {
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            // Проверяем, существует ли карта с таким номером
            Cart existingCart = cartRepository.findByNumber(cart.getNumber());

            if (existingCart == null) {
                // Карта с таким номером не существует, присваиваем текущему пользователю и сохраняем
                cart.setUserId(currentUser.getId());
                return cartRepository.save(cart);
            } else {
                // Карта с таким номером уже существует, бросаем пользовательское исключение
                throw new SomeCustomException(cart.getNumber());
            }
        }

        return null; // Возвращаем null, если текущий пользователь не авторизован
    }

    public List<Cart> getUserCards(int userId) {
        // Получаем список карт пользователя по его идентификатору
        return cartRepository.findByUserId(userId);
    }

    public Cart getCartByNumber(String cardNumber) {
        return cartRepository.findByNumber(cardNumber);
    }
}
