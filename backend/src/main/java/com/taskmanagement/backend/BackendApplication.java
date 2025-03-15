package com.taskmanagement.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan(basePackages = {"com.taskmanagement.backend"})
@EnableAspectJAutoProxy
/*
@SpringBootApplication is Annotation that combines below:
@Configuration → Defines configuration settings.
@EnableAutoConfiguration → Auto-configures dependencies like Spring Boot, JPA, and PostgreSQL.
@ComponentScan → Scans the package for components like controllers, services, and repositories.
*/

public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		/*
		Above command performs below:
		Starts the Spring Boot application.
		Loads all beans (components, controllers, repositories, services, etc.).
		Sets up an embedded Tomcat server (default port 8080).
		 */
		System.out.println("✅ Backend is running on http://localhost:8080");
	}

}
