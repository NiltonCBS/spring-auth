# Spring Auth API

Este projeto é uma API REST desenvolvida com Spring Boot, responsável pela autenticação de usuários com JWT (JSON Web Token) e controle de despesas. Ela fornece endpoints para cadastro, login e gerenciamento de despesas pessoais por usuário autenticado.

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- JPA / Hibernate
- PostgreSQL
- Lombok

## 🎯 Funcionalidades

- Registro de usuários
- Autenticação com JWT
- Validação de token e controle de sessões
- CRUD de despesas (com relacionamento ao usuário)
- Proteção de endpoints com autenticação
- Controle de acesso por token

## 🛠️ Requisitos

- Java 17 ou superior
- Maven
- PostgreSQL

## ⚙️ Configuração

### 1. Variáveis de ambiente (application.properties)

Você pode configurar suas variáveis diretamente no `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/testbd
spring.datasource.username={SEU-USERNAME}
spring.datasource.password={SUA-PASSWORD}

spring.jpa.hibernate.ddl-auto=update

jwt.secret={CHAVE-SECRETA}
```
## Rodando o Projeto
```
# Clone o repositório
git clone https://github.com/NiltonCBS/spring-auth.git

# Acesse a pasta
cd spring-auth

# Compile e execute
./mvnw spring-boot:run
```
## 📂 Estrutura de Pastas

spring-auth/

├── src/

│   ├── main/

│   │   ├── java/com/example/login_auth_api/

│   │   │   ├── controllers/       # Controladores HTTP

│   │   │   ├── domain/            # Modelos (User, Despesa)

│   │   │       ├── despesa/

│   │   │       ├── user/

│   │   │   ├── dto/               # Transporta os dados entre diferentes componentes

│   │   │   ├── infra/             # JWT, autenticação, filtros

│   │   │       ├── cors/          # Segurança de requisições

│   │   │       ├── security/

│   │   │   └── repositories/      # Interfaces de acesso ao banco

│   │   └── resources/

│   │       └── application.properties

└── pom.xml

## 📑 Endpoints Principais

## Autenticação
POST /auth/register – Cadastro de novo usuário

POST /auth/login – Login e retorno do token JWT

## Despesas
GET /despesas/listar – Lista todas as despesas do usuário autenticado

POST /despesas/cadastrar – Cria uma nova despesa

PUT /despesas/alterar/{id} – Atualiza uma despesa

DELETE /despesas/deletar/{id} – Remove uma despesa

Todos os endpoints de despesas exigem o header Authorization: Bearer <token>.

## 🔒 Autenticação com JWT
O token JWT é gerado no login e deve ser enviado nas próximas requisições.

O filtro de autenticação valida o token antes de acessar qualquer endpoint protegido.

A autenticação está integrada ao Spring Security.
