package com.app.persistence.repository;

import com.app.persistence.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository 
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    // Método personalizado para buscar un usuario por su username
    // Retorna Optional para manejar el caso donde no exista un usuario con ese username
    Optional<UserEntity> findUserEntityByUsername(String username);
}
