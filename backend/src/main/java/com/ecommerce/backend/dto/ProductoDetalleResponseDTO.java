package com.ecommerce.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class ProductoDetalleResponseDTO {

    private Long id;
    private String titulo;
    private BigDecimal precio;
    private String descripcion;
    private CategoriaInfo categoria;
    private String imagenUrl;
    private int stock;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CategoriaInfo{

        private Long id;
        private String denominacion;
    }
}
