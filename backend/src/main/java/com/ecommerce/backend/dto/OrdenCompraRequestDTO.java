package com.ecommerce.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrdenCompraRequestDTO {

    private List<ItemCompraDTO> items;

    @Getter
    @Setter
    public static class ItemCompraDTO{
        private Long productoId;
        private int cantidad;

    }
}
