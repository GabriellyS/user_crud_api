package com.example.user_crud_api.controller;

import com.example.user_crud_api.exception.UsuarioNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<?> notFound(UsuarioNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }
}
