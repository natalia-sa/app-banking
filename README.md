# 💳 Sistema de gestão Bancária

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- H2 Database
- Lombok
- Swagger
- JUnit 5
- Mockito

---

## Como executar

1. Clone o repositório:
2. Acesse o diretório raiz do repositório
3. Execute `./gradlew bootRun` ou execute através da ide
4. Para utilizar a API acesse http://localhost:8080/swagger-ui/index.html
5. Você também pode acessar o banco h2 pelo endereço http://localhost:8080/h2-console. Credenciais de acesso podem ser encontradas no application.properties

## Endpoints da API

### Conta (`/conta`)

#### **POST /conta**
Cria uma nova conta bancária.

- **Exemplo de Requisição**:
  ```json
  {
    "numero_conta": 12345,
    "saldo": 100.0
  }

#### **GET /conta**
Retorna uma conta bancaria através de seu número.

- **Exemplo de Response**:
  ```json
  {
    "numero_conta": 12345,
    "saldo": 100.0
  }

### Transação (`/transacao`)

#### **POST /transacao**
Realiza uma transação em uma conta bancária

- **Exemplo de Request**:
  ```json
  {
    "forma_pagamento": "P",
    "numero_conta": 1234,
    "valor": 150
  }
