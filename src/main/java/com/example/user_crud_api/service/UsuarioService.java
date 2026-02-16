package com.example.user_crud_api.service;

import com.example.user_crud_api.model.UsuarioModel;
import com.example.user_crud_api.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Page<UsuarioModel> listarUsuarios (String nome, Pageable pageable){
        if (nome != null && !nome.isBlank()){
            return repository.findByNameContainingIgnoreCase(nome,pageable);
        }else {
            return repository.findAll(pageable);
        }
    }

    public Optional<UsuarioModel> findById (long id){
        return repository.findById(id);
    }

    @Transactional
    public boolean updateUsuario(Long id,String nome,String email, String senha){
        Optional<UsuarioModel> isFound = repository.findById(id);
        if (isFound.isPresent()){
            UsuarioModel usuarioEncontrado = isFound.get();
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
            return true;
        }else {
            log.info("Usuário não encontrado no sistema");
            return false;
        }
    }
}
