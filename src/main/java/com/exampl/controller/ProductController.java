package com.exampl.controller;

import com.exampl.domain.Admin;
import com.exampl.domain.Cart;
import com.exampl.domain.ImageEntity;
import com.exampl.domain.Product;
import com.exampl.domain.Stock;
import com.exampl.domain.User;
import com.exampl.repository.ProductRepository;
import com.exampl.service.AdminService;
import com.exampl.service.CartService;
import com.exampl.service.ImageService;
import com.exampl.service.ProductService;
import com.exampl.service.StockService;
import com.exampl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@RequestMapping("/product")
public class ProductController {

    AdminService adminService;
    ProductService productService;

    ImageService imageService;

    StockService stockService;

    ProductRepository productRepository;

    UserService userService;

    CartService cartService;

    @Autowired
    public ProductController(AdminService adminService, ProductService productService, ImageService imageService, StockService stockService, ProductRepository productRepository, UserService userService, CartService cartService) {
        this.adminService = adminService;
        this.productService = productService;
        this.imageService = imageService;
        this.stockService = stockService;
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<Stock> stockList = stockService.getAllStockByAdmin(admin.getId());
        model.addAttribute("stockList", stockList);
        model.addAttribute("product", new Product());
        List<Product> products = productService.getAllProductWithImagesByAdmin(admin.getId());
        model.addAttribute("products", products);
        /*List<Product> products = productService.getAllProductWithImages();
        model.addAttribute("products", products);*/
        return "productCrud.html";
    }

    @PostMapping("/add")
    public String addProductWithImage(@ModelAttribute("product") Product product, @RequestParam("files") MultipartFile[] files) {
        if (files != null && files.length > 0) {
            Admin admin = adminService.getCurrentAdmin();
            if (admin != null) {
                product.setAdminId(admin.getId());
                product.setManufacturer(admin.getNameOrganizationAdmin());

                // Сначала сохраните продукт
                productService.addProductWithOutImage(product);

                // Теперь обработайте все загруженные изображения и свяжите их с продуктом
                for (MultipartFile file : files) {
                    ImageEntity imageEntity = imageService.uploadImage(file, admin.getId(), product);
                    if (imageEntity != null) {
                        product.getImages().add(imageEntity);
                    }
                }
            }
        }

        return "redirect:/product/addProduct";
    }


    @GetMapping("/all")
    public String getAllProducts(Model model) {

        List<Product> productList = productRepository.findAll();

        model.addAttribute("products", productList);
        List<String> organizationNames = adminService.getAllOrganizationNames();
        model.addAttribute("organizationNames", organizationNames);

        return "shop.html";
    }

    @PostMapping("/viewProduct")
    public String viewProduct(@RequestParam int productId, Model model) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
            return "product-details.html";
        } else {
            return "error";
        }
    }


    @GetMapping("/{productId}")
    public String viewPjroduct(@PathVariable int productId, Model model) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            model.addAttribute("product", product);
            User currentUser = userService.getCurrentUser();
            List<Product> allProducts = productService.getAllProducts();
            model.addAttribute("allProducts", allProducts);
            List<Cart> userCards = cartService.getUserCards(currentUser.getId());
            model.addAttribute("userCards", userCards);
            return "product-details.html";
        } else {
            return "error";
        }
    }

    @GetMapping("/products")
    public String listProductsWithImages(Model model) {
        Admin admin = adminService.getCurrentAdmin();
        List<Product> products = productService.getAllProductWithImagesByAdmin(admin.getId());
        model.addAttribute("products", products);
        return "productCrud.html";
    }

  

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable int productId) {
        productService.deleteProduct(productId);
        return "redirect:/product/addProduct";
    }

    @GetMapping("/edit/{productId}")
    public String showEditProductForm(@PathVariable("productId") int productId, Model model) {
        Admin admin = adminService.getCurrentAdmin();
        if (admin == null) {

            return "redirect:/login";
        }

        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "testPage.html";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product updatedProduct) {
        Admin admin = adminService.getCurrentAdmin();
        if (admin == null) {

            return "redirect:/login";
        }

        productService.updateProduct(updatedProduct, admin.getId());
        System.out.println("ID продукта, полученный из формы: " + updatedProduct.getId());
        System.out.println("айди администратора  = " + admin.getId());
        System.out.println("мы зашли в метод, и выполнили изменения");
        return "redirect:/product/addProduct"; // Перенаправление на страницу списка продуктов
    }

    @GetMapping("/getProductPrice")
    public ResponseEntity<Integer> getProductPrice(@RequestParam int id) {
        Integer productPrice = productService.getProductPriceById(id);
        return ResponseEntity.ok(productPrice);
    }

    @GetMapping("/search")
    public String searchByName(Model model, @RequestParam("productName") String productName) {
        List<Product> searchResults = productService.searchByNameProduct(productName);
        model.addAttribute("products", searchResults);
        List<String> organizationNames = adminService.getAllOrganizationNames();
        model.addAttribute("organizationNames", organizationNames);
        return "shop";
    }

    @GetMapping("/searchSubcategory")
    public String searchBySubcategory(Model model, @RequestParam("subcategory") String subcategory) {
        List<Product> searchResults = productService.searchBySubcategory(subcategory);
        model.addAttribute("products", searchResults);
        List<String> organizationNames = adminService.getAllOrganizationNames();
        model.addAttribute("organizationNames", organizationNames);
        return "shop";
    }


    @GetMapping("/searchCategory")
    public String searchByCategory(Model model, @RequestParam("category") String category) {
        List<Product> searchResults = productService.searchByCategory(category);
        model.addAttribute("products", searchResults);
        List<String> organizationNames = adminService.getAllOrganizationNames();
        model.addAttribute("organizationNames", organizationNames);
        return "shop";
    }


    @GetMapping("/searchСhapter")
    public String searchByСhapter(Model model, @RequestParam("сhapter") String сhapter) {
        List<Product> searchResults = productService.searchByСhapter(сhapter);
        model.addAttribute("products", searchResults);
        List<String> organizationNames = adminService.getAllOrganizationNames();
        model.addAttribute("organizationNames", organizationNames);
        return "shop";
    }

    @GetMapping("/searchManufacturer")
    public String searchByManufacturer(Model model, @RequestParam("manufacturer") String manufacturer) {
        List<Product> searchResults = productService.searchByManufacturer(manufacturer);
        model.addAttribute("products", searchResults);
        List<String> organizationNames = adminService.getAllOrganizationNames();
        model.addAttribute("organizationNames", organizationNames);
        return "shop";
    }

    @GetMapping("/allProductsSort")
    public String showAllProducts(Model model, @RequestParam(name = "sort") String sort) {
        List<Product> allProducts = productService.getAllProducts();

        if ("min".equals(sort)) {
            allProducts.sort(Comparator.comparing(Product::getTotalPrice));
        } else if ("max".equals(sort)) {
            allProducts.sort(Comparator.comparing(Product::getTotalPrice).reversed());
        }

        model.addAttribute("products", allProducts);
        List<String> organizationNames = adminService.getAllOrganizationNames();
        model.addAttribute("organizationNames", organizationNames);
        return "shop";
    }



}
  
/*    @GetMapping("/images/{imageId}")
    public String getImageById(@PathVariable Long imageId, Model model) {
        ImageEntity image = imageService.getImageById(imageId);

        if (image != null && image.getImageData() != null) {
            String base64Image = image.getImageData();
            model.addAttribute("base64Image", base64Image);
            return "image.html"; // Создайте HTML-шаблон "image.html" для отображения изображения
        } else {
            // Обработка случая, если изображение не найдено
            return "huita.html"; // Создайте HTML-шаблон "image-not-found.html" для этого случая
        }
    }*/
/* @GetMapping("/images/{imageId}")
    public String getImageById(@PathVariable Long imageId, Model model) {
        ImageEntity image = imageService.getImageById(imageId);

        if (image != null && image.getImageData() != null) {
            byte[] imageBytes = image.getImageData();
            String base64Image = Base64.encodeBase64String(imageBytes);
            model.addAttribute("base64Image", base64Image);
            return "image.html"; // Создайте HTML-шаблон "image.html" для отображения изображения
        } else {
            // Обработка случая, если изображение не найдено
            return "image-not-found.html"; // Создайте HTML-шаблон "image-not-found.html" для этого случая
        }
    }*/
/*  @GetMapping("/products")
    public String listProductsWithImages(Model model) {
        List<Product> products = productService.getAllProductWithImages();

        model.addAttribute("products", products);
        return "workWithProduct.html"; // Создайте HTML-шаблон "products-with-images.html" для отображения списка продуктов и их изображений
    }*/
/*    @GetMapping("/edit/{productId}")
    public String showEditProductForm(@PathVariable int productId, Model model) {
        // Здесь вы можете загрузить продукт из базы данных по его ID и передать его в модель
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);

        // Вернуть имя шаблона для модального окна
        return "updateProdiuct.htmll"; // Замените это на фактическое имя вашего шаблона
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") Product updatedProduct) {
        // Здесь вызовите ваш сервис для обновления продукта
        productService.updateProduct(updatedProduct);

        // После успешного обновления, выполните необходимые действия, например, перенаправление или отображение сообщения об успехе
        return "redirect:/product/addProduct"; // Пример перенаправления на страницу списка продуктов
    }*/

   /*    @PostMapping("/add")
        public String addProductWithImage(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file) {
            if (!file.isEmpty()) {
                ImageEntity imageEntity = imageService.uploadImage(file);
                if (imageEntity != null) {
                    product.setImages(Collections.singletonList(imageEntity));
                }
            }

            productService.addProductWithOutImage(product);
            return "redirect:/products";
        }*/
/*    @PostMapping("/add")
    public String addProductWithImage(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            Admin admin = adminService.getCurrentAdmin();
            if (admin != null) {
                product.setAdminId(admin.getId());
                product.setManufacturer(admin.getNameOrganizationAdmin());

                // Сначала сохраните продукт
                productService.addProductWithOutImage(product);

                // Теперь создайте изображение, устанавливая связь с уже сохраненным продуктом
                ImageEntity imageEntity = imageService.uploadImage(file, admin.getId(), product);
                if (imageEntity != null) {
                    product.setImages(Collections.singletonList(imageEntity));
                }
            }
        }

        return "redirect:/products";
    }*/

   /* @PostMapping("/add")
    public String addProductWithImage(@ModelAttribute("product") Product product, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            Admin admin = adminService.getCurrentAdmin();
            if (admin != null) {
                product.setAdminId(admin.getId());
                product.setManufacturer(admin.getNameOrganizationAdmin());

                // Сначала сохраните продукт
                productService.addProductWithOutImage(product);

                // Теперь создайте изображение, устанавливая связь с уже сохраненным продуктом
                ImageEntity imageEntity = imageService.uploadImage(file, admin.getId(), product);
                if (imageEntity != null) {
                    product.setImages(Collections.singletonList(imageEntity));
                }
            }
        }

        return "redirect:/product/addProduct";
    }*/