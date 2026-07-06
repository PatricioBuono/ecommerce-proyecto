package com.ecommerce.backend.controller;

import com.ecommerce.backend.entity.Usuario;
import com.ecommerce.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrarNuevoUsuario(@RequestBody Usuario usuario){
        try{
            Usuario usuarioGuardado = usuarioService.registrarUsuario(usuario);

            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioGuardado);

        } catch(IllegalArgumentException e){

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
