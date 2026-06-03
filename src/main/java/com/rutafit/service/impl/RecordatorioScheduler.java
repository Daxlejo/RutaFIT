package com.rutafit.service.impl;

import com.rutafit.model.PreferenciaNotificacion;
import com.rutafit.repository.SesionRepository;
import com.rutafit.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.rutafit.service.EmailService;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class RecordatorioScheduler {

    private final UsuarioRepository usuarioRepository;
    private final SesionRepository sesionRepository;
    private final EmailService emailService;

    // Se ejecuta todos los días a las 8am
    @Scheduled(cron = "0 0 8 * * *")
    public void enviarRecordatoriosDiarios() {
        usuarioRepository.findAll().stream()
                .filter(u -> u.isRecordatorioActivo() && u.isActivo())
                .filter(u -> u.getPreferenciaNotificacion() == PreferenciaNotificacion.EMAIL
                        || u.getPreferenciaNotificacion() == PreferenciaNotificacion.AMBAS)
                .filter(u -> !sesionRepository.existsByUsuarioIdAndFecha(u.getId(), LocalDate.now()))
                .forEach(u -> emailService.enviarRecordatorio(u.getEmail(), u.getNombre()));
    }
}
