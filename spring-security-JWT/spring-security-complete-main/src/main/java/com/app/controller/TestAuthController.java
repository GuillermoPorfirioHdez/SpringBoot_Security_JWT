package com.app.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth") 
// Ruta base para todos los endpoints en esta clase será "/auth"
public class TestAuthController {

    @GetMapping("/get")
    // Endpoint que responde a solicitudes GET en "/auth/get"
    public String helloGet(){
        return "Hello World - GET";
    }

    @PostMapping("/post")
    // Endpoint que responde a solicitudes POST en "/auth/post"
    public String helloPost(){
        return "Hello World - POST";
    }

    @PutMapping("/put")
    // Endpoint que responde a solicitudes PUT en "/auth/put"
    public String helloPut(){
        return "Hello World - PUT";
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('DELETE')")
    // Endpoint que responde a solicitudes DELETE en "/auth/delete"
    public String helloDelete(){
        return "Hello World - DELETE";
    }

    @PatchMapping("/patch")
    // Endpoint que responde a solicitudes PATCH en "/auth/patch"
    public String helloPatch(){
        return "Hello World - PATCH";
    }
}
