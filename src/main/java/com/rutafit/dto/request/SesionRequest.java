package com.rutafit.dto.request;

import com.rutafit.model.Serie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class SesionRequest {
    @NotBlank
    private String rutinaId;
    @NotNull
    private LocalDate fecha;
    private List<Serie> series;
}
