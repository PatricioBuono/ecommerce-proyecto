package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolUsuarioRepository extends JpaRepository<RolUsuario, Long> {

    Optional<RolUsuario> findByNombre(String nombre);
}
