package com.byeon.translator.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityService securityService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest().permitAll()
                )
                .formLogin(formLogin -> formLogin
                                .usernameParameter("userId")
                                .loginPage("/member/login") // 로그인 페이지 URL 설정
                                .loginProcessingUrl("/member/login") // 로그인 폼 제출 URL
                                .defaultSuccessUrl("/", true) // 로그인 성공 시 리다이렉트할 URL
                                .failureUrl("/member/login?error=login.failure") // 로그인 실패 시 리다이렉트할 URL
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트할 URL
                        .permitAll()
                )
                .userDetailsService(securityService);
        return http.build();
    }
}
