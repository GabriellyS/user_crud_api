package com.example.user_crud_api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    public void sendEmail (String email, String razao){
        log.info("Destinatário: {} / Assunto: Usuário {} com sucesso ",email, razao);
    }

}
