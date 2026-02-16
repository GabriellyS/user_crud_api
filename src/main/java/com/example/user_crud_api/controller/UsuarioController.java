package com.example.user_crud_api.controller;

import com.example.user_crud_api.dto.CreateUserDTO;
import com.example.user_crud_api.dto.ResponseUserDTO;
import com.example.user_crud_api.model.UsuarioModel;
import com.example.user_crud_api.service.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    private UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createUser (@RequestBody CreateUserDTO usuarioDto){
        service.createUser(usuarioDto.nome(), usuarioDto.email(), usuarioDto.senha());
        return ResponseEntity.status(201).body("Usuário criado - Email enviado");
    }

    @GetMapping
    public ResponseEntity<Page> listUser (@RequestParam (required = false) String nome, @PageableDefault(size = 10, sort = "name") Pageable pageable){
        return ResponseEntity.status(200).body(service.listarUsuarios(nome,pageable)
                .map(usuarioModel -> new ResponseUserDTO(usuarioModel.getName(),usuarioModel.getEmail())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById (@PathVariable Long id){
        Optional<UsuarioModel> isfound = service.findById(id);
        if(isfound.isPresent()) {
            UsuarioModel foundUser = isfound.get();
            return ResponseEntity.status(200).body(service.findById(id)
                    .map(usuarioModel -> new ResponseUserDTO(usuarioModel.getName(), usuarioModel.getEmail())));
        }
        return ResponseEntity.status(404).body("User not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editUser (@PathVariable Long id, @RequestBody CreateUserDTO userDTO){
        boolean isFound = service.updateUsuario(id, userDTO.nome(), userDTO.email(), userDTO.senha());
        if (isFound){
            return ResponseEntity.status(200).body("Usuário atualizado");
        }else {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
    }
}
