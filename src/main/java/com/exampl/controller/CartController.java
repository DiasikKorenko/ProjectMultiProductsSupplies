package com.exampl.controller;

import com.exampl.domain.Cart;
import com.exampl.domain.User;
import com.exampl.exception.SomeCustomException;
import com.exampl.service.CartService;
import com.exampl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
@RequestMapping("/cart")
public class CartController {

    UserService userService;
    CartService cartService;


    @Autowired
    public CartController(UserService userService,CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("/addCart")
    public String addCart(Model model) {

        model.addAttribute("cart", new Cart());
        return "CartTest.html";
    }

/*
    @PostMapping("/add")
    public String addCard(@ModelAttribute("cart") Cart cart) {
        // Предполагается, что CartForm имеет поля для данных карты (номер, срок действия, CVV и т.д.)
        // Валидация и обработка формы могут быть добавлены здесь перед вызовом сервиса

        // Вызываем сервис для создания карты, передавая объект cart
        cartService.createCart(cart);

        // Перенаправляем пользователя на страницу после добавления карты
        return "redirect:/addCart"; // Предположим, что у вас есть страница с именем "dashboard"
    }
*/


    @PostMapping("/add")
    public String addCard(@ModelAttribute("cart") Cart cart, RedirectAttributes redirectAttributes, Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("currentUser", user);
        try {

            // Ваш текущий код для создания карты
            cartService.createCart(cart);

            // В случае успешного добавления карты
            redirectAttributes.addFlashAttribute("successMessage", "Карта успешно добавлена");
            return "redirect:/user/my-account-2"; // Предположим, у вас есть страница "shop/all"
        } catch (SomeCustomException e) {
            // В случае ошибки добавления карты

            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/my-account-2"; // Перенаправление на страницу формы
        }
    }

}
