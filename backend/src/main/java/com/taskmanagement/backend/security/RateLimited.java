package com.taskmanagement.backend.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //defines this annotation is available at runtime
@Target(ElementType.METHOD) //specifies where this annotation can be used
//this is created for using Method-Level Rate Limiting directly with @RateLimited Annotation
// Annotation in Java provide metadata for code
//They do not execute logic directly but can be used with AOP (Aspect Oriented Programming) or interceptors to modify behavior dynamically
public @interface RateLimited {
    int value() default 5; //default to 5 requests per minute
    //this is an attribute of the annotation. it acts as configurable parameter, meaning you can specify a different rate limit per method
}
