package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.OrdenCompraRequestDTO;
import com.ecommerce.backend.dto.OrdenCompraResponseDTO;
import com.ecommerce.backend.entity.*;
import com.ecommerce.backend.repository.EstadoOrdenCompraRepository;
import com.ecommerce.backend.repository.OrdenCompraRepository;
import com.ecommerce.backend.repository.ProductoRepository;
import com.ecommerce.backend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenCompraService {

    private final OrdenCompraRepository ordenCompraRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstadoOrdenCompraRepository estadoOrdenCompraRepository;

    @Transactional
    public OrdenCompraResponseDTO procesarCheckout(OrdenCompraRequestDTO request, Long usuarioId){

        Usuario usuario = obtenerUsuario(usuarioId);
        OrdenCompra nuevaOrden = inicializarOrden(usuario);

        BigDecimal montoTotal = BigDecimal.ZERO;

        List<OrdenCompraProducto> listaProductos = new ArrayList<>();

        for(OrdenCompraRequestDTO.ItemCompraDTO item: request.getItems()){

            Producto producto = validarYDescontarStock(item.getProductoId(), item.getCantidad());
            OrdenCompraProducto ordenProducto = crearDetalle(nuevaOrden, producto, item.getCantidad());
            listaProductos.add(ordenProducto);

            montoTotal = montoTotal.add(ordenProducto.getMontoTotalOrdenProducto());
        }

        nuevaOrden.setMontoTotal(montoTotal);
        nuevaOrden.setOrdenProductos(listaProductos);

        ordenCompraRepository.save(nuevaOrden);

        return new OrdenCompraResponseDTO(
                "Compra realizada con éxito",
                new OrdenCompraResponseDTO.OrdenDTO(nuevaOrden.getId(), montoTotal, nuevaOrden.getEstadoActual().getNombre())
        );

    }

    private Usuario obtenerUsuario(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }


    private OrdenCompra inicializarOrden(Usuario usuario){

        EstadoOrdenCompra estadoCompletada = estadoOrdenCompraRepository.findByNombre("COMPLETADA")
                .orElseThrow(() -> new RuntimeException("El estado COMPLETADA no existe"));

        return new OrdenCompra(BigDecimal.ZERO, usuario, estadoCompletada);
    }

    private Producto validarYDescontarStock(Long productoId, int cantidadSolicitada){
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("El producto con ID "+ productoId +" no existe."));

        if(producto.getStock() < cantidadSolicitada){
            throw new RuntimeException("Stock insuficiente para el producto "+producto.getNombre());
        }

        producto.setStock(producto.getStock() - cantidadSolicitada);
        return producto;
    }

    private OrdenCompraProducto crearDetalle(OrdenCompra orden, Producto producto, int cantidad){
        BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
        return new OrdenCompraProducto(cantidad, subtotal, producto.getPrecio(), orden, producto);
    }

}

