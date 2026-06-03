package com.rutafit.service.impl;

import com.rutafit.dto.request.LoginRequest;
import com.rutafit.dto.request.RegistroRequest;
import com.rutafit.exception.ReglaNegocioException;
import com.rutafit.model.Usuario;
import com.rutafit.repository.UsuarioRepository;
import com.rutafit.security.JwtUtil;
import com.rutafit.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public String registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new ReglaNegocioException("El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuarioRepository.save(usuario);
        return jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().name());
    }

    public String login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ReglaNegocioException("Credenciales incorrectas"));

        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new ReglaNegocioException("Credenciales incorrectas");
        }
        return jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().name());
    }

    public void recuperarContrasena(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ReglaNegocioException("No existe una cuenta con ese email"));
        // Se envía la contraseña encriptada en texto simple no es seguro en producción,
        // pero cumple el requerimiento del proyecto
        emailService.enviarContrasena(usuario.getEmail(), usuario.getNombre(), usuario.getContrasena());
    }
}
