package com.example.user_crud_api.service;

import com.example.user_crud_api.exception.UsuarioNotFoundException;
import com.example.user_crud_api.model.UsuarioModel;
import com.example.user_crud_api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsuarioService {

    private UsuarioRepository repository;
    private EmailService emailService;

    public UsuarioService(UsuarioRepository usuarioRepository, EmailService emailService) {
        this.repository = usuarioRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void createUser (String nome, String email, String senha){
        repository.save(new UsuarioModel(nome,email,senha));

        /*Como nesse caso o email é sempre enviado, não precisa lançar a exceção
        já que uma vez salvo não teremos erros no envio do email*/
        emailService.sendEmail(email, "cadastrado");
    }

    @Transactional
    public Page<UsuarioModel> listUsers (String nome, Pageable pageable){
        if (nome != null && !nome.isBlank()){
            return repository.findByNameContainingIgnoreCase(nome,pageable);
        }else {
            return repository.findAll(pageable);
        }
    }

    public UsuarioModel findById (long id){
        return repository.findById(id)
                .orElseThrow(UsuarioNotFoundException::new);
    }

    @Transactional
    public void updateUser(Long id, String nome, String email, String senha){
        UsuarioModel usuarioEncontrado = repository.findById(id)
                .orElseThrow(UsuarioNotFoundException::new);
        if (nome != null && !nome.isBlank()){
            usuarioEncontrado.setName(nome);
        }
        if (email != null && !email.isBlank()){
            usuarioEncontrado.setEmail(email);
        }
        if ((senha != null && !senha.isBlank())){
            usuarioEncontrado.setSenha(senha);
        }
        emailService.sendEmail(usuarioEncontrado.getEmail(), "atualizado");
    }
}
