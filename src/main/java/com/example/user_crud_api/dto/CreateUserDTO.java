package com.example.user_crud_api.dto;

public record CreateUserDTO(
        String nome,
        String email,
        String senha
) {
}
