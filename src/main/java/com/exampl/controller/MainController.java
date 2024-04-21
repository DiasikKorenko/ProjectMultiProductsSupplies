package com.exampl.controller;

import com.exampl.domain.Order;
import com.exampl.domain.Stock;
import com.exampl.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {




    @GetMapping("/")
    public String home()  {
        return "index.html";
    }


    @GetMapping("/about")
    public String about()  {
        return "about.html";
    }

    @GetMapping("/loginGood")
    public String loginGood()  {
        return "loginGood.html"; // Имя представления без расширения
    }
    @PostMapping("/loginError")
    public String loginPage(Model model) {
        // Добавляем сообщение об ошибке в модель, если оно есть
        return "loginError";
    }

    @GetMapping("/technicalSupport")
    public String technicalSupport()  {
        return "contact.html"; // Имя представления без расширения
    }


    @GetMapping("/registration")
    public String reg()  {
        return "created.html";
    }


    @GetMapping("/adminHome")
    public String AdminHome()  {

        return "indexAdmin(creatStock).html";
    }

    @GetMapping("/AdminGeneralPage")
    public String AdminGeneralPage(Model model)  {
        model.addAttribute("stock", new Stock());
        return "indexAdmin(creatStock).html";
    }
  /*  @GetMapping("/updateAdminData")
    public String AdminAccountPage()  {
        return "my-accountAdmin.html";
    }*/

    @GetMapping("/allstocks")
    public String test()  {
        return "allStockAdmin.html";
    }


    /*@GetMapping("/my-account")
    public String orderUsers(Model model) {
        List<Order> historyOrders = orderService.getCurrentUserOrders();
        model.addAttribute("historyOrders", historyOrders);
        return "my-account.html";
    }*/

/*    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/adminHome";
        } else if (request.isUserInRole("USER")) {
            return "redirect:/";
        }
        return "/loginError";
    }*/



//+++
    /*@RequestMapping("/default")
    public ResponseEntity<?> defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            return ResponseEntity.ok("redirect:/adminHome");
        } else if (request.isUserInRole("USER")) {
            return ResponseEntity.ok("redirect:/");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Ошибка входа, попробуйте еще раз.");
    }*/

    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ADMIN")) {
            return "redirect:/adminHome";
        } else if (request.isUserInRole("USER")) {
            return "redirect:/loginGood";
        }
        return "errorPage"; // Замените "errorPage" на имя вашего представления
    }
}
