package com.example.MongoSpring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        
        // ✅ Allow multiple frontend origins (adjust for production)
        corsConfiguration.setAllowedOrigins(List.of(
            "http://localhost:3000",
            "https://yourdomain.com"
        ));

        // ✅ Allow all standard HTTP methods
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // ✅ Allow any headers
        corsConfiguration.setAllowedHeaders(List.of("*"));

        // ✅ Allow credentials for authentication (cookies, sessions)
        corsConfiguration.setAllowCredentials(true); 

        // ✅ Register CORS configuration
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}
