package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes_estados")
@Getter
@Setter
@NoArgsConstructor
public class OrdenCompraEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_desde")
    private LocalDateTime fechaHoraDesde;

    @Column(name = "fecha_hasta")
    private LocalDateTime fechaHoraHasta;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_orden",
            foreignKey = @ForeignKey(name = "fk_historial_orden")
    )
    private OrdenCompra orden;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_estado_orden",
            foreignKey = @ForeignKey(name = "fk_historial_estado")
    )
    private EstadoOrdenCompra estado;

    public OrdenCompraEstado(OrdenCompra orden, EstadoOrdenCompra estado){
        this.fechaHoraDesde = LocalDateTime.now();
        this.orden = orden;
        this.estado = estado;
    }
}
