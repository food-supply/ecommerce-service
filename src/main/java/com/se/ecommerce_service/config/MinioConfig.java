package com.se.ecommerce_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class MinioConfig {
    @Bean
    public MinioClient minioClient(MinioProperties props) {
        return MinioClient.builder()
            .endpoint(props.getUrl())
            .credentials(props.getAccessKey(), props.getSecretKey())
            .build();
    }
}
