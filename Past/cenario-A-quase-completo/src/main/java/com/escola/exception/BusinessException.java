package com.escola.exception;

/** Regra de negócio violada (ex.: e-mail duplicado). */
public class BusinessException extends RuntimeException {
    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
