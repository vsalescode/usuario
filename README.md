# 🔐 Serviço de Usuário (User Service)

O **"Guardião" da segurança** do ecossistema.\
Responsável pela gestão de identidades, autenticação (OAuth/JWT) e
persistência dos dados cadastrais de usuários, endereços e telefones.

------------------------------------------------------------------------

## 🚀 Visão Geral

Este microsserviço é a base de segurança de todo o sistema.\
Além do CRUD de usuários, ele atua como **Identity Provider**, sendo
responsável por:

-   Criptografia de senhas
-   Validação de credenciais
-   Emissão de Tokens JWT
-   Proteção de endpoints via Spring Security

------------------------------------------------------------------------

## ✅ Principais Responsabilidades

-   🔑 **Autenticação:** Login e geração de Token JWT
-   🔒 **Segurança de Dados:** Hash de senha com BCrypt
-   👤 **Gestão Cadastral:** Usuários com múltiplos endereços e
    telefones
-   🛡️ **API Segura:** Proteção stateless com JWT

------------------------------------------------------------------------

## 🛠️ Tecnologias Utilizadas

-   Java 17
-   Spring Boot 3
-   Spring Security
-   JWT (HMAC SHA256)
-   PostgreSQL
-   Hibernate / JPA
-   MapStruct
-   Lombok

------------------------------------------------------------------------

## ⚙️ Configuração do Banco

O serviço roda na porta **8080**.

Arquivo:

    src/main/resources/application.properties

### Exemplo de Configuração

``` properties
spring.application.name=usuario
server.port=8080

# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/db_usuario
spring.datasource.username=postgres
spring.datasource.password=sua-senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

> Certifique-se de que o banco `db_usuario` exista.

------------------------------------------------------------------------

## 🔐 Fluxo de Autenticação (JWT)

A autenticação é **stateless**, sem sessão no servidor.

1.  Cliente envia `email/senha` para `/usuario/login`
2.  Serviço valida senha (BCrypt)
3.  Se válido, gera JWT assinado (HMAC SHA256)
4.  Cliente envia nos próximos requests:

```{=html}
<!-- -->
```
    Authorization: Bearer {token}

------------------------------------------------------------------------

## 🔌 Endpoints

### 📌 Públicos

  Método   Rota               Descrição
  -------- ------------------ -----------------------
  POST     `/usuario`         Cadastra novo usuário
  POST     `/usuario/login`   Retorna Token JWT

------------------------------------------------------------------------

### 🔒 Protegidos (Bearer Token obrigatório)

  Método   Rota                 Descrição
  -------- -------------------- ----------------------
  GET      `/usuario`           Busca usuário logado
  PUT      `/usuario`           Atualiza nome/senha
  DELETE   `/usuario/{email}`   Remove usuário

------------------------------------------------------------------------

## 📍 Endereços e Telefones (OneToMany)

### Endereço

-   POST `/usuario/endereco`
-   PUT `/usuario/endereco?id={id}`

### Telefone

-   POST `/usuario/telefone`
-   PUT `/usuario/telefone?id={id}`

------------------------------------------------------------------------

## 💾 Modelo de Dados

Relacionamento:

-   1 Usuário → N Endereços
-   1 Usuário → N Telefones

Estrutura:

-   **USUARIO**
    -   id (PK)
    -   email (UK)
    -   senha
    -   nome
-   **ENDERECO**
    -   id (PK)
    -   rua
    -   cidade
    -   cep
    -   usuario_id (FK)
-   **TELEFONE**
    -   id (PK)
    -   numero
    -   ddd
    -   usuario_id (FK)

O modelo relacional evita registros órfãos e garante integridade
referencial.

------------------------------------------------------------------------

## ▶️ Como Executar

1.  Suba o PostgreSQL
2.  Verifique as credenciais no `application.properties`
3.  Execute:

``` bash
mvn spring-boot:run
```

Disponível em:

    http://localhost:8080

------------------------------------------------------------------------

## 👨‍💻 Autor

Desenvolvido por **João Victor**

🔗 [LinkedIn](https://www.linkedin.com/in/vsalescode/)
🌐 [Portfólio](https://portfolio-vsalescode.vercel.app/)

