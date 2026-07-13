package com.ecommerce.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrdenCompraResponseDTO {

    private String mensaje;
    private OrdenDTO orden;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class OrdenDTO{
        private Long idOrden;
        private BigDecimal montoTotal;
        private String estado;
    }
}
