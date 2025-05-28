package com.app.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles") // Se mapeará con la tabla "roles"
public class RoleEntity {

    @Id // Marca el campo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Valor autoincremental en la base de datos
    private Long id;

    // Enum que representa el nombre del rol, como ADMIN o USER
    @Column(name = "role_name") // Nombre de la columna en la tabla
    @Enumerated(EnumType.STRING) // Guarda el nombre del enum como cadena en la base de datos
    private RoleEnum roleEnum;

    // Relación ManyToMany entre rol y permisos
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "role_permissions", // Nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "role_id"), // Llave foránea hacia esta tabla
        inverseJoinColumns = @JoinColumn(name = "permission_id") // Llave foránea hacia PermissionEntity
    )
    @Builder.Default
    private Set<PermissionEntity> permissionList = new HashSet<>(); // Lista de permisos que tiene este rol
}
