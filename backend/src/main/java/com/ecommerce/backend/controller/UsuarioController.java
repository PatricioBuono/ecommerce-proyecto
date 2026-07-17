package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.UsuarioLoginRequestDTO;
import com.ecommerce.backend.dto.UsuarioLoginResponseDTO;
import com.ecommerce.backend.dto.UsuarioRegistroResponseDTO;
import com.ecommerce.backend.entity.Usuario;
import com.ecommerce.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioService usuarioService;
    
    @PostMapping("/registro")
    public ResponseEntity<UsuarioRegistroResponseDTO> registrarNuevoUsuario(@RequestBody Usuario usuario) {

        UsuarioRegistroResponseDTO usuarioGuardado = usuarioService.registrarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);
    }

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody UsuarioLoginRequestDTO loginRequest){
        try{
            UsuarioLoginResponseDTO respuesta = usuarioService.iniciarSesion(loginRequest);

            return ResponseEntity.ok(respuesta);
        } catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
