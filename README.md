# 🧑‍💻 Agendador de Tarefas – Microserviço Usuário

![Java](https://img.shields.io/badge/Java-17+-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![MongoDB](https://img.shields.io/badge/MongoDB-NoSQL-green)
![JWT](https://img.shields.io/badge/Security-JWT-orange)
![OpenFeign](https://img.shields.io/badge/Communication-OpenFeign-informational)
![Build](https://img.shields.io/badge/Build-Maven-blueviolet)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)

Sistema de **agendamento de tarefas** desenvolvido com **arquitetura de microserviços**, focado em **escalabilidade**, **segurança** e **separação de responsabilidades**.

Os microserviços se comunicam entre si utilizando **Spring Cloud OpenFeign**, garantindo chamadas HTTP desacopladas, declarativas e de fácil manutenção.

---

## 🧱 Arquitetura Geral

```text
[BFF]
  ├── Usuario Service (✅ Completo)
  ├── Agendador Service (✅ Completo)
  ├── Notificacao Service (✅ Completo)
  └── Comunicação via OpenFeign
```

---

## 🔗 Comunicação entre Microserviços

- Comunicação síncrona via **REST**
- Clientes declarativos com **Spring Cloud OpenFeign**
- Reaproveitamento de JWT entre serviços
- Redução de acoplamento
- Facilidade de manutenção e testes

Exemplo de uso:
- Agendador valida usuário via token JWT
- Agendador consulta dados do Usuário quando necessário
- Notificação consumirá eventos do Agendador
- BFF centraliza chamadas aos microserviços

---

## 🧩 Microserviços

### 🧑‍💻 Usuario Service (✅ Completo)
- Cadastro e autenticação
- Emissão de JWT
- Gerenciamento de dados do usuário
- PostgreSQL

### ⏰ Agendador Service (✅ Completo)
- CRUD de tarefas
- Agendamento por data/hora
- Controle de status de notificação
- MongoDB

### 📧 Notificacao Service (🔜)
- Envio de emails
- Consumo de eventos do Agendador

### 🛜 BFF (🔜)
- Backend dedicado para o frontend
- Consumo de Microserviços

---

## 🛠️ Stack Tecnológica

- Java 17+
- Spring Boot
- Spring Security
- Spring Cloud OpenFeign
- JWT
- Spring Data JPA
- Spring Data MongoDB
- PostgreSQL
- MongoDB
- Maven
- Lombok

---

## 🛣️ Roadmap

- ✅ Usuario Service
- ✅ Agendador Service
- 🔜 Notificacao Service
- 🔜 BFF
- 🔜 Docker / Docker Compose
- 🔜 Testes Automatizados

---

## 📌 Observações

Projeto desenvolvido com foco em **arquitetura distribuída**, **segurança**, **boas práticas** e **preparação para escala**, ideal para portfólio e cenários reais.
