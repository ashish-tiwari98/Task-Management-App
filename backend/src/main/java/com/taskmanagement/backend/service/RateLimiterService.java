package com.taskmanagement.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
//This service interacts with Redis to track API request counts per IP address or User ID
public class RateLimiterService {
    private static final Logger logger = LoggerFactory.getLogger(RateLimiterService.class);
    private final RedisTemplate<String, String> redisTemplate;
    /*
    This is a Spring Boot Redis client used to interact with Redis.
    It allows storing and retrieving values in Redis.
     */
    private final int REQUEST_LIMIT = 10; //max allowed requests per key
    private final Duration EXPIRATION = Duration.ofMinutes(1); // key expires after 1 minute

    public RateLimiterService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // IP-Based Rate Limiting
    public boolean isAllowedByIp(String ip) {
        String redisKey = "rate-limit:ip:" + ip;
        return isAllowed(redisKey, REQUEST_LIMIT); // 10 requests per minute for IP
    }

    public boolean isAllowedByUser(String username, int limit) {
        if (username == null || username.equals("anonymousUser")) {
            logger.warn("Skipping rate limit for anonymous user");
            return true;
        }
        String redisKey = "rate-limit:user:" + username;
        return isAllowed(redisKey, limit);
    }


    public boolean isAllowed(String redisKey, int limit) {
        logger.info("Checking rate limit for key: {}", redisKey);
        Long currentCount = redisTemplate.opsForValue().increment(redisKey, 1); //increments atomically the request count in Redis and if not exist then initializes to 1

        if( currentCount == 1) {
            //It only runs first time to when request is made and explicitly sets expiration time
            redisTemplate.expire(redisKey, EXPIRATION);
            logger.info("New key created in Redis with expiration: {}", EXPIRATION);
        }
        //After this first request call Redis automatically resets count on expiration time set above

        return currentCount <= limit;
    }
}
