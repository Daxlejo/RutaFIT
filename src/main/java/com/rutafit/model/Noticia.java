package com.rutafit.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "noticias")
@Getter
@Setter
public class Noticia extends Publicacion {

    @Field("fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Field("destacada")
    private boolean destacada = false;
}
