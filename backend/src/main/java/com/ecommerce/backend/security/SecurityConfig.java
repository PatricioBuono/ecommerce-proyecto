package com.ecommerce.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 1. Deshabilitamos CSRF (Cross-Site Request Forgery)
                // Es una protección para formularios tradicionales, pero en APIs REST con JWT no se usa.
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Configuramos las reglas de acceso a las rutas
                .authorizeHttpRequests(auth -> auth
                        // Declaramos que el login y el registro son de acceso público
                        .requestMatchers("/api/auth/login", "/api/auth/registro", "/api/productos/**").permitAll()
                        // Declaramos que CUALQUIER otra ruta va a requerir el token
                        .anyRequest().authenticated()
                )

                // 3. Configuramos el manejo de sesiones
                // Al ser una API REST, la hacemos STATELESS (sin estado).
                // Spring no va a recordar quién sos entre peticiones; tu token es tu única identificación.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 4. Agregamos nuestro "patovica" a la puerta
                // Le decimos que ejecute nuestro filtro ANTES de intentar validar usuario y contraseña por defecto
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitimos que tu Angular se conecte
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        // Permitimos estos métodos (clave agregar OPTIONS para el preflight)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permitimos que Angular envíe estas cabeceras (clave Authorization)
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Opcional: permite el envío de credenciales/cookies
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a todas las rutas
        return source;
    }
}