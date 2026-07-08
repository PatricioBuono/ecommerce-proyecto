package com.ecommerce.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductoCatalogoResponseDTO {
    private Long id;
    private String titulo;
    private BigDecimal precio;
    private String imagenUrl;
    private int stock;
}
