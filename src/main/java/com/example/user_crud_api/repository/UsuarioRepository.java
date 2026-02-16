package com.example.user_crud_api.repository;

import com.example.user_crud_api.model.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    public Page<UsuarioModel> findByNameContainingIgnoreCase (String nome, Pageable pageable);

}
