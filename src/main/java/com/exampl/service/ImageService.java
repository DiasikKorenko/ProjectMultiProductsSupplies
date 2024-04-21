package com.exampl.service;

import com.exampl.domain.ImageEntity;
import com.exampl.domain.Product;
import com.exampl.repository.ImageRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

/*    public ImageEntity uploadImage(MultipartFile file, int adminId, Product product) {
        try {


            ImageEntity imageEntity = new ImageEntity();
            byte[] imageBytes = file.getBytes();
            String base64Image = Base64.encodeBase64String(imageBytes);
            imageEntity.setImageData(base64Image);
            imageEntity.setAdminId(adminId);
            imageEntity.setProduct(product);

            // Установите админа и продукт, связанные с изображением


            return imageRepository.save(imageEntity);
        } catch (IOException e) {
            // Обработка ошибки при загрузке изображения
            return null;
        }
    }*/


/*
    public ImageEntity uploadImage(MultipartFile file, int adminId, Product product) {
        try {
            ImageEntity imageEntity = new ImageEntity();
            byte[] imageBytes = file.getBytes();
            imageEntity.setImageData(imageBytes);
            imageEntity.setAdminId(adminId);
            imageEntity.setProduct(product);
            return imageRepository.save(imageEntity);
        } catch (IOException e) {
            // Обработка ошибки при загрузке изображения
            return null;
        }
    }
*/


    public ImageEntity uploadImage(MultipartFile file, int adminId, Product product) {
        try {
            ImageEntity imageEntity = new ImageEntity();
            byte[] imageBytes = file.getBytes();
            imageEntity.setImageData(imageBytes);
            imageEntity.setAdminId(adminId);
            imageEntity.setProduct(product);
            return imageRepository.save(imageEntity);
        } catch (IOException e) {
            // Обработка ошибки при загрузке изображения
            return null;
        }
    }


    public List<ImageEntity> getImagesByProductId(int productId) {
        return imageRepository.findAllByProductId(productId);
    }


}