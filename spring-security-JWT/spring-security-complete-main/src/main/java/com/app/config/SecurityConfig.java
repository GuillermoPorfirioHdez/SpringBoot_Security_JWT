package com.app.config;

import com.app.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marca esta clase como una clase de configuración
@EnableWebSecurity // Habilita la seguridad web de Spring Security
@EnableMethodSecurity // Permite el uso de anotaciones como @PreAuthorize en los métodos
public class SecurityConfig {

    @Bean
    // Define un Bean que configura la cadena de filtros de seguridad de Spring Security.
// Recibe como parámetro un objeto HttpSecurity que permite personalizar la seguridad HTTP (autorizaciones, autenticación, CSRF, sesiones, etc.)
// Lanza una excepción si ocurre algún error durante la configuración.
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
 {
        return httpSecurity
        
                .csrf(csrf -> csrf.disable()) // Desactiva la protección CSRF (importante en APIs REST)
                .httpBasic(Customizer.withDefaults()) // Usa autenticación HTTP básica (usuario y contraseña en cada solicitud)
                .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura que no se use sesión (JWT o token idealmente)
                .authorizeHttpRequests(http -> {
                    // ENDPOINTS PÚBLICOS
                    http.requestMatchers(HttpMethod.GET, "/auth/get").permitAll(); // Cualquiera puede acceder a /auth/get

                    // ENDPOINTS PRIVADOS
                    http.requestMatchers(HttpMethod.POST, "/auth/post")
                        .hasAnyRole("ADMIN", "DEVELOPER"); // Solo usuarios con rol ADMIN o DEVELOPER

                    http.requestMatchers(HttpMethod.PATCH, "/auth/patch")
                        .hasAnyAuthority("REFACTOR"); // Solo usuarios con permiso REFACTOR

                    // CUALQUIER OTRO ENDPOINT (no especificado)
                    http.anyRequest().denyAll(); // Bloquea todo lo demás
                })
                .build(); // Devuelve el filtro de seguridad configurado
    }

 
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Devuelve un AuthenticationManager, que se usará para procesar el login
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        // Configura un proveedor de autenticación basado en DAO (acceso a base de datos)
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder()); // Establece el encoder para comparar contraseñas
        provider.setUserDetailsService(userDetailService); // Establece el servicio que carga usuarios
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Define el algoritmo de encriptación de contraseñas (seguro y recomendado)
        return new BCryptPasswordEncoder();
    }
}
