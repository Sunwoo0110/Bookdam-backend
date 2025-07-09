package com.sunwoo.bookdam.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**",
                                "/h2-console/**",
                                "/login", "/signup", "/public/**"
                        ).permitAll()   // 누구나 접근 가능
                        .anyRequest().authenticated() // 그 외 인증 필요
                )
                .formLogin(Customizer.withDefaults()) // 폼 로그인 기본
                .logout(Customizer.withDefaults());

        return http.build();
    }
}
