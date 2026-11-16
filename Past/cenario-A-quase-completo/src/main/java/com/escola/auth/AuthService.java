package com.escola.auth;

import com.escola.exception.BusinessException;
import com.escola.security.JwtService;
import com.escola.user.Role;
import com.escola.user.User;
import com.escola.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    // Tokens de verificação / recuperação ficam em memória (simplificação documentada no README).
    // Em produção: tabela no banco com validade + envio real de e-mail.
    private final Map<String, String> tokensVerificacao = new ConcurrentHashMap<>();
    private final Map<String, String> tokensRecuperacao = new ConcurrentHashMap<>();

    @Value("${app.auth.exigir-verificacao-email:false}")
    private boolean exigirVerificacao;

    @Transactional
    public AuthDtos.Mensagem registrar(AuthDtos.Register req) {
        if (users.existsByEmail(req.email())) {
            throw new BusinessException("E-mail já cadastrado");
        }
        // O papel NÃO vem da requisição: auto-cadastro é sempre ALUNO (segurança).
        users.save(User.builder()
                .nome(req.nome())
                .email(req.email())
                .senha(encoder.encode(req.senha()))
                .role(Role.ALUNO)
                .emailVerificado(false)
                .build());

        String token = UUID.randomUUID().toString();
        tokensVerificacao.put(token, req.email());
        log.info("[EMAIL SIMULADO] Verificar e-mail: GET /auth/verificar-email?token={}", token);
        return new AuthDtos.Mensagem("Cadastro realizado. Verifique seu e-mail.");
    }

    @Transactional
    public AuthDtos.Mensagem verificarEmail(String token) {
        String email = tokensVerificacao.remove(token);
        if (email == null) {
            throw new BusinessException("Token inválido");
        }
        users.findByEmail(email).ifPresent(u -> u.setEmailVerificado(true));
        return new AuthDtos.Mensagem("E-mail verificado");
    }

    @Transactional(readOnly = true)
    public AuthDtos.TokenResponse login(AuthDtos.Login req) {
        User user = users.findByEmail(req.email())
                .filter(u -> encoder.matches(req.senha(), u.getSenha()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));
        if (exigirVerificacao && !user.isEmailVerificado()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "E-mail ainda não verificado");
        }
        return new AuthDtos.TokenResponse(jwtService.gerar(user), "Bearer", user.getRole().name());
    }

    public AuthDtos.Mensagem esqueciSenha(AuthDtos.ForgotPassword req) {
        // Resposta sempre igual, para não revelar se o e-mail existe.
        users.findByEmail(req.email()).ifPresent(u -> {
            String token = UUID.randomUUID().toString();
            tokensRecuperacao.put(token, u.getEmail());
            log.info("[EMAIL SIMULADO] Redefinir senha: POST /auth/redefinir-senha token={}", token);
        });
        return new AuthDtos.Mensagem("Se o e-mail existir, enviaremos instruções.");
    }

    @Transactional
    public AuthDtos.Mensagem redefinirSenha(AuthDtos.ResetPassword req) {
        String email = tokensRecuperacao.remove(req.token());
        if (email == null) {
            throw new BusinessException("Token inválido");
        }
        users.findByEmail(email).ifPresent(u -> u.setSenha(encoder.encode(req.novaSenha())));
        return new AuthDtos.Mensagem("Senha redefinida");
    }
}
