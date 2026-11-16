package com.escola.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/** Auditoria: registra QUEM alterou dados (útil para LGPD). */
@Aspect
@Component
@Slf4j
public class AuditAspect {

    @AfterReturning("execution(* com.escola.aluno.AlunoService.criar(..)) "
            + "|| execution(* com.escola.aluno.AlunoService.atualizar(..)) "
            + "|| execution(* com.escola.aluno.AlunoService.remover(..))")
    public void auditar(JoinPoint jp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String quem = (auth != null) ? auth.getName() : "anonimo";
        log.info("[AUDITORIA] usuario={} acao={}", quem, jp.getSignature().getName());
    }
}
