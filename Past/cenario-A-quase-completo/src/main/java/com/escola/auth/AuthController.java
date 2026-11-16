package com.escola.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/registrar")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthDtos.Mensagem registrar(@Valid @RequestBody AuthDtos.Register req) {
        return service.registrar(req);
    }

    @GetMapping("/verificar-email")
    public AuthDtos.Mensagem verificar(@RequestParam String token) {
        return service.verificarEmail(token);
    }

    @PostMapping("/login")
    public AuthDtos.TokenResponse login(@Valid @RequestBody AuthDtos.Login req) {
        return service.login(req);
    }

    @PostMapping("/esqueci-senha")
    public AuthDtos.Mensagem esqueciSenha(@Valid @RequestBody AuthDtos.ForgotPassword req) {
        return service.esqueciSenha(req);
    }

    @PostMapping("/redefinir-senha")
    public AuthDtos.Mensagem redefinir(@Valid @RequestBody AuthDtos.ResetPassword req) {
        return service.redefinirSenha(req);
    }
}
