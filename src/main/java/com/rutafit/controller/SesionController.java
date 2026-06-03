package com.rutafit.controller;

import com.rutafit.dto.request.SesionRequest;
import com.rutafit.model.Sesion;
import com.rutafit.model.Usuario;
import com.rutafit.service.impl.SesionService;
import com.rutafit.service.impl.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sesiones")
@RequiredArgsConstructor
public class SesionController {

    private final SesionService sesionService;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Sesion>> historial(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername());
        return ResponseEntity.ok(sesionService.obtenerHistorial(usuario.getId()));
    }

    @PostMapping
    public ResponseEntity<Sesion> registrar(@AuthenticationPrincipal UserDetails userDetails,
                                             @Valid @RequestBody SesionRequest request) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername());
        return ResponseEntity.ok(sesionService.registrar(usuario.getId(), request));
    }

    @GetMapping("/record/{ejercicioId}")
    public ResponseEntity<Map<String, Object>> record(@AuthenticationPrincipal UserDetails userDetails,
                                                       @PathVariable String ejercicioId) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername());
        return ResponseEntity.ok(sesionService.obtenerRecord(usuario.getId(), ejercicioId));
    }

    @GetMapping("/progresion/{ejercicioId}")
    public ResponseEntity<List<Map<String, Object>>> progresion(@AuthenticationPrincipal UserDetails userDetails,
                                                                 @PathVariable String ejercicioId) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername());
        return ResponseEntity.ok(sesionService.obtenerProgresion(usuario.getId(), ejercicioId));
    }
}
