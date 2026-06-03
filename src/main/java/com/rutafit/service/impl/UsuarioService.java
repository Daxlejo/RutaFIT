package com.rutafit.service.impl;

import com.rutafit.dto.request.EditarPerfilRequest;
import com.rutafit.exception.ReglaNegocioException;
import com.rutafit.exception.RecursoNoEncontradoException;
import com.rutafit.model.Usuario;
import com.rutafit.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
    }

    public Usuario editarPerfil(String email, EditarPerfilRequest request) {
        Usuario usuario = obtenerPorEmail(email);
        LocalDateTime ahora = LocalDateTime.now();

        if (request.getNombre() != null) {
            validarCambioSemanal(usuario.getFechaUltimoCambioNombre(), "nombre");
            usuario.setNombre(request.getNombre());
            usuario.setFechaUltimoCambioNombre(ahora);
        }

        if (request.getEmail() != null) {
            validarCambioSemanal(usuario.getFechaUltimoCambioEmail(), "email");
            if (usuarioRepository.existsByEmail(request.getEmail())) {
                throw new ReglaNegocioException("Ese email ya está en uso");
            }
            usuario.setEmail(request.getEmail());
            usuario.setFechaUltimoCambioEmail(ahora);
        }

        if (request.getContrasenaNueva() != null) {
            if (!passwordEncoder.matches(request.getContrasenaActual(), usuario.getContrasena())) {
                throw new ReglaNegocioException("La contraseña actual no es correcta");
            }
            usuario.setContrasena(passwordEncoder.encode(request.getContrasenaNueva()));
        }

        if (request.getPreferenciaNotificacion() != null) {
            usuario.setPreferenciaNotificacion(request.getPreferenciaNotificacion());
        }

        if (request.getTokenPush() != null) {
            usuario.setTokenPush(request.getTokenPush());
        }

        if (request.getRecordatorioActivo() != null) {
            usuario.setRecordatorioActivo(request.getRecordatorioActivo());
        }

        return usuarioRepository.save(usuario);
    }

    private void validarCambioSemanal(LocalDateTime ultimoCambio, String campo) {
        if (ultimoCambio != null && ultimoCambio.isAfter(LocalDateTime.now().minusDays(7))) {
            long diasRestantes = 7 - java.time.temporal.ChronoUnit.DAYS.between(ultimoCambio, LocalDateTime.now());
            throw new ReglaNegocioException("El " + campo + " solo puede cambiarse una vez por semana. Faltan " + diasRestantes + " días.");
        }
    }
}
