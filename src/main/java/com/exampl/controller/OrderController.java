package com.exampl.controller;

import com.exampl.DTO.MonthlyDataDTO;
import com.exampl.DTO.OrderDTO;
import com.exampl.DTO.ProductDTO;
import com.exampl.domain.Admin;
import com.exampl.domain.Delivery;
import com.exampl.domain.Order;
import com.exampl.domain.OrderProduct;
import com.exampl.domain.Product;
import com.exampl.domain.User;
import com.exampl.exception.NotEnoughStockException;
import com.exampl.repository.OrderRepository;
import com.exampl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Component
@Controller
@RequestMapping()
public class OrderController {

    private final AdminService adminService;

    private final OrderService orderService;

    private final UserService userService;

    private final ProductService productService;

    private final CartService cartService;

    private final StockService stockService;

    private final DeliveryService deliveryService;

    private final OrderProductService orderProductService;

    private final OrderRepository orderRepository;

    private final BasketAllService basketAllService;

    @Autowired
    public OrderController(AdminService adminService, OrderService orderService, UserService userService, ProductService productService, CartService cartService, StockService stockService, DeliveryService deliveryService, OrderRepository orderRepository, OrderProductService orderProductService, BasketAllService basketAllService) {
        this.adminService = adminService;
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
        this.cartService = cartService;
        this.stockService = stockService;
        this.deliveryService = deliveryService;
        this.orderRepository = orderRepository;
        this.orderProductService = orderProductService;
        this.basketAllService = basketAllService;
    }







    @GetMapping("/my-account")
    public String orderList(Model model) {
        List<Order> orders = orderService.getAllOrdersByUser(userService.getCurrentUser().getId());
        model.addAttribute("orders", orders);
        return "/my-account"; // Имя вашего HTML-шаблона
    }


    @GetMapping("/orderForm")
    public String showOrderForm(Model model) {

        return "orderForm";
    }



/*    @PostMapping("/createOrder")
    public String createOrder(@RequestParam("productIds") List<Integer> productIds,
                              @RequestParam("deliveryAddress") String deliveryAddress,
                              @RequestParam("cardNumber") int cardNumber,
                              @RequestParam("quantities") List<Integer> quantities,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getCurrentUser();
            orderService.createOrder(user.getId(), productIds, deliveryAddress, cardNumber, quantities);
            model.addAttribute("successMessage", "Заказ оформлен успешно");
        } catch (NotEnoughStockException e) {
            model.addAttribute("errorMessage", "Ошибка оформления заказа. Средств недостаточно или товара на складе не хватает");
        }
        return "orderForm";
    }*/

    @PostMapping("/createOrder")
    public String createOrder(@RequestParam("productIds") List<Integer> productIds,
                              @RequestParam("deliveryAddress") String deliveryAddress,
                              @RequestParam("cardNumber") String cardNumber,
                              @RequestParam("quantities") List<Integer> quantities,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getCurrentUser();
            orderService.createOrder(user.getId(), productIds, deliveryAddress, cardNumber, quantities);
            model.addAttribute("successMessage", "Заказ оформлен успешно");
        } catch (NotEnoughStockException e) {
            model.addAttribute("errorMessage", "Ошибка оформления заказа. Средств недостаточно или товара на складе не хватает");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка оформления заказа. Пожалуйста, попробуйте снова или свяжитесь с поддержкой.");
            // Если возникла ошибка, удалите заказ из БД
            // Это предполагает, что у вас есть метод в сервисе для удаления заказа по его идентификатору
        }
        /*return "orderForm";*/

        return "basket";
    }





    @PostMapping("/createOrderWithBasket")
    public String createOrderWithBasket(@RequestParam("productIds") List<Integer> productIds,
                              @RequestParam("deliveryAddress") String deliveryAddress,
                              @RequestParam("cardNumber") String cardNumber,
                              @RequestParam("quantities") List<Integer> quantities,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getCurrentUser();
            orderService.createOrder(user.getId(), productIds, deliveryAddress, cardNumber, quantities);
            model.addAttribute("successMessage", "Заказ оформлен успешно");

            basketAllService.clearBasketByUserId(user.getId());

        } catch (NotEnoughStockException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка оформления заказа. Средств недостаточно или товара на складе не хватает");
        } catch (Exception e) {
            System.out.println("Ошибка при создании заказа: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Ошибка оформления заказа. Пожалуйста, попробуйте снова или свяжитесь с поддержкой.");
            // Если возникла ошибка, удалите заказ из БД
            // Это предполагает, что у вас есть метод в сервисе для удаления заказа по его идентификатору
        }
        /*return "orderForm";*/

        return "redirect:/basketAll/viewBasket";
    }


    @GetMapping("/byManufacturerOld")
    public String getOrdersByManufacturer(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<Order> orders = orderService.getOrdersByManufacturerId(admin.getId());
        model.addAttribute("orders", orders);
        return "workWithOrder2";
    }

    @GetMapping("/byManufacturerNew")
    public String getOrdersByManufacturerNew(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<Order> orders = orderService.getNewOrdersByManufacturerId(admin.getId());
        model.addAttribute("orders", orders);
        return "workWithOrder";
    }

    @GetMapping("/byManufacturerTreatment")
    public String getTreatmentOrdersByManufacturerNew(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<Order> orders = orderService.getTreatmentOrdersByManufacturerId(admin.getId());
        model.addAttribute("orders", orders);
        return "workWithOrder1";
    }

    @GetMapping("/viewOrderByManufacturer/{orderId}")
    public String viewOrderByManufacturer(@PathVariable int orderId, Model model) {
        Admin admin = adminService.getCurrentAdmin();
        Order order = orderService.getOrderByIdAndManufacturerId(orderId, admin.getId());

        if (order == null) {
            // Обработка случая, когда заказ не найден
            return "redirect:/error"; // Или любая другая логика обработки ошибки
        }

        List<OrderProduct> orderProducts = order.getOrderProducts();

        if (orderProducts != null && !orderProducts.isEmpty()) {
            // Создание списка для хранения информации о складах для каждого продукта
            List<String> warehouseNames = new ArrayList<>();

            // Перебор всех продуктов в заказе
            for (OrderProduct orderProduct : orderProducts) {
                // Получение объекта продукта из OrderProduct
                Product product = orderProduct.getProduct();

                // Получение айди продукта из объекта Product
                int productId = product.getId(); // Замените "getId()" на актуальный метод получения айди

                // Получение айди склада из продукта
                int warehouseId = productService.getProductById(productId).getStockId();

                // Получение названия склада по айди
                String warehouseName = stockService.getStorageAddressByProductId(warehouseId);

                // Добавление названия склада в список
                warehouseNames.add(warehouseName);
            }

            model.addAttribute("warehouseNames", warehouseNames);
            model.addAttribute("order", order);
            return "workWithOrder3.html";
        }
        return "redirect:/error";
    }

    @PostMapping("/updateOrderStatus")
    public String updateOrderStatus(@RequestParam int orderId, @RequestParam String status) {
        try {
            // Найдем заказ в базе данных по его идентификатору
            Order order = orderRepository.findById(orderId).orElse(null);

            if (order != null) {
                // Обновим статус заказа
                order.setStatus(status);
                // Сохраним обновленный заказ в базе данных
                orderRepository.save(order);

                return "redirect:/byManufacturerOld";
            } else {
                return "404";
            }
        } catch (Exception e) {
            return "404";
        }
    }

    @GetMapping("/forecasto")
    public String showForecasto(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<OrderDTO> orderDTOs = orderService.convertToOrderDTOs(orderService.getOrdersByManufacturerId(admin.getId()));
        model.addAttribute("orderDTOs", orderDTOs);
        return "Spros";
    }


     @GetMapping("/testOtchett")
    public String showForecastotestOtchett(Model model) {
         Admin admin = adminService.getCurrentAdmin();
         List<OrderDTO> orderList = orderService.convertToOrderDTOs(orderService.getOrdersByManufacturerId(admin.getId()));
         model.addAttribute("orderList", orderList);
         return "testOtchet";
    }


    @GetMapping("/testOtchet")
    public ResponseEntity<List<OrderDTO>> showForecastotestOtchet() {
        Admin admin = adminService.getCurrentAdmin();
        List<OrderDTO> orderDTOs = orderService.convertToOrderDTOs(orderService.getOrdersByManufacturerId(admin.getId()));
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/showInfoAll")
    public String showInfoAll(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<OrderDTO> orderList = orderService.convertToOrderDTOs(orderService.getOrdersByManufacturerId(admin.getId()));
        model.addAttribute("orderList", orderList);
        List<ProductDTO> productDTOs = productService.convertToProductDTOs(productService.getAllProductWithImagesByAdmin(admin.getId()));
        model.addAttribute("productDTOs", productDTOs);
        List<Delivery> deliveries = deliveryService.getAllInfoDeliveryByAdmin(admin.getId());
        model.addAttribute("deliveries", deliveries);
        return "testr.html";
    }


    @GetMapping("/testOrderOrderProductJson")
    public ResponseEntity<List<OrderDTO>> showOrderOrderProductJson() {
        Admin admin = adminService.getCurrentAdmin();
        List<OrderDTO> orderDTOs = orderService.convertToOrderDTOs(orderService.getOrdersByManufacturerId(admin.getId()));
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/testProductJson")
    public ResponseEntity<List<ProductDTO>> showProductJson() {
        Admin admin = adminService.getCurrentAdmin();
        List<ProductDTO> productDTOs = productService.convertToProductDTOs(productService.getAllProductWithImagesByAdmin(admin.getId()));
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/testDeliveryJson")
    public ResponseEntity<List<Delivery>> showDeliveryJson() {
        Admin admin = adminService.getCurrentAdmin();
        List<Delivery> deliveries = deliveryService.getAllInfoDeliveryByAdmin(admin.getId());
        return ResponseEntity.ok(deliveries);
    }


































































    @GetMapping("/totalOrderProduct")
    public ResponseEntity<Integer> getTotalOrdersForManufacturer() {
        Admin admin = adminService.getCurrentAdmin();
        Integer totalOrders = orderProductService.getTotalOrdersForManufacturer(admin.getId());
        return ResponseEntity.ok().body(totalOrders);

    }


    @GetMapping("/totalQuantityForManufacturer")
    public ResponseEntity<Integer> getTotalQuantityForManufacturer() {
        Admin admin = adminService.getCurrentAdmin();
        Integer totalQuantity = orderProductService.getTotalQuantityForManufacturer(admin.getId());
        return ResponseEntity.ok().body(totalQuantity);
    }


    @GetMapping("/sumTotalCostOrdersForManufacturer")
    public ResponseEntity<Integer> getSumTotalQuantityForManufacturer() {
        Admin admin = adminService.getCurrentAdmin();
        Integer sumTotalCostOrders = orderProductService.getSumTotalCostOrdersForManufacturer(admin.getId());
        return ResponseEntity.ok().body(sumTotalCostOrders);
    }


    @GetMapping("/sumTotalProfitForManufacturerLastWeek")
    public ResponseEntity<Integer> getTotalProfitForManufacturerLastWeek() {
        Admin admin = adminService.getCurrentAdmin();
        Integer sumTotalProfitForManufacturerLastWeek = orderProductService.getTotalProfitForManufacturerLastWeek(admin.getId());
        return ResponseEntity.ok().body(sumTotalProfitForManufacturerLastWeek);
    }

    @GetMapping("/sumTotalProfitForManufacturerLastMonth")
    public ResponseEntity<Integer> getTotalProfitForManufacturerLastMonth() {
        Admin admin = adminService.getCurrentAdmin();
        Integer sumTotalProfitForManufacturerLastMonth = orderProductService.getTotalProfitForManufacturerLastMonth(admin.getId());
        return ResponseEntity.ok().body(sumTotalProfitForManufacturerLastMonth);
    }

    @GetMapping("/sumTotalCostForManufacturerLastSixMonth")
    public ResponseEntity<Integer> getTotalCostForManufacturerLastSixMonth() {
        Admin admin = adminService.getCurrentAdmin();
        Integer sumTotalCostForManufacturerLastSixMonth = orderProductService.getTotalCostForManufacturerLastSixMonth(admin.getId());
        return ResponseEntity.ok().body(sumTotalCostForManufacturerLastSixMonth);
    }



/*    @GetMapping("/monthlyData")
    public List<MonthlyDataDTO> getMonthlyDataForManufacturer() {
        Admin admin = adminService.getCurrentAdmin();
        return orderProductService.getMonthlyDataForManufacturer(admin.getId());
    }*/

    @GetMapping("/monthlyData")
    public ResponseEntity<List<MonthlyDataDTO>> getMonthlyDataForManufacturer() {
        Admin admin = adminService.getCurrentAdmin();
        List<MonthlyDataDTO> monthlyData = orderProductService.getMonthlyDataForManufacturer(admin.getId());
        return ResponseEntity.ok(monthlyData);
    }



}

    /*   @PostMapping("/createOrder")
       public String createOrder(@RequestParam("productIds") List<Integer> productIds,
                                 @RequestParam("deliveryAddress") String deliveryAddress,
                                 @RequestParam("cardNumber") int cardNumber,
                                 @RequestParam("quantities") List<Integer> quantities) {
           User user = userService.getCurrentUser();
           orderService.createOrder(user.getId(), productIds, deliveryAddress,cardNumber, quantities);
           return "redirect:/orderForm";
       }*/

   /* @GetMapping("/Spros")
    public String getSpros(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<Order> orders = orderService.getNewOrdersByManufacturerId(admin.getId());

        // Конвертация списка заказов в формат JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String ordersJson = "";
        try {
            ordersJson = objectMapper.writeValueAsString(orders);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("ordersJson", ordersJson);
        return "Spros";
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getOrders() {
        Admin admin = adminService.getCurrentAdmin();
        List<Order> orders = orderService.getNewOrdersByManufacturerId(admin.getId());
        List<OrderDTO> orderDTOs = orderService.convertToOrderDTOs(orders);
        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }*/

/*    @GetMapping("/forecast")
    public String showForecast(Model model) throws JsonProcessingException {
        Admin admin = adminService.getCurrentAdmin();
        List<OrderDTO> orderDTOs = orderService.convertToOrderDTOs(orderService.getNewOrdersByManufacturerId(admin.getId()));
        model.addAttribute("orders", orderDTOs);
        model.addAttribute("ordersJson", new ObjectMapper().writeValueAsString(orderDTOs));
        return "Spros";
    }*/
   /* @GetMapping("/testOtchet")
    public String showForecastotestOtchet(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<OrderDTO> orderDTOs = orderService.convertToOrderDTOs(orderService.getNewOrdersByManufacturerId(admin.getId()));
        model.addAttribute("orderDTOs", orderDTOs);
        return "testOtchet";
    }*/
  /* @GetMapping(name = "/testOtchet", produces = "application/json")
    @ResponseBody
    public List<OrderDTO> showForecastotestOtchet(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<OrderDTO> orderDTOs = orderService.convertToOrderDTOs(orderService.getNewOrdersByManufacturerId(admin.getId()));
        return orderDTOs;
    }*/
 /*   @GetMapping("/testOtchet")
    public String showForecastotestOtchet(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<OrderDTO> orderDTOs = orderService.convertToOrderDTOs(orderService.getNewOrdersByManufacturerId(admin.getId()));

        // Преобразуйте orderDTOs в формат, понятный JavaScript
        List<Map<String, Object>> formattedOrderDTOs = new ArrayList<>();
        for (OrderDTO orderDTO : orderDTOs) {
            Map<String, Object> formattedOrderDTO = new HashMap<>();
            formattedOrderDTO.put("id", orderDTO.getId());
            formattedOrderDTO.put("dateOrder", orderDTO.getDateOrder().toString());
            formattedOrderDTO.put("sumAllProduct", orderDTO.getSumAllProduct());

            // Преобразуйте orderProducts в формат, понятный JavaScript
            List<Map<String, Object>> formattedOrderProducts = new ArrayList<>();
            for (OrderProductDTO orderProductDTO : orderDTO.getOrderProducts()) {
                Map<String, Object> formattedOrderProduct = new HashMap<>();
                formattedOrderProduct.put("id", orderProductDTO.getId());
                formattedOrderProduct.put("productName", orderProductDTO.getProductName());
                formattedOrderProduct.put("quantity", orderProductDTO.getQuantity());
                formattedOrderProducts.add(formattedOrderProduct);
            }

            formattedOrderDTO.put("orderProducts", formattedOrderProducts);
            formattedOrderDTOs.add(formattedOrderDTO);
        }

        model.addAttribute("orderDTOs", formattedOrderDTOs);
        return "testOtchet";
    }*/


 /*   @GetMapping("/testOtchet")
    public String showForecastotestOtchet(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<OrderDTO> orderDTOs = orderService.convertToOrderDTOs(orderService.getNewOrdersByManufacturerId(admin.getId()));

        ObjectMapper objectMapper = new ObjectMapper();
        String orderDTOsJson = "[]";  // По умолчанию, если нет данных

        try {
            orderDTOsJson = objectMapper.writeValueAsString(orderDTOs);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("orderDTOsJson", orderDTOsJson);
        return "testOtchet";
    }*/

/*    @GetMapping("/list")
    public String orderList(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "/page.html"; // Имя вашего HTML-шаблона
    }*/
// Дополнительные логика и передача данных в модель, если необходимо
      /*  User currentUser = userService.getCurrentUser();

        List<Product> allProducts = productService.getAllProducts();

        // Передайте список продуктов в модель
        model.addAttribute("allProducts", allProducts);
        // Получи список карт пользователя
        List<Cart> userCards = cartService.getUserCards(currentUser.getId());

        // Передай список карт в модель
        model.addAttribute("userCards", userCards);

        // Другие атрибуты модели, если есть*/