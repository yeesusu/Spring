package com.escola.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** DTOs = objetos que entram/saem da API (nunca expomos a Entity direto). */
public final class AuthDtos {

    private AuthDtos() {
    }

    public record Register(@NotBlank String nome,
                           @NotBlank @Email String email,
                           @NotBlank @Size(min = 6, message = "mínimo 6 caracteres") String senha) {
    }

    public record Login(@NotBlank @Email String email, @NotBlank String senha) {
    }

    public record TokenResponse(String token, String tipo, String role) {
    }

    public record ForgotPassword(@NotBlank @Email String email) {
    }

    public record ResetPassword(@NotBlank String token,
                                @NotBlank @Size(min = 6, message = "mínimo 6 caracteres") String novaSenha) {
    }

    public record Mensagem(String mensagem) {
    }
}
