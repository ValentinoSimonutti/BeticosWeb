package com.beticos.futbolapp.config;
import com.beticos.futbolapp.model.auth.Usuario;
import com.beticos.futbolapp.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {

            repo.deleteAll();

            if (repo.count() == 0) {

                Usuario u1 = new Usuario();
                u1.setNombre("Valen");
                u1.setEmail("valen@beticos.com");
                u1.setPassword(encoder.encode("123456"));

                Usuario u2 = new Usuario();
                u2.setNombre("Agus");
                u2.setEmail("agus@beticos.com");
                u2.setPassword(encoder.encode("123456"));

                repo.save(u1);
                repo.save(u2);
            }

        };
    }
}
