package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ProductoCatalogoResponseDTO;
import com.ecommerce.backend.entity.Producto;
import com.ecommerce.backend.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<ProductoCatalogoResponseDTO> obtenerCatalogo(){

        List<Producto> productosDisponibles = productoRepository.findByStockGreaterThanOrderByIdDesc(0);

        return productosDisponibles.stream()
                .map(producto -> new ProductoCatalogoResponseDTO(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getUrlImagen(),
                        producto.getStock()
                ))
                .collect(Collectors.toList());
    }
}
