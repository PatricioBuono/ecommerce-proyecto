package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario extends EntidadBase{

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

    @ManyToOne
    @JoinColumn(
            name = "id_rol",
            foreignKey = @ForeignKey(name = "fk_usuario_rol")
    )
    private RolUsuario rol;

    public Usuario(String nombre, String apellido, String email, String password, RolUsuario rol){
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }
}
