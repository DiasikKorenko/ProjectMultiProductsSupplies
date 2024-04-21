package com.exampl.controller;

import com.exampl.service.DeliveryService;
import com.exampl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@RequestMapping("/delivery")
public class DeliveryController {

    UserService userService;

    DeliveryService deliveryService;


    @Autowired
    public DeliveryController(UserService userService, DeliveryService deliveryService) {
        this.userService = userService;
        this.deliveryService = deliveryService;
    }

    @PostMapping("/saveRoute")
    public ResponseEntity<String> saveRoute(@RequestParam("orderId") int orderId,
                                            @RequestParam("route") String route,
                                            @RequestParam("expenses") double expenses) {
        // Ваш код сохранения данных в базе данных
        // orderId - идентификатор заказа
        // routeIndex - индекс маршрута
        // distance - километраж
        // fuelCost - затраты на топливо

        // Пример кода для сохранения данных в базе данных (используйте свой собственный сервис)
        deliveryService.saveRouteData(orderId, route, expenses);

        return ResponseEntity.ok("Данные успешно сохранены в базе данных.");
    }





















































}
