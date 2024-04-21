package com.exampl.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultipartConfig {

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8"); // Устанавливаем кодировку
        resolver.setMaxUploadSizePerFile(100 * 1024 * 1024); // Максимальный размер файла (в данном случае 5 МБ)
        return resolver;
    }
}
