package com.ecommerce.backend.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final String campo;

    public ValidationException(String campo, String mensaje){
        super(mensaje);
        this.campo = campo;
    }
}
