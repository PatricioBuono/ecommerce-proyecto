package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.UsuarioLoginRequestDTO;
import com.ecommerce.backend.dto.UsuarioLoginResponseDTO;
import com.ecommerce.backend.dto.UsuarioRegistroResponseDTO;
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
    private final String regexEmail = "^[A-Za-z0-9._+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private final String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*\\-_.]).+$";

    public UsuarioRegistroResponseDTO registrarUsuario(Usuario nuevoUsuario){

        validarEstructuraRegistro(nuevoUsuario);

        // Falta la encriptación con spring security

        RolUsuario rolCliente = rolUsuarioRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Error en el sistema: El rol CLIENTE no existe en la base de datos."));
        nuevoUsuario.setRol(rolCliente);
        nuevoUsuario.setFechaHoraAlta(LocalDateTime.now());

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        UsuarioRegistroResponseDTO dto = new UsuarioRegistroResponseDTO();
        dto.setId(usuarioGuardado.getId());
        dto.setNombre(usuarioGuardado.getNombre());
        dto.setApellido(usuarioGuardado.getApellido());
        dto.setEmail(usuarioGuardado.getEmail());
        dto.setNombreRol(usuarioGuardado.getRol().getNombre());

        return dto;
    }

    public UsuarioLoginResponseDTO iniciarSesion(UsuarioLoginRequestDTO loginUsuario){
        // el metodo iniciarSesion debe ser del tipo token que devuelve TokenJWTResponseDTO
        validarCampoTexto(loginUsuario.getEmail(), "Credenciales incorrectas.");
        validarCampoTexto(loginUsuario.getPassword(), "Credenciales incorrectas.");

        Usuario usuarioEncontrado = usuarioRepository.findByEmail(loginUsuario.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas."));

        if(!usuarioEncontrado.getPassword().equals(loginUsuario.getPassword())){
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        UsuarioLoginResponseDTO.UsuarioInfo userInfo = new UsuarioLoginResponseDTO.UsuarioInfo(
                usuarioEncontrado.getId(),
                usuarioEncontrado.getNombre(),
                usuarioEncontrado.getEmail(),
                usuarioEncontrado.getRol().getNombre()
        );

        return new UsuarioLoginResponseDTO("Login exitoso", "TOKEN", userInfo);
    }

    private void validarEstructuraRegistro(Usuario usuario){
        validarCampoTexto(usuario.getNombre(), "El nombre es obligatorio.");
        validarCampoTexto(usuario.getApellido(), "El apellido es obligatorio.");
        validarCampoTexto(usuario.getEmail(), "El correo electrónico es obligatorio.");
        validarCampoTexto(usuario.getPassword(), "La contraseña es obligatoria.");

        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new IllegalArgumentException("El email ya se encuentra registrado en el sistema.");
        }
        if(!usuario.getEmail().matches(regexEmail)){
            throw new IllegalArgumentException("El formato de correo electrónico no es válido.");
        }

        String passwordPlana = usuario.getPassword();
        if(passwordPlana.length() < 8){
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
        }
        if(!passwordPlana.matches(regex)){
            throw new IllegalArgumentException("La contraseña debe contener al menos una letra mayúscula, un número y un carácter especial.");
        }
    }

    private void validarCampoTexto(String texto, String mensajeError){
        if(texto == null || texto.trim().isEmpty()){
            throw new IllegalArgumentException(mensajeError);
        }
    }
}
