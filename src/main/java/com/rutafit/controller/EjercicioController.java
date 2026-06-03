package com.rutafit.controller;

import com.rutafit.model.Ejercicio;
import com.rutafit.model.Usuario;
import com.rutafit.service.impl.EjercicioService;
import com.rutafit.service.impl.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ejercicios")
@RequiredArgsConstructor
public class EjercicioController {

    private final EjercicioService ejercicioService;
    private final UsuarioService usuarioService;

    @GetMapping("/catalogo")
    public ResponseEntity<List<Ejercicio>> catalogo(@RequestParam(required = false) String grupoMuscular) {
        return ResponseEntity.ok(ejercicioService.obtenerCatalogo(grupoMuscular));
    }

    @GetMapping("/propios")
    public ResponseEntity<List<Ejercicio>> propios(@AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername());
        return ResponseEntity.ok(ejercicioService.obtenerPropios(usuario.getId()));
    }

    @PostMapping("/propios")
    public ResponseEntity<Ejercicio> crearPropio(@AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestBody Map<String, String> body) {
        Usuario usuario = usuarioService.obtenerPorEmail(userDetails.getUsername());
        return ResponseEntity.ok(ejercicioService.crearPropio(
                usuario.getId(), body.get("nombre"), body.get("descripcion"), body.get("grupoMuscular")));
    }
}
