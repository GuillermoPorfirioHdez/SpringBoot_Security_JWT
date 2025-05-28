package com.app.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissions") 
// @Table: Especifica el nombre de la tabla en la base de datos que mapea esta entidad. 


public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    // @Column: Configura las propiedades de la columna en la tabla.
    // unique = true     -> La columna "name" debe tener valores únicos.
    // nullable = false  -> No se permite que la columna tenga valores nulos.
    // updatable = false -> El valor de esta columna no se puede modificar después de insertarse.
    private String name;
}
