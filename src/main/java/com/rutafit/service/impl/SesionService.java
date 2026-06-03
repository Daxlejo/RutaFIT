package com.rutafit.service.impl;

import com.rutafit.dto.request.SesionRequest;
import com.rutafit.exception.RecursoNoEncontradoException;
import com.rutafit.model.*;
import com.rutafit.repository.RutinaRepository;
import com.rutafit.repository.SesionRepository;
import com.rutafit.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.rutafit.service.EmailService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SesionService {

    private final SesionRepository sesionRepository;
    private final RutinaRepository rutinaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;

    public List<Sesion> obtenerHistorial(String usuarioId) {
        return sesionRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }

    public Sesion registrar(String usuarioId, SesionRequest request) {
        Rutina rutina = rutinaRepository.findByIdAndUsuarioId(request.getRutinaId(), usuarioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rutina no encontrada"));

        Sesion sesion = new Sesion();
        sesion.setUsuarioId(usuarioId);
        sesion.setRutinaId(request.getRutinaId());
        sesion.setFecha(request.getFecha());
        sesion.setSeries(request.getSeries() != null ? request.getSeries() : new ArrayList<>());
        sesionRepository.save(sesion);

        notificarSiCorresponde(usuarioId, rutina.getNombre(), sesion.getSeries().size());
        return sesion;
    }

    // Récord personal: sesión con el mayor peso levantado en un ejercicio
    // específico
    public Map<String, Object> obtenerRecord(String usuarioId, String ejercicioId) {
        List<Sesion> sesiones = sesionRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);

        Sesion mejorSesion = null;
        double mayorPeso = 0;

        for (Sesion sesion : sesiones) {
            for (Serie serie : sesion.getSeries()) {
                if (serie.getEjercicioId().equals(ejercicioId) && serie.getKilos() > mayorPeso) {
                    mayorPeso = serie.getKilos();
                    mejorSesion = sesion;
                }
            }
        }

        if (mejorSesion == null) {
            return Map.of("mensaje", "Sin registros para este ejercicio");
        }

        return Map.of(
                "fecha", mejorSesion.getFecha(),
                "kilos", mayorPeso,
                "sesionId", mejorSesion.getId());
    }

    // Progresión: peso máximo por sesión ordenado cronológicamente
    public List<Map<String, Object>> obtenerProgresion(String usuarioId, String ejercicioId) {
        List<Sesion> sesiones = sesionRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);
        List<Map<String, Object>> progresion = new ArrayList<>();

        for (Sesion sesion : sesiones) {
            sesion.getSeries().stream()
                    .filter(s -> s.getEjercicioId().equals(ejercicioId))
                    .max(Comparator.comparingDouble(Serie::getKilos))
                    .ifPresent(mejor -> progresion.add(Map.of(
                            "fecha", sesion.getFecha(),
                            "maxKilos", mejor.getKilos(),
                            "repeticiones", mejor.getRepeticiones())));
        }

        Collections.reverse(progresion);
        return progresion;
    }

    private void notificarSiCorresponde(String usuarioId, String rutinaNombre, int totalSeries) {
        usuarioRepository.findById(usuarioId).ifPresent(usuario -> {
            PreferenciaNotificacion pref = usuario.getPreferenciaNotificacion();
            if (pref == PreferenciaNotificacion.EMAIL || pref == PreferenciaNotificacion.AMBAS) {
                emailService.enviarConfirmacionSesion(usuario.getEmail(), usuario.getNombre(), rutinaNombre,
                        totalSeries);
            }
        });
    }
}
