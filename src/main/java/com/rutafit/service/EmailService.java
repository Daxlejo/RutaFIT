package com.rutafit.service;

public interface EmailService {
    void enviarContrasena(String email, String nombre, String contrasena);
    void enviarConfirmacionSesion(String email, String nombre, String rutina, int totalSeries);
    void enviarRecordatorio(String email, String nombre);
}
