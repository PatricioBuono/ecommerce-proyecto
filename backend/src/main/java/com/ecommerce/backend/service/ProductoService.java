package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.ProductoCatalogoResponseDTO;
import com.ecommerce.backend.dto.ProductoDetalleResponseDTO;
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

    public ProductoDetalleResponseDTO obtenerDetalleProducto(Long id){

        Producto productoEncontrado = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto solicitado no existe o ya no está disponible."));

        ProductoDetalleResponseDTO.CategoriaInfo categoriaInfo = null;
        if(productoEncontrado.getCategoria() != null){
            categoriaInfo = new ProductoDetalleResponseDTO.CategoriaInfo(
                    productoEncontrado.getCategoria().getId(),
                    productoEncontrado.getCategoria().getNombre()
            );
        }

        return new ProductoDetalleResponseDTO(
                productoEncontrado.getId(),
                productoEncontrado.getNombre(),
                productoEncontrado.getPrecio(),
                productoEncontrado.getDescripcion(),
                categoriaInfo,
                productoEncontrado.getUrlImagen(),
                productoEncontrado.getStock()
        );
    }
}
