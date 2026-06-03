package com.rutafit.service.impl;

import com.rutafit.exception.ReglaNegocioException;
import com.rutafit.exception.RecursoNoEncontradoException;
import com.rutafit.model.Ejercicio;
import com.rutafit.repository.EjercicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EjercicioService {

    private final EjercicioRepository ejercicioRepository;

    public List<Ejercicio> obtenerCatalogo(String grupoMuscular) {
        if (grupoMuscular != null) {
            return ejercicioRepository.findByUsuarioIdIsNullAndGrupoMuscularAndActivoTrue(grupoMuscular);
        }
        return ejercicioRepository.findByUsuarioIdIsNullAndActivoTrue();
    }

    public List<Ejercicio> obtenerPropios(String usuarioId) {
        return ejercicioRepository.findByUsuarioIdAndActivoTrue(usuarioId);
    }

    public Ejercicio crearPropio(String usuarioId, String nombre, String descripcion, String grupoMuscular) {
        if (ejercicioRepository.existsByNombreAndUsuarioId(nombre, usuarioId)) {
            throw new ReglaNegocioException("Ya tienes un ejercicio con ese nombre");
        }
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombre(nombre);
        ejercicio.setDescripcion(descripcion);
        ejercicio.setGrupoMuscular(grupoMuscular);
        ejercicio.setUsuarioId(usuarioId);
        return ejercicioRepository.save(ejercicio);
    }

    // Solo admin
    public Ejercicio crearGlobal(String nombre, String descripcion, String grupoMuscular) {
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombre(nombre);
        ejercicio.setDescripcion(descripcion);
        ejercicio.setGrupoMuscular(grupoMuscular);
        return ejercicioRepository.save(ejercicio);
    }

    public Ejercicio desactivar(String id) {
        Ejercicio ejercicio = ejercicioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Ejercicio no encontrado"));
        ejercicio.setActivo(false);
        return ejercicioRepository.save(ejercicio);
    }
}
