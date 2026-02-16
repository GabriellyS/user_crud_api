package com.example.user_crud_api.service;

import com.example.user_crud_api.exception.UsuarioNotFoundException;
import com.example.user_crud_api.model.UsuarioModel;
import com.example.user_crud_api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

//Extendo usando o Mockito para simulações de testes unitários
@ExtendWith(MockitoExtension.class)

//Os testes unitários focam no UsuarioService por contem as regras de negócio
//Cobrindo cenários de sucesso e falha para garantir consistência e previsibilidade do comportamento.
public class UsuarioServiceTest {

    //Simulo o UsuarioReposotory, não preciso acessar o banco verdadeiro
    @Mock
    private UsuarioRepository repository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UsuarioService service;

    Pageable pageable = PageRequest.of(0, 10);

    //Valida o fluxo principal de criação e garante que a persistência e notificação via email ocorram
    @Test
    public void createUser_ShouldSave() {
        //Quando
        service.createUser("Salem", "salem@cat.com", "Salem123");

        //Entao
        verify(repository).save(any(UsuarioModel.class));
        verify(emailService).sendEmail("salem@cat.com", "cadastrado");
    }

    //Verifica quando um filtro de nome é informado, e garante se o serviço utiliza o metodo adequado, respeitando a paginação
    @Test
    public void listUsers_WhenHavePartOfNome() {
        service.listUsers("sal", pageable);
        verify(repository).findByNameContainingIgnoreCase("sal", pageable);
    }

    //Verifica que na ausência de um filtro de nome, a service retorna todos os usuarios respeitando a paginação
    @Test
    public void listUsers_WhenHaveNotName(){
        service.listUsers("", pageable);
        verify(repository).findAll(pageable);
    }

    //Assegurar que, ao atualizar um usuario existente apenas os campos informados sejam alterados
    //os dados persistidos permaneçam inalterados e o email de notificação seja enviado após a atualização
    @Test
    public void updateUser_WhenUserExists(){
        UsuarioModel salem = new UsuarioModel("Salem","salem@cat.com","Salem123");
        when(repository.findById(1L)).thenReturn(Optional.of(salem));

        service.updateUser(1L,"", "", "Salem12");

        assertEquals("Salem", salem.getName());
        assertEquals("salem@cat.com", salem.getEmail());
        assertEquals("Salem12",salem.getSenha());

        verify(emailService).sendEmail("salem@cat.com", "atualizado");
    }

    //Valida o tratamento correto de exceção e evita alterações indevidas no sistema
    @Test
    public void updateUser_WhenUserDoesNotExists(){
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UsuarioNotFoundException.class, () -> service.updateUser(1L,"", "", "Salem12"));
    }

    //Valida se o serviço retorna corretamente um usuario quando o id existe no repositório
    @Test
    public void findById_WhenIdExists() {

        UsuarioModel salem = new UsuarioModel("Salem", "salem@cat.com", "Salem123");
        when(repository.findById(1L)).thenReturn(Optional.of(salem));

        UsuarioModel usuarioTeste = service.findById(1L);

        assertNotNull(usuarioTeste);
        assertEquals("Salem", usuarioTeste.getName());

    }

    //Valida se o serviço retorna corretamente o exception quando o id não existe no repositório
    @Test
    public void findById_WhenIdDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UsuarioNotFoundException.class, () -> service.findById(1L));
    }

}
