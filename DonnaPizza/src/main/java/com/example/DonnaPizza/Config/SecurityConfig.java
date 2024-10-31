package com.example.DonnaPizza.Config;

import com.example.DonnaPizza.Services.ServicioUsuarios;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import static org.apache.catalina.core.ApplicationSessionCookieConfig.createSessionCookie;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final ServicioUsuarios servicioUsuarios;

    @Bean
    public UserDetailsService userDetailsService() {
        return servicioUsuarios;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(servicioUsuarios);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .formLogin(httpForm -> {
                    httpForm.loginPage("/inicioSesion").permitAll();
                    httpForm.defaultSuccessUrl("/menuUsuario", true);
                    httpForm.successHandler((request, response, authentication) -> {
                        // Establecer una cookie persistente
                        response.addCookie(createSessionCookie());
                        response.sendRedirect("/menuUsuario");
                    });
                })
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/req/signup", "/css/**", "/js/**", "/img/**", "/**").permitAll();
                    registry.anyRequest().authenticated();
                })
                .build();
    }

    private Cookie createSessionCookie() {
        Cookie cookie = new Cookie("JSESSIONID", "valor");
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }
}

