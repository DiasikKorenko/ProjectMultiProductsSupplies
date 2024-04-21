package com.exampl.controller;

import com.exampl.domain.Basket;
import com.exampl.domain.Product;
import com.exampl.domain.User;
import com.exampl.service.BasketService;
import com.exampl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Component
@RequestMapping("/basket")
public class BasketController {

    private final BasketService basketService;

    private final UserService userService;

    @Autowired
    public BasketController(BasketService basketService, UserService userService) {
        this.basketService = basketService;
        this.userService = userService;
    }

    @GetMapping("/addProduct")
    public String addProductToBasket(@RequestParam Product productId, RedirectAttributes redirectAttributes, Model model) {
        User user = userService.getCurrentUser();
        basketService.addProduct(user.getId(), productId);
        redirectAttributes.addFlashAttribute("message", "Product added to the basket");
        redirectAttributes.addFlashAttribute("addedProductId", productId); // Добавление ID добавленного товара
        return "redirect:/product/all";
    }

    @GetMapping("/addProductDetails")
    public String addProductToBasketDetails(@RequestParam Product productId, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {
        User user = userService.getCurrentUser();
        basketService.addProduct(user.getId(), productId);
        redirectAttributes.addFlashAttribute("message", "Товар добавлен в избранное");
        redirectAttributes.addFlashAttribute("addedProductId", productId); // Добавление ID добавленного товара
        String referer = request.getHeader("Referer");

        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        } else {
            // Если URL предыдущей страницы не доступен, выполните какой-то альтернативный код или верните дефолтную страницу
            return "redirect:/";
        }
    }

   /* @PostMapping("/deleteProduct/{productId}")
    public String deleteProductFromBasket(@RequestParam int productId) {
        basketService.deleteById(productId);

        return "redirect:/basket/viewBasket";
    }*/

    @GetMapping("/deleteProduct/{id}")
    public String deleteProductFromBasket(@PathVariable int id) {
        basketService.deleteProductById(id);
        return "redirect:/basket/viewBasket";
    }

  /*  @GetMapping("/viewBasket")
    public String viewBasket( Model model) {
        User user = userService.getCurrentUser();
        List<Basket> basketItems = basketService.findAllByUserId(user.getId());
        model.addAttribute("basketItems", basketItems);
        return "basketView"; // предполагается, что у вас есть файл basketView.html
    }*/

    @GetMapping("/viewBasket")
    public String viewBasket(Model model) {
        User user = userService.getCurrentUser();
        List<Basket> basketItems = basketService.findAllByUserId(user.getId());

        model.addAttribute("basketItems", basketItems);
        return "wishlist.html"; // Предполагается, что у вас есть файл basketView.html
    }
}
