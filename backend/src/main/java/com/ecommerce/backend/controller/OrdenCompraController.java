package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.OrdenCompraRequestDTO;
import com.ecommerce.backend.dto.OrdenCompraResponseDTO;
import com.ecommerce.backend.service.OrdenCompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ordenCompra")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class OrdenCompraController {

    private final OrdenCompraService ordenCompraService;

    @PostMapping
    public ResponseEntity<?> procesarCheckoutController(@RequestBody OrdenCompraRequestDTO ordenCompraRequestDTO){

        // TODO: En el futuro, extraer este ID del Token JWT del usuario logueado.
        // Por ahora lo dejamos fijo en 1 para poder probar la lógica del checkout.
        Long usuarioId = 1L;

        try {
            OrdenCompraResponseDTO response = ordenCompraService.procesarCheckout(ordenCompraRequestDTO, usuarioId);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> respuestaError = new HashMap<>();
            respuestaError.put("error", "Bad Request");
            respuestaError.put("mensaje", e.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuestaError);
        }
    }
}
