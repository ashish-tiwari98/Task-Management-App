package com.taskmanagement.backend.security;

import com.taskmanagement.backend.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component //it marks this class as Spring managed bean so it gets automatically detected and registered
public class RateLimiterInterceptor implements HandlerInterceptor {//this allows intercepting HTTP requests before, during or after controller execution
    private static final Logger logger = LoggerFactory.getLogger(RateLimiterInterceptor.class);
    private final RateLimiterService rateLimiterService;

    public RateLimiterInterceptor(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }
//above segment injects RateLimiterService which handles API request tracking using Redis
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { //runs before request reaches controller
        logger.info("🔍 Interceptor running BEFORE controller for IP: {}", request.getRemoteAddr());
        String clientIp = request.getRemoteAddr();

        // Apply rate limiting based on IP
        logger.info("Checking IP-based rate limit for IP: {}", clientIp);
        if (!rateLimiterService.isAllowedByIp("ip:" + clientIp)) {
            logger.warn("Rate limit exceeded for IP: {}", clientIp);
            response.setStatus(429);
            response.getWriter().write("Rate limit exceeded for your IP. Try again later.");
            return false;
        }
        return true;
    }
}
