package com.taskmanagement.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter { //this ensures it executes once per request and prevents multiple executions for same request

    private final JwtUtil jwtUtil; //for extracting, validating and parsing JWT
    private final UserDetailsService userDetailsService; // Loads user details from db and helps in verifying if user exists

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException { // executes on every request , extracts and vaidates the JWT and if valid it sets authentication in SecurityContextHolder
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {//ensures The Security Context does not already have an authentication object.
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //setting authentication in security context
            if (jwtUtil.validateToken(token)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());//this can be extended for role based access control
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);//Stores the authentication, making the user authenticated for this request.
            }
        }
        filterChain.doFilter(request, response);//Allows the request to proceed after authentication.
    }
}

/*
Full flow
1. Frontend Sends a Request
2. Filter Executes:
Extracts JWT from the request.
Decodes username from the JWT.
Loads user details from DB.
Validates the JWT (checks expiration, signature, etc.).
Authenticates user (stores authentication in SecurityContextHolder).
3. Spring Security Authorizes the Request ✅


AuthenticationProvider is used only for login authentication.
JwtAuthenticationFilter authenticates every request after login.
SecurityContextHolder stores authentication, so Spring Security knows the user is logged in.
 */
