package com.taskmanagement.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    Spring Security expects a UserDetailsService implementation to fetch user details from your database.
//    You must implement UserDetailsService(interface) to tell Spring how to retrieve user information.
//    interface is like below
//    public interface UserDetailsService {
//    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
//}

    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, UserDetailsService userDetailsService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.userDetailsService = userDetailsService; // Custom service to load users from the database
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Allows the frontend (e.g., http://localhost:5173) to communicate with the backend. Without this, browsers will block cross-origin requests.
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/register/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // Only admins can access /admin/*
                        .requestMatchers("/user/**").hasAuthority("ROLE_USER") // Only users can access /user/*
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // ✅ Ensure JWT is processed before login. Extracts user information from JWT and sets it in SecurityContext.
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // Fetches user details and verifies credentials.
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Proper CORS Configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Update frontend URL
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

/*
1️⃣ User tries to log in.
2️⃣ SecurityConfig → Uses AuthenticationProvider.
3️⃣ AuthenticationProvider → Calls UserDetailsService.loadUserByUsername(username).
4️⃣ UserDetailsService → Queries UserRepository.
5️⃣ UserRepository → Fetches user from database.
6️⃣ AuthenticationProvider → Verifies password using BCrypt.
7️⃣ If valid, user is authenticated and assigned roles. ✅


End-to-End Execution Flow
1. Login Request (/auth/login)

SecurityConfig allows it (permitAll()).
UserDetailsServiceImpl verifies username & password.
If valid, a JWT is generated & returned.

2. Subsequent API Request (Authenticated Route)

JwtAuthenticationFilter extracts the JWT.
It gets the username from the JWT and calls UserDetailsServiceImpl to fetch user details.
If valid, SecurityContextHolder is updated, and the request proceeds.
 */
