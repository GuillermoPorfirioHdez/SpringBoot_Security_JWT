package com.app.service;

import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class UserDetailServiceImpl implements UserDetailsService {
        // Implementa la interfaz de Spring Security que se usa para cargar datos del
        // usuario durante el login

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // Método obligatorio de la interfaz UserDetailsService
                // Se llama automáticamente cuando un usuario intenta autenticarse

                // Busca el usuario en la base de datos usando el UserRepository
                UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "El usuario " + username + " no existe."));

                // Creamos una lista vacía que almacenará las autoridades (roles y permisos) del
                // usuario.
                // Cada autoridad se representa como un objeto SimpleGrantedAuthority, que
                // Spring Security usa para validar el acceso.
                List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

                // Agrega los roles del usuario como "ROLE_NOMBRE" (ej. "ROLE_ADMIN")
                userEntity.getRoles()
                                .forEach(role -> authorityList.add(
                                                new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

                // Agrega los permisos asociados a cada rol como autoridades individuales
                userEntity.getRoles().stream()
                                .flatMap(role -> role.getPermissionList().stream())
                                .forEach(permission -> authorityList
                                                .add(new SimpleGrantedAuthority(permission.getName())));

                // Devuelve un objeto User (de Spring Security) que contiene:
                // - el username
                // - el password (encriptado)
                // - y los estados de la cuenta (activa, bloqueada, etc.)
                // - junto con la lista de autoridades (roles y permisos)
                return new User(
                                userEntity.getUsername(),
                                userEntity.getPassword(),
                                userEntity.isEnabled(), // ¿Está habilitada la cuenta?
                                userEntity.isAccountNoExpired(), // ¿La cuenta no ha expirado?
                                userEntity.isCredentialNoExpired(), // ¿Las credenciales no han expirado?
                                userEntity.isAccountNoLocked(), // ¿La cuenta no está bloqueada?
                                authorityList // Lista de roles y permisos
                );
        }
}
