package com.example.habitmanager.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CorsConfigTest {

    @Test
    void corsConfigurerBeanIsCreatedAndRegistersMappings() {
        CorsConfig corsConfig = new CorsConfig();

        WebMvcConfigurer configurer = corsConfig.corsConfigurer();

        assertNotNull(configurer);
        assertDoesNotThrow(() -> configurer.addCorsMappings(new CorsRegistry()));
    }
}
