package com.escola.config;

import com.escola.user.Role;
import com.escola.user.User;
import com.escola.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Cria usuários de demonstração na primeira execução. */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        criarSeNaoExistir("Administrador", "admin@escola.com", "admin123", Role.ADMIN);
        criarSeNaoExistir("Professor Demo", "prof@escola.com", "prof123", Role.PROFESSOR);
    }

    private void criarSeNaoExistir(String nome, String email, String senha, Role role) {
        if (!users.existsByEmail(email)) {
            users.save(User.builder()
                    .nome(nome)
                    .email(email)
                    .senha(encoder.encode(senha))
                    .role(role)
                    .emailVerificado(true)
                    .build());
        }
    }
}
