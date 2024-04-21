package com.exampl.service;

import com.exampl.DTO.ProductDTO;
import com.exampl.domain.Admin;
import com.exampl.domain.ImageEntity;
import com.exampl.domain.Product;
import com.exampl.repository.ImageRepository;
import com.exampl.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final AdminService adminService;

    private final ImageRepository imageRepository;
    private final ImageService imageService;


    @Autowired
    public ProductService(ProductRepository productRepository, AdminService adminService, ImageRepository imageRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.adminService = adminService;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
    }

    public void addProductWithOutImage(Product product) {
        Admin admin = adminService.getCurrentAdmin();
        if (admin != null) {
            product.setAdminId(admin.getId());
            product.setManufacturer(admin.getNameOrganizationAdmin());
            productRepository.save(product);
        }

    }

    public List<Product> getAllProductWithImagesByAdmin(int adminId) {
        List<Product> products = productRepository.findAllByAdminId(adminId);
        for (Product product : products) {
            List<ImageEntity> images = imageRepository.findAllByProductId(product.getId());
            product.setImages(images);
        }
        return products;
    }


    public List<Product> getAllProductWithImages() {

        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            List<ImageEntity> images = imageRepository.findAllByProductId(product.getId());
            product.setImages(images);
        }
        return products;
    }

    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }

    public Product getProductById(int productId) {
        // Вам нужно реализовать получение продукта по его идентификатору из репозитория.
        // Это может потребовать использования JPA, Hibernate или других средств доступа к данным.
        return productRepository.findById(productId).orElse(null);
    }
    public void updateProduct(Product updatedProduct, int adminId) {
        // Поиск существующего продукта в базе данных
        Admin admin = adminService.getCurrentAdmin(); // Получаем админа по его ID
        if (admin != null) {
            Product existingProduct = productRepository.findById(updatedProduct.getId()).orElse(null);
            System.out.println("выводим айди продукта который надо найти через репозиторий === "+existingProduct);
            if (existingProduct != null) {
                // Обновление только выбранных полей
                if (updatedProduct.getNameProduct() != null) {
                    existingProduct.setNameProduct(updatedProduct.getNameProduct());
                }
                if (updatedProduct.getDescription() != null) {
                    existingProduct.setDescription(updatedProduct.getDescription());
                }
                if (updatedProduct.getCategory() != null) {
                    existingProduct.setCategory(updatedProduct.getCategory());
                }
                if (updatedProduct.getSubcategory() != null) {
                    existingProduct.setSubcategory(updatedProduct.getSubcategory());
                }
                if (updatedProduct.getChapter() != null) {
                    existingProduct.setChapter(updatedProduct.getChapter());
                }
                if (updatedProduct.getTotalPrice() != null) {
                    existingProduct.setTotalPrice(updatedProduct.getTotalPrice());
                }

                if (updatedProduct.getPurchasePrice() != null) {
                    existingProduct.setPurchasePrice(updatedProduct.getPurchasePrice());
                }
                if (updatedProduct.getCount() != null) {
                    existingProduct.setCount(updatedProduct.getCount());
                }
                System.out.println("мы зашли в метод, и выполнили изменения");
                // Сохранение обновленного продукта
                productRepository.save(existingProduct);
                System.out.println("мы зашли в метод, и сохранили изменения");

            } else {
                // Если продукт не найден, вы можете выбрать подходящий способ обработки этой ситуации
                System.out.println("мы зашли в метод, и не нашли продукт для изменения");
            }
        }
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Integer getProductPriceById(int productId) {
        Product product = productRepository.findById(productId).orElse(null);
        return product != null ? product.getTotalPrice() : 0;
    }



























    public List<Product> searchByNameProduct(String productName) {
        return productRepository.findAllByNameProduct(productName);
    }


    public List<Product> searchBySubcategory(String subcategory) {
        return productRepository.findAllBySubcategory(subcategory);
    }


    public List<Product> searchByСhapter(String сhapter) {
        return productRepository.findAllBySubcategory(сhapter);
    }

    public List<Product> searchByCategory(String category) {
        return productRepository.findAllByCategory(category);
    }
    public List<Product> searchByManufacturer(String manufacturer) {
        return productRepository.findAllByManufacturer(manufacturer);
    }








































































    public List<ProductDTO> convertToProductDTOs(List<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setNameProduct(product.getNameProduct());
            productDTO.setPurchasePrice(product.getPurchasePrice());
            productDTO.setCount(product.getCount());

            // Другие необходимые поля...

            productDTOs.add(productDTO);
        }
        return productDTOs;
    }











}
