package com.rutafit.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "tips")
@Getter
@Setter
public class Tip extends Publicacion {

    @Field("categoria")
    private CategoriaTip categoria;
}
