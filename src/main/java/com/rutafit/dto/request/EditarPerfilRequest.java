package com.rutafit.dto.request;

import com.rutafit.model.PreferenciaNotificacion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EditarPerfilRequest {
    private String nombre;
    @Email
    private String email;
    @Size(min = 8)
    private String contrasenaNueva;
    private String contrasenaActual;
    private PreferenciaNotificacion preferenciaNotificacion;
    private String tokenPush;
    private Boolean recordatorioActivo;
}
