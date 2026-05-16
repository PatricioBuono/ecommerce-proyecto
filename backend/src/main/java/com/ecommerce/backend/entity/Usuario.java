package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "fecha_alta", updatable = false)
    private LocalDateTime fechaHoraAlta;

    @Column(name = "fecha_baja")
    private LocalDateTime fechaHoraBaja;

    @PrePersist
    protected void onCreate(){
        this.fechaHoraAlta = LocalDateTime.now();
    }

    public Usuario(String nombre, String apellido, String email, String password){
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
    }
}
