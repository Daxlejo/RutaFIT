package com.rutafit.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "sesiones")
@Getter
@Setter
public class Sesion extends DocumentoBase {

    @Id
    private String id;

    @Field("usuario_id")
    private String usuarioId;

    @Field("rutina_id")
    private String rutinaId;

    @Field("fecha")
    private LocalDate fecha;

    // Series embebidas: cada serie tiene ejercicioId, kilos y repeticiones
    @Field("series")
    private List<Serie> series = new ArrayList<>();
}
