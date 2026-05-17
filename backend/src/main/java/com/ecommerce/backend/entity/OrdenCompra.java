package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes_de_compra")
@Getter
@Setter
@NoArgsConstructor
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "fecha_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaHoraFin;

    @Column(nullable = false, name = "monto_total")
    private BigDecimal montoTotal;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_usuario",
            foreignKey = @ForeignKey(name = "fk_orden_usuario")
    )
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_estado_orden",
            foreignKey = @ForeignKey(name = "fk_estado_orden")
    )
    private EstadoOrdenCompra estado;

    public OrdenCompra(
            LocalDateTime fechaHoraInicio, BigDecimal montoTotal, Usuario usuario, EstadoOrdenCompra estado
    ){
        this.fechaHoraInicio = LocalDateTime.now();
        this.montoTotal = montoTotal;
        this.usuario = usuario;
        this.estado = estado;
    }
}
