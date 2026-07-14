package com.ecommerce.backend.controller;

import com.ecommerce.backend.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<?> obtenerCatalogo(){
        return ResponseEntity.ok(productoService.obtenerCatalogo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerDetalleProducto(@PathVariable Long id){
        try{
            return ResponseEntity.ok(productoService.obtenerDetalleProducto(id));
        }catch(RuntimeException e){
            Map<String, String> respuestaError = new HashMap<>();
            respuestaError.put("error", "Not Found");
            respuestaError.put("mensaje", e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaError);
        }
    }
}
