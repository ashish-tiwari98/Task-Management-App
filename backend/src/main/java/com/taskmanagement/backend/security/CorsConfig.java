//package com.taskmanagement.backend.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.List;
//
//@Configuration
//public class CorsConfig {
//
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(List.of("http://localhost:5173")); // Allow frontend
//        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));//allow all HTTP headers
//        config.setAllowCredentials(true); // Allow cookies/session sharing
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);//Applies the CORS configuration to all API endpoints (/**)
//
//        return new CorsFilter(source);
//    }
//}
//
///*
//Preflight (OPTIONS request) is sent automatically by browsers before some cross-origin requests.
//It checks if the server allows the request method, headers, and credentials.
//The backend must respond correctly to OPTIONS requests to prevent CORS errors.
//Including "OPTIONS" in setAllowedMethods ensures that preflight requests succeed.
// */
//corsconfig not needed and is redundant as spring security applies stricter CORS rules than the standalone CorsFilter from CorsConfig.java
//Even if CorsConfig is defined, once Spring Security is enabled, it overrides global CORS settings.
