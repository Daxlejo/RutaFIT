package com.rutafit.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ejercicios")
@Getter
@Setter
public class Ejercicio extends DocumentoBase {

    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Field("descripcion")
    private String descripcion;

    @Field("grupo_muscular")
    private String grupoMuscular;

    // null = ejercicio global del admin, con valor = ejercicio personal del usuario
    @Field("usuario_id")
    private String usuarioId;

    @Field("activo")
    private boolean activo = true;
}
