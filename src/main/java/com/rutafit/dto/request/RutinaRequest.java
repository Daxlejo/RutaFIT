package com.rutafit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class RutinaRequest {
    @NotBlank
    private String nombre;
    @NotEmpty
    private List<String> ejerciciosIds;
}
