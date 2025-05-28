package com.app.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Setter    // Genera automáticamente los métodos set para todos los atributos
@Getter    // Genera automáticamente los métodos get para todos los atributos
@Builder   // Implementa el patrón builder para facilitar la creación de objetos
@AllArgsConstructor // Genera un constructor con todos los atributos
@NoArgsConstructor  // Genera un constructor sin argumentos (necesario para JPA)
@Entity   // Marca la clase como entidad JPA, vinculada a una tabla de la base de datos
@Table(name = "users")  // Especifica el nombre de la tabla en la base de datos
public class UserEntity {

    @Id  // Define el atributo como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental en la base de datos
    private Long id;

    @Column(unique = true, nullable = false)  // El campo debe ser único y obligatorio en la tabla
    private String username;

    @Column(nullable = false)  // Campo obligatorio para almacenar la contraseña
    private String password;

    @Column(name = "is_enabled")  // Indica si la cuenta está habilitada o no
    private boolean isEnabled;

    @Column(name = "account_no_expired")  // Indica si la cuenta no ha expirado
    private boolean accountNoExpired;

    @Column(name = "account_no_locked")  // Indica si la cuenta no está bloqueada
    private boolean accountNoLocked;

    @Column(name = "credential_no_expired")  // Indica si las credenciales no han expirado
    private boolean credentialNoExpired;

    // Relación Many-to-Many con RoleEntity, con carga inmediata (EAGER)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(  // Tabla intermedia para manejar la relación entre usuarios y roles
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),  // Columna que referencia a esta entidad
        inverseJoinColumns = @JoinColumn(name = "role_id")  // Columna que referencia a roles
    )
    @Builder.Default  // Indica a Lombok que use este valor por defecto al usar el builder
    private Set<RoleEntity> roles = new HashSet<>();  // Conjunto de roles asignados al usuario
}
