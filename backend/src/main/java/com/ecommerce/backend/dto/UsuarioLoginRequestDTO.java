package com.ecommerce.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLoginRequestDTO {
    private String email;
    private String password;
}
