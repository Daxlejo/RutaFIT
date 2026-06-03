package com.rutafit.controller;

import com.rutafit.model.*;
import com.rutafit.service.impl.ContenidoService;
import com.rutafit.service.impl.EjercicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final EjercicioService ejercicioService;
    private final ContenidoService contenidoService;

    @PostMapping("/ejercicios")
    public ResponseEntity<Ejercicio> crearEjercicio(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ejercicioService.crearGlobal(
                body.get("nombre"), body.get("descripcion"), body.get("grupoMuscular")));
    }

    @DeleteMapping("/ejercicios/{id}")
    public ResponseEntity<Ejercicio> desactivarEjercicio(@PathVariable String id) {
        return ResponseEntity.ok(ejercicioService.desactivar(id));
    }

    @PostMapping("/tips")
    public ResponseEntity<Tip> crearTip(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(contenidoService.crearTip(
                body.get("titulo"), body.get("contenido"),
                CategoriaTip.valueOf(body.get("categoria"))));
    }

    @PutMapping("/tips/{id}")
    public ResponseEntity<Tip> editarTip(@PathVariable String id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(contenidoService.editarTip(
                id, body.get("titulo"), body.get("contenido"),
                CategoriaTip.valueOf(body.get("categoria"))));
    }

    @DeleteMapping("/tips/{id}")
    public ResponseEntity<Void> eliminarTip(@PathVariable String id) {
        contenidoService.eliminarTip(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/noticias")
    public ResponseEntity<Noticia> crearNoticia(@RequestBody Map<String, Object> body) {
        boolean destacada = Boolean.parseBoolean(body.getOrDefault("destacada", false).toString());
        return ResponseEntity.ok(contenidoService.crearNoticia(
                body.get("titulo").toString(), body.get("contenido").toString(), destacada));
    }
}
