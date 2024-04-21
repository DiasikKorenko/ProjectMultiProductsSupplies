package com.exampl.service;

import com.exampl.DTO.OrderDTO;
import com.exampl.DTO.OrderProductDTO;
import com.exampl.domain.Cart;
import com.exampl.domain.Order;
import com.exampl.domain.OrderProduct;
import com.exampl.domain.Product;
import com.exampl.domain.User;
import com.exampl.exception.NotEnoughStockException;
import com.exampl.repository.CartRepository;
import com.exampl.repository.OrderProductRepository;
import com.exampl.repository.OrderRepository;
import com.exampl.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    private final CartService cartService;

    private final ProductRepository productRepository;

    private final UserService userService;

    private final CartRepository cartRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository, OrderProductRepository orderProductRepository, CartService cartService, ProductRepository productRepository, UserService userService, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.cartService = cartService;
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

   /* public List<Order> getAllOrdersByUser(int userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        return orders;
    }*/

    public List<Order> getAllOrdersByUser(int userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        Collections.reverse(orders);
        return orders;
    }

   /* public void createOrder(int userId, List<Integer> productIds, String deliveryAddress, int cardNumber, List<Integer> quantities ) {
        User user = userService.getCurrentUser(); // Получи пользователя по userId (используй userRepository)

        Order order = new Order();
        order.setDateOrder(new Date());
        order.setStatus("Новый");
        order.setStatusPay("Ожидает оплаты");
        order.setDeliveryAddress(deliveryAddress);
        order.setUser(user);

        List<OrderProduct> orderProducts = new ArrayList<>();

    *//*    for (Integer productId : productIds) {
            Product product = productRepository.findById(productId).orElse(null);

            if (product != null) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrder(order);
                orderProduct.setProduct(product);
                orderProduct.setQuantity(quantity); // Предположим, что каждый товар добавляется в количестве 1.

                orderProducts.add(orderProduct);
            }
        }*//*

        for (int i = 0; i < productIds.size(); i++) {
            Integer productId = productIds.get(i);
            Product product = productRepository.findById(productId).orElse(null);

            if (product != null) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setOrder(order);
                orderProduct.setProduct(product);
                orderProduct.setQuantity(quantities.get(i)); // Используем количество из списка quantities

                orderProducts.add(orderProduct);
            }
        }

        order.setOrderProducts(orderProducts);
        orderRepository.save(order);
        order.updateSumAllProduct(); // Обновляем сумму после сохранения заказа
        orderRepository.save(order); // Снова сохраняем заказ с обновленной суммой

        Cart userCart = cartService.getCartByNumber(cardNumber);

        if (userCart != null && userCart.getSumCart() >= order.getSumAllProduct()) {
            // Уменьшаем баланс карты пользователя
            userCart.setSumCart(userCart.getSumCart() - order.getSumAllProduct());
            cartRepository.save(userCart);

            order.setStatusPay("Оплачено");
            order.updateSumAllProduct(); // Обновляем сумму после сохранения заказа
            orderRepository.save(order); // Снова сохраняем заказ с обновленной суммой и статусом оплаты
        } else {
            // Обработка неудачной оплаты
            order.setStatusPay("Ошибка оплаты");
            orderRepository.save(order); // Сохраняем заказ с обновленным статусом оплаты
        }
    }
*/



   /* public void createOrder(int userId, List<Integer> productIds, String deliveryAddress, int cardNumber, List<Integer> quantities) {
        User user = userService.getCurrentUser(); // Получи пользователя по userId (используй userRepository)

        Order order = new Order();
        order.setDateOrder(new Date());
        order.setStatus("Новый");
        order.setStatusPay("Ожидает оплаты");
        order.setDeliveryAddress(deliveryAddress);
        order.setUser(user);


        List<OrderProduct> orderProducts = new ArrayList<>();

        for (int i = 0; i < productIds.size(); i++) {
            Integer productId = productIds.get(i);
            Product product = productRepository.findById(productId).orElse(null);

            if (product != null) {
                int orderedQuantity = quantities.get(i);

                // Проверка наличия достаточного количества товара на складе
                if (product.getCount() >= orderedQuantity) {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(order);
                    orderProduct.setProduct(product);
                    orderProduct.setQuantity(orderedQuantity);
                    orderProduct.setManufacturerId(product.getAdminId());

                    orderProducts.add(orderProduct);

                    // Уменьшение количества товара на складе
                    product.setCount(product.getCount() - orderedQuantity);
                    productRepository.save(product);
                } else {
                    // Обработка недостаточного количества товара на складе
                    throw new NotEnoughStockException("Товара на складе не хватает");
                }
            }
        }

        order.setOrderProducts(orderProducts);
        orderRepository.save(order);
        order.updateSumAllProduct(); // Обновляем сумму после сохранения заказа
        orderRepository.save(order); // Снова сохраняем заказ с обновленной суммой

        Cart userCart = cartService.getCartByNumber(cardNumber);

        if (userCart != null && userCart.getSumCart() >= order.getSumAllProduct()) {
            // Уменьшаем баланс карты пользователя
            userCart.setSumCart(userCart.getSumCart() - order.getSumAllProduct());
            cartRepository.save(userCart);

            order.setStatusPay("Оплачено");
            order.updateSumAllProduct(); // Обновляем сумму после сохранения заказа
            orderRepository.save(order); // Снова сохраняем заказ с обновленной суммой и статусом оплаты
        } else {
            // Обработка неудачной оплаты
            order.setStatusPay("Ошибка оплаты");
            *//*orderRepository.save(order);*//* // Сохраняем заказ с обновленным статусом оплаты
            orderRepository.delete(order);
        }
    }
*/

    public void createOrder(int userId, List<Integer> productIds, String deliveryAddress, String cardNumber, List<Integer> quantities) {
        User user = userService.getCurrentUser(); // Получи пользователя по userId (используй userRepository)

        Order order = new Order();
        /*order.setDateOrder(new Date());*/
        /*order.setDateOrder(new Date((new java.util.Date()).getTime()));*/
        order.setDateOrder(java.sql.Timestamp.valueOf(LocalDateTime.now()));
        order.setStatus("Новый");
        order.setStatusPay("Ожидает оплаты");
        order.setDeliveryAddress(deliveryAddress);
        order.setUser(user);

        List<OrderProduct> orderProducts = new ArrayList<>();

        for (int i = 0; i < productIds.size(); i++) {
            Integer productId = productIds.get(i);
            Product product = productRepository.findById(productId).orElse(null);

            if (product != null) {
                int orderedQuantity = quantities.get(i);

                // Проверка наличия достаточного количества товара на складе
                if (product.getCount() >= orderedQuantity) {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setOrder(order);
                    orderProduct.setProduct(product);
                    orderProduct.setQuantity(orderedQuantity);
                    orderProduct.setManufacturerId(product.getAdminId());
                    orderProduct.setDate(java.sql.Timestamp.valueOf(LocalDateTime.now()));

                    orderProducts.add(orderProduct);
                } else {
                    // Обработка недостаточного количества товара на складе
                    throw new NotEnoughStockException("Товара на складе не хватает");
                }
            }
        }

        order.setOrderProducts(orderProducts);
        orderRepository.save(order);
        order.updateSumAllProduct(); // Обновляем сумму после сохранения заказа
        orderRepository.save(order); // Снова сохраняем заказ с обновленной суммой

        Cart userCart = cartService.getCartByNumber(cardNumber);

        if (userCart != null && userCart.getSumCart() >= order.getSumAllProduct()) {
            // Уменьшаем баланс карты пользователя
            userCart.setSumCart(userCart.getSumCart() - order.getSumAllProduct());
            cartRepository.save(userCart);

            order.setStatusPay("Оплачено");
            order.updateSumAllProduct(); // Обновляем сумму после сохранения заказа
            orderRepository.save(order); // Снова сохраняем заказ с обновленной суммой и статусом оплаты

            // Теперь, когда оплата прошла успешно, уменьшаем количество товара на складе
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                Product product = orderProduct.getProduct();
                int orderedQuantity = orderProduct.getQuantity();
                product.setCount(product.getCount() - orderedQuantity);
                productRepository.save(product);
            }
        } else {
            // Обработка неудачной оплаты
            order.setStatusPay("Ошибка оплаты");
            orderRepository.delete(order);
            throw new NotEnoughStockException("Товара на складе не хватает или у вас недостаточно средств");

        }
    }















    public List<OrderDTO> convertToOrderDTOs(List<Order> orders) {
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setDateOrder(order.getDateOrder());
            orderDTO.setSumAllProduct(order.getSumAllProduct());

            List<OrderProductDTO> orderProductDTOs = new ArrayList<>();
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                OrderProductDTO orderProductDTO = new OrderProductDTO();
                orderProductDTO.setId(orderProduct.getId());
                orderProductDTO.setProductName(orderProduct.getProduct().getNameProduct());
                orderProductDTO.setQuantity(orderProduct.getQuantity());
                orderProductDTO.setSum(orderProduct.getSum());
                // Другие необходимые поля...

                orderProductDTOs.add(orderProductDTO);
            }

            orderDTO.setOrderProducts(orderProductDTOs);
            // Другие необходимые поля...

            orderDTOs.add(orderDTO);
            System.out.println(orderDTO);
        }
        return orderDTOs;
    }



















































    public List<Order> getOrdersByManufacturerId(int manufacturerId) {
        return orderRepository.findOrdersByManufacturerId(manufacturerId);
    }

    public List<Order> getNewOrdersByManufacturerId(int manufacturerId) {
        return orderRepository.findOrdersByManufacturerIdAndStatus(manufacturerId, "Новый");
    }

    public List<Order> getTreatmentOrdersByManufacturerId(int manufacturerId) {
        return orderRepository.findOrdersByManufacturerIdAndStatus(manufacturerId, "В обработке");
    }

    public Order getOrderByIdAndManufacturerId(int orderId, int manufacturerId) {

        return orderRepository.findOrderByIdAndManufacturerId(orderId, manufacturerId);
    }


}




