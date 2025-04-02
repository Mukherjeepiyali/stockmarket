package com.example.MongoSpring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // ✅ Disable CSRF for API-based authentication
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()  // ✅ Authentication routes (Public)
                .requestMatchers("/api/demat/**").permitAll() // ✅ Allow ALL Demat APIs
                .requestMatchers("/stocks/**").permitAll() 
                .requestMatchers("/api/admin/**").hasRole("ADMIN")  // ✅ Admin routes require ADMIN role
                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")  // ✅ Users and Admins can access
                .requestMatchers("/api/public/**").permitAll()  // ✅ Public APIs
                .requestMatchers("/**").permitAll() // ✅ TEMPORARY: Allow all (Adjust for production)
                .anyRequest().authenticated()  
            )
            .httpBasic();  // ✅ Enable Basic Authentication

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder().encode("admin123"))  
            .roles("ADMIN")  
            .build();

        UserDetails user = User.withUsername("user")
            .password(passwordEncoder().encode("user123"))  
            .roles("USER")  
            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
