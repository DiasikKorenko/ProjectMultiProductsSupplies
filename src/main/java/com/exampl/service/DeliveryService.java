package com.exampl.service;

import com.exampl.domain.Admin;
import com.exampl.domain.Delivery;
import com.exampl.domain.ImageEntity;
import com.exampl.domain.Product;
import com.exampl.repository.CartRepository;
import com.exampl.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {


    private final UserService userService;
    private final CartRepository cartRepository;

    private final DeliveryRepository deliveryRepository;

    private final AdminService adminService;

    @Autowired
    public DeliveryService(UserService userService, CartRepository cartRepository, DeliveryRepository deliveryRepository, AdminService adminService) {
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.deliveryRepository = deliveryRepository;
        this.adminService = adminService;
    }


    public void saveRouteData(int orderId, String route, double expenses) {
        // Здесь реализуйте сохранение данных в базе данных
        // Используйте ваш собственный метод сохранения, возможно, сущность Route и репозиторий
        Admin admin = adminService.getCurrentAdmin();
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setRoute(route);
        delivery.setExpenses(expenses);
        delivery.setAdminId(admin.getId());

        deliveryRepository.save(delivery);
    }










    public List<Delivery> getAllInfoDeliveryByAdmin(int adminId) {
        List<Delivery> deliveries = deliveryRepository.findAllByAdminId(adminId);
        return deliveries;
    }
}
