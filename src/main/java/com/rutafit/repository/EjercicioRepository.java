package com.rutafit.repository;

import com.rutafit.model.Ejercicio;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EjercicioRepository extends MongoRepository<Ejercicio, String> {

    // Ejercicios globales del admin
    List<Ejercicio> findByUsuarioIdIsNullAndActivoTrue();

    // Ejercicios globales filtrados por grupo muscular
    List<Ejercicio> findByUsuarioIdIsNullAndGrupoMuscularAndActivoTrue(String grupoMuscular);

    // Ejercicios personales del usuario
    List<Ejercicio> findByUsuarioIdAndActivoTrue(String usuarioId);

    boolean existsByNombreAndUsuarioId(String nombre, String usuarioId);
}
