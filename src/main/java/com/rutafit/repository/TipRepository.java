package com.rutafit.repository;

import com.rutafit.model.CategoriaTip;
import com.rutafit.model.Tip;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TipRepository extends MongoRepository<Tip, String> {

    List<Tip> findByActivoTrue();
    List<Tip> findByCategoriaAndActivoTrue(CategoriaTip categoria);
}
