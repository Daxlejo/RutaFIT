package com.rutafit.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "usuarios")
@Getter
@Setter
public class Usuario extends DocumentoBase {

    @Id
    private String id;

    @Field("nombre")
    private String nombre;

    @Indexed(unique = true)
    @Field("email")
    private String email;

    @Field("contrasena")
    private String contrasena;

    @Field("rol")
    private Rol rol = Rol.ROLE_USER;

    @Field("preferencia_notificacion")
    private PreferenciaNotificacion preferenciaNotificacion = PreferenciaNotificacion.EMAIL;

    @Field("token_push")
    private String tokenPush;

    @Field("recordatorio_activo")
    private boolean recordatorioActivo = false;

    // Restricción: nombre y email solo cambian una vez por semana
    @Field("fecha_ultimo_cambio_nombre")
    private LocalDateTime fechaUltimoCambioNombre;

    @Field("fecha_ultimo_cambio_email")
    private LocalDateTime fechaUltimoCambioEmail;

    @Field("activo")
    private boolean activo = true;
}
