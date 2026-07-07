package com.ecommerce.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioLoginResponseDTO {
    private String mensaje;
    private String token;
    private UsuarioInfo usuario;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UsuarioInfo{
        private Long id;
        private String nombre;
        private String email;
        private String rol;
    }
}
