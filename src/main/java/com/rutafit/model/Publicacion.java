package com.rutafit.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
public abstract class Publicacion extends DocumentoBase {

    @Id
    private String id;

    @Field("titulo")
    private String titulo;

    @Field("contenido")
    private String contenido;

    @Field("activo")
    private boolean activo = true;
}
