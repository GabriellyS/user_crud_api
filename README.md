# User CRUD API

API REST desenvolvida em **Java com Spring Boot** para cadastro e consulta de usuários.  
Criado com foco em boas práticas de arquitetura, separação de camadas e testes unitários.  

O projeto segue uma arquitetura em camadas utilizando o ecossistema Spring:

* **Controller** → Responsável pelos endpoints REST
* **Service** → Contém as regras de negócio
* **Repository** → Acesso aos dados com Spring Data JPA
* **Exception Handler** → Tratamento centralizado de erros

## Funcionalidades

* Cadastro de usuário (**nome, email e senha**)
* Consulta de usuários com:
  * Paginação
  * Filtro opcional por qualquer parte do nome
* Consulta de usuário por **ID**
* Envio de email (via log no console) ao **criar ou atualizar** um usuário

## Tecnologias Utilizadas

* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **MySQL Database**
* **Docker** (Build externo do banco de dados)  
* **Git & GitHub** (controle de versão)

![Java](https://img.icons8.com/dusk/70/java-coffee-cup-logo.png)
![Intellij](https://img.icons8.com/color/70/intellij-idea.png)


## Endpoints da API

### Criar usuário

`POST /users`

### Listar usuários (com paginação e filtro opcional)

`GET /users`

**Parâmetros opcionais:**

* `name` → filtro por parte do nome
* `page` → número da página
* `size` → quantidade de registros por página

### Buscar usuário por ID

`GET /users/{id}`

### Atualizar usuário

`PUT /users/{id}`

## Testes Unitários

Os testes unitários foram implementados na classe `UsuarioServiceTest`, focando na camada de serviço por concentrar as regras de negócio.

### Cenários cobertos:

* Criação de usuário
* Listagem com e sem filtro
* Atualização de usuário
* Busca por ID
* Tratamento de exceções (usuário não encontrado)

## Como executar o projeto (Java 17+)

1. Clone o repositório:

```bash
git clone https://github.com/GabriellyS/user_crud_api.git
```

2. Acesse a pasta do projeto:

```bash
cd user_crud_api
```

3. Suba o banco de dados usando o docker compose:

```bash
docker compose up -d
```

4. Execute a aplicação:

```bash
mvn spring-boot:run
```

Projeto desenvolvido para fins de estudo e avaliação técnica
