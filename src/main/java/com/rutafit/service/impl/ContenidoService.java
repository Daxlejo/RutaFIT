package com.rutafit.service.impl;

import com.rutafit.exception.ReglaNegocioException;
import com.rutafit.exception.RecursoNoEncontradoException;
import com.rutafit.model.*;
import com.rutafit.repository.NoticiaRepository;
import com.rutafit.repository.TipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContenidoService {

    private final TipRepository tipRepository;
    private final NoticiaRepository noticiaRepository;

    public List<Tip> obtenerTips(CategoriaTip categoria) {
        if (categoria != null) {
            return tipRepository.findByCategoriaAndActivoTrue(categoria);
        }
        return tipRepository.findByActivoTrue();
    }

    public Tip crearTip(String titulo, String contenido, CategoriaTip categoria) {
        Tip tip = new Tip();
        tip.setTitulo(titulo);
        tip.setContenido(contenido);
        tip.setCategoria(categoria);
        return tipRepository.save(tip);
    }

    public Tip editarTip(String id, String titulo, String contenido, CategoriaTip categoria) {
        Tip tip = tipRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tip no encontrado"));
        tip.setTitulo(titulo);
        tip.setContenido(contenido);
        tip.setCategoria(categoria);
        return tipRepository.save(tip);
    }

    public void eliminarTip(String id) {
        Tip tip = tipRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Tip no encontrado"));
        tip.setActivo(false);
        tipRepository.save(tip);
    }

    public List<Noticia> obtenerNoticias() {
        return noticiaRepository.findByActivoTrue();
    }

    public Noticia obtenerNoticiaDestacada() {
        return noticiaRepository.findByDestacadaTrue()
                .orElseThrow(() -> new RecursoNoEncontradoException("No hay noticia destacada activa"));
    }

    public Noticia crearNoticia(String titulo, String contenido, boolean destacada) {
        if (destacada) {
            // Solo una noticia puede estar destacada a la vez
            noticiaRepository.findByDestacadaTrue().ifPresent(n -> {
                n.setDestacada(false);
                noticiaRepository.save(n);
            });
        }
        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setContenido(contenido);
        noticia.setFechaPublicacion(LocalDate.now());
        noticia.setDestacada(destacada);
        return noticiaRepository.save(noticia);
    }
}
