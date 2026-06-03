package com.rutafit.service.impl;

import com.rutafit.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void enviarContrasena(String email, String nombre, String contrasena) {
        enviar(email,
                "RutaFIT - Recuperación de contraseña",
                "Hola " + nombre + ",\n\nTu contraseña es: " + contrasena);
    }

    @Override
    public void enviarConfirmacionSesion(String email, String nombre, String rutina, int totalSeries) {
        enviar(email,
                "RutaFIT - Sesión registrada",
                "Hola " + nombre + ",\n\nTu sesión de '" + rutina + "' fue registrada exitosamente con " + totalSeries + " series.");
    }

    @Override
    public void enviarRecordatorio(String email, String nombre) {
        enviar(email,
                "RutaFIT - ¡Es hora de entrenar!",
                "Hola " + nombre + ",\n\nAún no has entrenado hoy. ¡Tú puedes!");
    }

    private void enviar(String destinatario, String asunto, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mailSender.send(mensaje);
    }
}
