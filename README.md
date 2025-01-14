# Projeto PS

## Descrição

Este projeto é uma aplicação Spring Boot que gerencia caminhões. Ele utiliza um banco de dados H2 em memória e valida os
dados na tabela FIPE antes de salvar.

## Pré-requisitos

### Sem docker

- Java 23
- Maven

### Com docker

- Docker

## Rodando a aplicação

1. Clone o repositório:
    ```sh
    git clone https://github.com/heitorfreitasferreira/agromercantil-ps
    cd agromercantil-ps
    ```

2. Compile o projeto:
    ```sh
    mvn clean install
    ```

3. Execute a aplicação:
    ```sh
    mvn spring-boot:run
    ```

A aplicação estará disponível em `http://localhost:8080`.

## Endpoints

### POST /api/trucks

Cadastra um novo caminhão.

### GET /api/trucks

Lista caminhões cadastrados.

### PUT /api/trucks/{id}

Atualiza informações de um caminhão.

## Testes

Para rodar os testes, execute:

```sh
mvn test
```

## Configurações

As configurações da aplicação estão no arquivo `src/main/resources/application.properties`.
