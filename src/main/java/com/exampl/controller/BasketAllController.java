package com.exampl.controller;

import com.exampl.domain.*;
import com.exampl.service.BasketAllService;
import com.exampl.service.CartService;
import com.exampl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@RequestMapping("/basketAll")
public class BasketAllController {


    private final BasketAllService basketAllService;

    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public BasketAllController(BasketAllService basketAllService, UserService userService, CartService cartService) {
        this.basketAllService = basketAllService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("/addProductAll")
    public String addProductToBasketAll(@RequestParam Product productId, RedirectAttributes redirectAttributes, Model model) {
        User user = userService.getCurrentUser();
        basketAllService.addProduct(user.getId(), productId);
        redirectAttributes.addFlashAttribute("message", "Товар добвлен в корзину");
        redirectAttributes.addFlashAttribute("addedProductId", productId); // Добавление ID добавленного товара
        return "redirect:/product/all";
    }

    @GetMapping("/addProductDetails")
    public String addProductToBasketAllDetails(@RequestParam Product productId, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) {
        User user = userService.getCurrentUser();
        basketAllService.addProduct(user.getId(), productId);
        redirectAttributes.addFlashAttribute("messageBasketAll", "Товар добавлен в корзину");
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
        basketAllService.deleteProductById(id);
        return "redirect:/basketAll/viewBasket";
    }

  /*  @GetMapping("/viewBasket")
    public String viewBasket( Model model) {
        User user = userService.getCurrentUser();
        List<Basket> basketItems = basketService.findAllByUserId(user.getId());
        model.addAttribute("basketItems", basketItems);
        return "basketView"; // предполагается, что у вас есть файл basketView.html
    }*/

    @GetMapping("/viewBasket")
    public String viewBasket(Model model, RedirectAttributes redirectAttributes) {
        User user = userService.getCurrentUser();
        List<BasketAll> basketItems = basketAllService.findAllByUserId(user.getId());
        List<Cart> userCards = cartService.getUserCards(user.getId());
        model.addAttribute("userCards", userCards);
        model.addAttribute("basketItems", basketItems);
        /*model.addAttribute("successMessage", model.getAttribute("successMessage"));*/
        String successMessage = (String) redirectAttributes.getFlashAttributes().get("successMessage");

        String errorMessage = (String) redirectAttributes.getFlashAttributes().get("errorMessage");

        // Проверяем, какое сообщение установлено
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
            // Очищаем атрибут, чтобы он не отображался повторно
            redirectAttributes.getFlashAttributes().remove("successMessage");
        } else if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            // Очищаем атрибут, чтобы он не отображался повторно
            redirectAttributes.getFlashAttributes().remove("errorMessage");
        }
        System.out.println(basketItems);
        return "basket"; // Предполагается, что у вас есть файл basketView.html
    }
}
