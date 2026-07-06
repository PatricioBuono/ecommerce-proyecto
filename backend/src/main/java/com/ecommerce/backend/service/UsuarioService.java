package com.ecommerce.backend.service;

import com.ecommerce.backend.entity.RolUsuario;
import com.ecommerce.backend.entity.Usuario;
import com.ecommerce.backend.repository.RolUsuarioRepository;
import com.ecommerce.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final RolUsuarioRepository rolUsuarioRepository;

    public Usuario registrarUsuario(Usuario nuevoUsuario){

        if(usuarioRepository.existsByEmail(nuevoUsuario.getEmail())){
            throw new IllegalArgumentException("El email ya se encuentra registrado en el sistema.");
        }

        String passwordPlana = nuevoUsuario.getPassword();
        if(passwordPlana == null){
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        if(passwordPlana.length() < 8){
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }

        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*\\-_.]).+$";
        if(!passwordPlana.matches(regex)){
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra mayúscula, un número y un carácter especial.");
        }

        // Falta la encriptación con spring security

        RolUsuario rolCliente = rolUsuarioRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Error en el sistema: El rol CLIENTE no existe en la base de datos."));
        nuevoUsuario.setRol(rolCliente);

        nuevoUsuario.setFechaHoraAlta(LocalDateTime.now());

        return usuarioRepository.save(nuevoUsuario);
    }
}
