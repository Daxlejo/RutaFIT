package com.rutafit.controller;

import com.rutafit.model.CategoriaTip;
import com.rutafit.model.Noticia;
import com.rutafit.model.Tip;
import com.rutafit.service.impl.ContenidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContenidoController {

    private final ContenidoService contenidoService;

    @GetMapping("/api/tips")
    public ResponseEntity<List<Tip>> tips(@RequestParam(required = false) CategoriaTip categoria) {
        return ResponseEntity.ok(contenidoService.obtenerTips(categoria));
    }

    @GetMapping("/api/noticias")
    public ResponseEntity<List<Noticia>> noticias() {
        return ResponseEntity.ok(contenidoService.obtenerNoticias());
    }

    @GetMapping("/api/noticias/destacada")
    public ResponseEntity<Noticia> noticiaDestacada() {
        return ResponseEntity.ok(contenidoService.obtenerNoticiaDestacada());
    }
}
