package com.rutafit.repository;

import com.rutafit.model.Sesion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SesionRepository extends MongoRepository<Sesion, String> {

    List<Sesion> findByUsuarioIdOrderByFechaDesc(String usuarioId);
    Optional<Sesion> findByIdAndUsuarioId(String id, String usuarioId);
    boolean existsByUsuarioIdAndFecha(String usuarioId, LocalDate fecha);
}
