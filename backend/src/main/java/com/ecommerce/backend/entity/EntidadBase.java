package com.ecommerce.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class EntidadBase{

    @Column(name = "fecha_alta", nullable = false)
    private LocalDateTime fechaHoraAlta;

    @Column(name = "fecha_baja")
    private LocalDateTime fechaHoraBaja;

    protected void onCreate(){
        this.fechaHoraAlta = LocalDateTime.now();
    }
}
