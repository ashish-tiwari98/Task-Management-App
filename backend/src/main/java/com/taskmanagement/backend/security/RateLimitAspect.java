package com.taskmanagement.backend.security;

import com.taskmanagement.backend.service.RateLimiterService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RateLimitAspect {
    private static final Logger logger = LoggerFactory.getLogger(RateLimitAspect.class);
    private final RateLimiterService rateLimiterService;

    public RateLimitAspect(RateLimiterService rateLimiterService) {
        this.rateLimiterService = rateLimiterService;
    }

    @Around("@annotation(rateLimited)")
    public Object applyRateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("🔍 RateLimitAspect invoked for: {}", joinPoint.getSignature().toShortString()); // Log the method

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimited rateLimited = method.getAnnotation(RateLimited.class);
        int limit = rateLimited.value();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "anonymous";

        logger.info("🔍 Checking method-based rate limit for user: {}", username);

        if (!rateLimiterService.isAllowedByUser(username, limit)) {
            logger.warn("🚫 Rate limit exceeded for user: {}", username);
            throw new RuntimeException("Rate limit exceeded for user: " + username);
        }

        return joinPoint.proceed();
    }
}
