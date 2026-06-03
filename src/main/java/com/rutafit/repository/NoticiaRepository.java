package com.rutafit.repository;

import com.rutafit.model.Noticia;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NoticiaRepository extends MongoRepository<Noticia, String> {

    List<Noticia> findByActivoTrue();
    Optional<Noticia> findByDestacadaTrue();
}
