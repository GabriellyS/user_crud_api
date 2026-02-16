package com.example.user_crud_api.exception;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException() {
        super("Usuário não encontrado");
    }
}

