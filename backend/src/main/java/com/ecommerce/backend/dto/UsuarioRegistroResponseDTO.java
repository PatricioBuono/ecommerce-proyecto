package com.ecommerce.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRegistroResponseDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String nombreRol;

}
