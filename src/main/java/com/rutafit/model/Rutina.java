package com.rutafit.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "rutinas")
@Getter
@Setter
public class Rutina extends DocumentoBase {

    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Field("usuario_id")
    private String usuarioId;

    // IDs de ejercicios incluidos en la rutina
    @Field("ejercicios_ids")
    private List<String> ejerciciosIds = new ArrayList<>();
}
