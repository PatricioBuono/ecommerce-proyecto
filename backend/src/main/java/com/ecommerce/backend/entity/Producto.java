package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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

    public Producto(String nombre, BigDecimal precio, String descripcion, int stock, String urlImagen){
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.urlImagen = urlImagen;
    }
}
