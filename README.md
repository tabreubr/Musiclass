# Musiclass — Backend

[![CI](https://github.com/tabreubr/Musiclass/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/tabreubr/Musiclass/actions/workflows/ci.yml)

API REST para organizar aulas de música: alunos, aulas, lições, metas de
desenvolvimento e de progresso, e a comunicação entre instrutor e aluno.

> **Status:** em desenvolvimento. A integração com o
> [frontend](https://github.com/tabreubr/musiclass-front) está em correção.

## Stack

- Java 21 e Spring Boot 4.0.5 (Web MVC, Data JPA, Validation)
- Spring Security com autenticação JWT stateless (papéis `INSTRUCTOR`, `STUDENT`, `ADMIN`)
- PostgreSQL 16, com o schema versionado pelo Flyway
- Testes com JUnit 5 e Testcontainers (PostgreSQL real em Docker)
- Maven (via Maven Wrapper) e GitHub Actions

## Como rodar localmente

Pré-requisitos: JDK 21 e Docker.

1. Suba o banco (só o PostgreSQL, em `localhost:5432`):

   ```bash
   docker compose up -d
   ```

2. Rode a aplicação com o perfil `dev`:

   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
   ```

   No Windows (PowerShell), use `.\mvnw.cmd` no lugar de `./mvnw`. Pela IDE,
   rode `MusiclassApplication` com o perfil ativo `dev`.

A API sobe em `http://localhost:8080`. O Flyway cria as tabelas na primeira
execução.

O perfil `dev` traz valores **apenas para a máquina local** (senha do banco,
segredo JWT) e cria dois instrutores de exemplo (`thiago@musiclass.com` e
`gui@musiclass.com`) com a senha definida em `SEED_INSTRUCTOR_PASSWORD`
(padrão `123456`). Para obter um token (exemplo em bash/Git Bash; no Windows,
use o Postman ou outro cliente HTTP com o mesmo corpo JSON):

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "thiago@musiclass.com", "password": "123456"}'
```

### Variáveis de ambiente

Fora do perfil `dev`, a configuração base não tem valores padrão para segredos:
se `JWT_SECRET` ou `SPRING_DATASOURCE_PASSWORD` faltarem, a aplicação não sobe.

| Variável | Padrão | Descrição |
|---|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/musiclass` | URL do banco |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | — (obrigatória) | Senha do banco |
| `JWT_SECRET` | — (obrigatória) | Segredo de assinatura dos tokens |
| `JWT_EXPIRATION` | `86400000` | Validade do token, em milissegundos |
| `FRONTEND_URL` | `http://localhost:3000` | Origem liberada no CORS |

## Testes

```bash
./mvnw verify
```

Requer o Docker em execução: os testes de integração sobem um PostgreSQL
descartável com o Testcontainers e chamam a API por HTTP, passando pela
segurança. Não é preciso configurar variáveis; o perfil `test` já tem o que
precisa. O mesmo comando roda no CI a cada push e pull request para a `main`.

## Autor

Thiago Linares de Abreu

## Licença

[MIT](LICENSE)
