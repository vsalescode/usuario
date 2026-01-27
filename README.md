
# 📅 Task Scheduler – Microservices Architecture

![Java](https://img.shields.io/badge/Java-17+-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![JWT](https://img.shields.io/badge/Security-JWT-orange)
![OpenFeign](https://img.shields.io/badge/Communication-OpenFeign-informational)
![Build](https://img.shields.io/badge/Build-Maven-blueviolet)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)

Sistema de **agendamento de tarefas** desenvolvido com **arquitetura de microserviços**, focado em escalabilidade, segurança e separação de responsabilidades.

Os microserviços se comunicam entre si utilizando **Spring Cloud OpenFeign**, garantindo chamadas HTTP desacopladas, declarativas e de fácil manutenção.

---

## 🧱 Arquitetura Geral

```text
[BFF]
  ├── Usuario Service (✅ Completo)
  ├── Agendador Service (🚧 Em progresso)
  ├── Notificacao Service (🔜 Email)
  └── Comunicação via OpenFeign
```

---

## 🔗 Comunicação entre Microserviços

- Comunicação síncrona via **REST**
- Clientes declarativos com **Spring Cloud OpenFeign**
- Reduz acoplamento entre serviços
- Facilita manutenção e testes

Exemplo de uso:
- Agendador consulta dados do Usuário
- Notificação consome eventos do Agendador
- BFF centraliza chamadas aos microserviços

---

## 🧑‍💻 Microserviço de Usuário (Status: ✅ Completo)

Funcionalidades:
- Cadastro de usuários
- Autenticação com JWT
- Gerenciamento de dados pessoais
- Endereços e telefones
- Segurança com Spring Security

### 🔐 Autenticação
```
Authorization: Bearer <token>
```

---

## 🚀 Endpoints – Usuário

| Método | Endpoint | Descrição |
|------|--------|---------|
| POST | `/usuario` | Cadastro |
| POST | `/usuario/login` | Login |
| GET | `/usuario?email=` | Buscar usuário |
| PUT | `/usuario` | Atualizar usuário |
| DELETE | `/usuario/{email}` | Deletar usuário |
| POST | `/usuario/endereco` | Novo endereço |
| PUT | `/usuario/endereco?id=` | Atualizar endereço |
| POST | `/usuario/telefone` | Novo telefone |
| PUT | `/usuario/telefone?id=` | Atualizar telefone |

---

## 🗄️ Banco de Dados

- PostgreSQL
- Spring Data JPA
- Relacionamentos:
  - Usuário → Endereços
  - Usuário → Telefones

---

## 🛠️ Tecnologias

- Java 17+
- Spring Boot
- Spring Security
- Spring Cloud OpenFeign
- JWT
- Spring Data JPA
- PostgreSQL
- Maven
- Lombok

---

## ▶️ Executando Localmente

### Pré-requisitos
- Java 17+
- Maven
- PostgreSQL

### application.properties
```
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=postgres
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
```

### Run
```
mvn clean install
mvn spring-boot:run
```

---

## 🛣️ Roadmap

- ✅ Usuário
- 🚧 Agendador
- 🔜 Notificação por Email
- 🔜 BFF
- 🔜 Comunicação OpenFeign entre todos os serviços
- 🔜 Docker / Docker Compose
- 🔜 Testes Automatizados

---

## 📌 Observações

Projeto estruturado com foco em **arquitetura distribuída**, **segurança**, **boas práticas** e **comunicação desacoplada entre microserviços**.
