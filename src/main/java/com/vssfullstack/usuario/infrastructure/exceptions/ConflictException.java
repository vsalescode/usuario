package com.vssfullstack.usuario.infrastructure.exceptions;

public class ConflictException extends RuntimeException {

    public ConflictException(String mensagem) {
        super(mensagem);
    }

}
