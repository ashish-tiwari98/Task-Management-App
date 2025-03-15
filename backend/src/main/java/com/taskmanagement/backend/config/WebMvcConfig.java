package com.taskmanagement.backend.config;


import com.taskmanagement.backend.security.RateLimiterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Modifies existing WebMvcConfig.java to apply rate limiting globally:
//To activate interceptor in Spring Boot, it needs to be registered in a WebMvcConfigurer
@Configuration //marks this class as Spring configuration, so Spring Boot will auto-detect and apply it
public class WebMvcConfig implements WebMvcConfigurer { // WebMvcConfigurer: its an interface which allows customization of spring MVC behavior including registering interceptor
    private final RateLimiterInterceptor rateLimiterInterceptor;

    public WebMvcConfig(RateLimiterInterceptor rateLimiterInterceptor) {
        this.rateLimiterInterceptor = rateLimiterInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {//interceptor is like a middleware that executes logic before the controller processes the request
        registry.addInterceptor(rateLimiterInterceptor)
                .addPathPatterns("/tasks/**", "/auth/**"); // Apply rate limiting to tasks & auth APIs globally
    }
}
