package com.rutafit.service.impl;

import com.rutafit.dto.request.RutinaRequest;
import com.rutafit.exception.ReglaNegocioException;
import com.rutafit.exception.RecursoNoEncontradoException;
import com.rutafit.model.Rutina;
import com.rutafit.repository.RutinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RutinaService {

    private final RutinaRepository rutinaRepository;

    public List<Rutina> obtenerPorUsuario(String usuarioId) {
        return rutinaRepository.findByUsuarioId(usuarioId);
    }

    public Rutina crear(String usuarioId, RutinaRequest request) {
        Rutina rutina = new Rutina();
        rutina.setNombre(request.getNombre());
        rutina.setUsuarioId(usuarioId);
        rutina.setEjerciciosIds(request.getEjerciciosIds());
        return rutinaRepository.save(rutina);
    }

    public Rutina editar(String usuarioId, String rutinaId, RutinaRequest request) {
        Rutina rutina = rutinaRepository.findByIdAndUsuarioId(rutinaId, usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rutina no encontrada"));

        if (request.getEjerciciosIds().isEmpty()) {
            throw new ReglaNegocioException("La rutina debe tener al menos un ejercicio");
        }

        rutina.setNombre(request.getNombre());
        rutina.setEjerciciosIds(request.getEjerciciosIds());
        return rutinaRepository.save(rutina);
    }

    public void eliminar(String usuarioId, String rutinaId) {
        Rutina rutina = rutinaRepository.findByIdAndUsuarioId(rutinaId, usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rutina no encontrada"));
        rutinaRepository.delete(rutina);
    }
}
