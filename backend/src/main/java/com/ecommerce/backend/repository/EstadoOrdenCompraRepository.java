package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.EstadoOrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstadoOrdenCompraRepository extends JpaRepository<EstadoOrdenCompra, Long> {

    Optional<EstadoOrdenCompra> findByNombre(String nombre);
}
