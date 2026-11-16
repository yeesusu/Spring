package com.escola.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * POA / AOP: preocupação transversal (log + tempo) fora da regra de negócio.
 * Intercepta todo método de classes *Service. Também prova o RNF "resposta < 2s".
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    private static final long LIMITE_MS = 2000;

    @Around("execution(* com.escola..*Service.*(..))")
    public Object medirTempo(ProceedingJoinPoint pjp) throws Throwable {
        long inicio = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long ms = System.currentTimeMillis() - inicio;
            if (ms > LIMITE_MS) {
                log.warn("[AOP] {} LENTO: {}ms", pjp.getSignature().toShortString(), ms);
            } else {
                log.info("[AOP] {} executou em {}ms", pjp.getSignature().toShortString(), ms);
            }
        }
    }
}
