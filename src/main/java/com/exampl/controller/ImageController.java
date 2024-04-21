package com.exampl.controller;

import com.exampl.domain.ImageEntity;
import com.exampl.repository.ImageRepository;
import com.exampl.service.ImageService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public ImageController(ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }


    @GetMapping("/{productId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int productId) {
        // Получите изображение, связанное с productId, из базы данных или откуда-либо еще
        // Ваш код для получения изображения может выглядеть по-разному в зависимости от того, как вы его храните

        // Пример: получение изображения из базы данных
        List<ImageEntity> imageEntities = imageRepository.findAllByProductId(productId);

        if (!imageEntities.isEmpty()) {
            // Предположим, что у нас есть только одно изображение для productId
            ImageEntity imageEntity = imageEntities.get(0);

            if (imageEntity.getImageData() != null) {
                byte[] imageBytes = imageEntity.getImageData();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Укажите соответствующий MIME-тип

                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }
        }

        // Обработка случая, если изображение не найдено
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




    @GetMapping("/{productId}/{imageId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int productId, @PathVariable Long imageId) {
        // Получите изображение по imageId и productId, например, из базы данных

        // Пример: получение изображения из базы данных по imageId и productId
        ImageEntity imageEntity = imageRepository.findByProductIdAndId(productId, imageId);

        if (imageEntity != null && imageEntity.getImageData() != null) {
            byte[] imageBytes = imageEntity.getImageData();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Укажите соответствующий MIME-тип

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        }

        // Обработка случая, если изображение не найдено
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }







}
/*    @GetMapping("/{imageId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long imageId) {
        // Получите изображение по imageId, из базы данных или откуда-либо еще
        // Ваш код для получения изображения может выглядеть по-разному в зависимости от того, как вы его храните

        // Пример: получение изображения из базы данных по imageId
        Optional<ImageEntity> imageEntityOptional = imageRepository.findById(imageId);

        if (imageEntityOptional.isPresent()) {
            ImageEntity imageEntity = imageEntityOptional.get();

            if (imageEntity.getImageData() != null) {
                byte[] imageBytes = imageEntity.getImageData();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // Укажите соответствующий MIME-тип

                return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
            }
        }

        // Обработка случая, если изображение не найдено
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/