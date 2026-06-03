package com.rutafit.controller;

import com.rutafit.dto.request.RutinaRequest;
import com.rutafit.model.Rutina;
import com.rutafit.model.Usuario;
import com.rutafit.service.impl.RutinaService;
import com.rutafit.service.impl.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutinas")
@RequiredArgsConstructor
public class RutinaController {

    private final RutinaService rutinaService;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Rutina>> listar(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername());
        return ResponseEntity.ok(rutinaService.obtenerPorUsuario(usuario.getId()));
    }

    @PostMapping
    public ResponseEntity<Rutina> crear(@AuthenticationPrincipal UserDetails userDetails,
                                        @Valid @RequestBody RutinaRequest request) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername());
        return ResponseEntity.ok(rutinaService.crear(usuario.getId(), request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rutina> editar(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable String id,
                                         @Valid @RequestBody RutinaRequest request) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername());
        return ResponseEntity.ok(rutinaService.editar(usuario.getId(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable String id) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername());
        rutinaService.eliminar(usuario.getId(), id);
        return ResponseEntity.noContent().build();
    }
}
