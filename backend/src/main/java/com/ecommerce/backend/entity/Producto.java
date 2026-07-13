package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
public class Producto extends EntidadBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private BigDecimal precio;

    private String descripcion;

    private int stock;

    @Column(nullable = false, name = "url_imagen")
    private String urlImagen;

    @ManyToOne
    @JoinColumn(
            name = "id_categoria",
            foreignKey = @ForeignKey(name = "fk_categoria")
    )
    private CategoriaProducto categoria;

    @ManyToOne
    @JoinColumn(
            name = "id_estado_producto",
            foreignKey = @ForeignKey(name = "fk_estado_producto")
    )
    private EstadoProducto estado;

    @OneToMany(mappedBy = "producto")
    private List<OrdenCompraProducto> historialVentas;

    public Producto(String nombre, BigDecimal precio, String descripcion, int stock, String urlImagen, CategoriaProducto categoria, EstadoProducto estado){
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
        this.estado = estado;
    }


}
