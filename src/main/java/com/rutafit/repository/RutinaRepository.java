package com.rutafit.repository;

import com.rutafit.model.Rutina;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RutinaRepository extends MongoRepository<Rutina, String> {

    List<Rutina> findByUsuarioId(String usuarioId);
    Optional<Rutina> findByIdAndUsuarioId(String id, String usuarioId);
}
