package com.in6225.IMS.config;

import com.in6225.IMS.security.CustomUserDetailsService;
import com.in6225.IMS.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable()) // Modern lambda DSL style (optional but recommended)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Modern lambda DSL style
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Authentication endpoints remain public
                        .requestMatchers("/api/account/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // --- Specific Rules Start ---
                        .requestMatchers("/api/admin/**").hasAnyRole("ADMIN")
                        // PUT and DELETE restricted to MANAGER and ADMIN
                        .requestMatchers(HttpMethod.PUT, "/api/products/**", "/api/transactions/**").hasAnyRole("MANAGER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**", "/api/transactions/**", "/api/alerts/**").hasAnyRole("MANAGER", "ADMIN")

                        // GET and POST allowed for any authenticated user
                        // If GET/POST should be public (no login required), change authenticated() to permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**", "/api/transactions/**", "/api/alerts/**","/api/dashboard/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/products/**", "/api/transactions/**", "/api/alerts/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/alerts/**").authenticated()


                        // Any other request requires authentication (good practice fallback)
                        .anyRequest().authenticated()
                )
                // Add the JWT filter before the standard username/password filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
