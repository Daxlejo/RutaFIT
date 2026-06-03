package com.rutafit.controller;

import com.rutafit.dto.request.LoginRequest;
import com.rutafit.dto.request.RegistroRequest;
import com.rutafit.service.impl.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registro")
    public ResponseEntity<Map<String, String>> registrar(@Valid @RequestBody RegistroRequest request) {
        String token = authService.registrar(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/recuperar-contrasena")
    public ResponseEntity<Map<String, String>> recuperar(@RequestParam String email) {
        authService.recuperarContrasena(email);
        return ResponseEntity.ok(Map.of("mensaje", "Contraseña enviada al correo"));
    }
}
