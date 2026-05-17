package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estados_ordenes")
@Getter
@Setter
@NoArgsConstructor
public class EstadoOrdenCompra extends EntidadBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    public EstadoOrdenCompra(String nombre){
        this.nombre = nombre;
    }
}
