package com.ecommerce.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList; // Por ahora usaremos una lista vacía de permisos

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
            ) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        String email = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer")){
            jwt = authorizationHeader.substring(7);
            try{
                email = jwtUtil.extraerEmail(jwt);
            } catch (Exception e){
                System.out.println("Error extrayendo el token o token vencido");
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 4. Le preguntamos a nuestro JwtUtil si el token sigue siendo válido (no está vencido, etc.)
            if (jwtUtil.esTokenValido(jwt)) {

                // 5. Creamos el objeto de Autenticación de Spring Security
                // (El tercer parámetro es la lista de roles/permisos. Por ahora lo dejamos vacío,
                // luego lo conectaremos con el rol real del usuario).
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        email, null, new ArrayList<>()
                );

                // 6. Le agregamos los detalles de la petición (como la IP o la sesión)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 7. Finalmente, le decimos a Spring Security: "Este usuario es legítimo, déjalo pasar"
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 8. Importante: Continuar con la cadena de filtros para que la petición llegue a tu Controlador
        filterChain.doFilter(request, response);
    }
}
