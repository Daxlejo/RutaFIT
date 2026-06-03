package com.rutafit.controller;

import com.rutafit.dto.request.EditarPerfilRequest;
import com.rutafit.model.Usuario;
import com.rutafit.service.impl.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Usuario> obtener(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(usuarioService.obtenerPorEmail(userDetails.getUsername()));
    }

    @PutMapping
    public ResponseEntity<Usuario> editar(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody EditarPerfilRequest request) {
        return ResponseEntity.ok(usuarioService.editarPerfil(userDetails.getUsername(), request));
    }
}
