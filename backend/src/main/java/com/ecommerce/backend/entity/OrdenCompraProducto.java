package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "ordenes_productos")
@Getter
@Setter
@NoArgsConstructor
public class OrdenCompraProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad_producto")
    private int cantidadProducto;

    @Column(name = "monto_total_orden_producto")
    private BigDecimal montoTotalOrdenProducto;

    @Column(name = "precio_unitario")
    private BigDecimal precioUnitarioProducto;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_orden_compra",
            foreignKey = @ForeignKey(name = "fk_orden_compra")
    )
    private OrdenCompra orden;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_producto",
            foreignKey = @ForeignKey(name = "fk_producto")
    )
    private Producto producto;

    public OrdenCompraProducto(
            int cantidadProducto, BigDecimal montoTotalOrdenProducto,
            BigDecimal precioUnitarioProducto, OrdenCompra orden, Producto producto
    ){
        this.cantidadProducto = cantidadProducto;
        this.montoTotalOrdenProducto = montoTotalOrdenProducto;
        this.precioUnitarioProducto = precioUnitarioProducto;
        this.orden = orden;
        this.producto = producto;
    }
}
