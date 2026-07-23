package com.beticos.futbolapp.service.auth;


import com.beticos.futbolapp.dto.LoginRequest;
import com.beticos.futbolapp.model.auth.Usuario;
import com.beticos.futbolapp.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario login(LoginRequest request) {

        Usuario usuario = usuarioRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Usuario o contraseña incorrectos"));

        boolean passwordCorrecta = passwordEncoder.matches(
                request.getPassword(),
                usuario.getPassword()
        );

        if (!passwordCorrecta) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        return usuario;
    }
}
