# Projeto - Gestao Energetica ESG

API Java Spring Boot para gerenciamento de empresas, setores, equipamentos, consumo de energia e alertas de consumo elevado. O projeto usa Maven, Spring Boot, Oracle Database, Flyway, Spring Security, Swagger/OpenAPI, Docker e GitHub Actions.

## Como executar localmente

Requisitos:

- Java 21
- Maven
- Docker e Docker Compose, para execucao containerizada
- Acesso ao banco Oracle informado pela FIAP

Crie um arquivo `.env` a partir do exemplo:

```bash
cp .env.example .env
```

Preencha as variaveis:

```env
DATASOURCE_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
DATASOURCE_USERNAME=seu_usuario_oracle
DATASOURCE_PASSWORD=sua_senha_oracle
APP_ADMIN_USERNAME=admin
APP_ADMIN_PASSWORD=sua_senha_admin
JWT_SECRET=seu_segredo
SERVER_PORT=8081
```

Para executar com Maven:

```bash
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
```

Swagger:

```text
http://localhost:8081/swagger-ui/index.html
```

## Como executar com Docker

Com o `.env` preenchido, rode:

```bash
docker compose up --build
```

A API ficara disponivel na porta definida em `SERVER_PORT`.

## Pipeline CI/CD

O pipeline esta em `.github/workflows/ci-cd.yml` e executa:

- build e testes com Java 21;
- empacotamento Maven;
- build da imagem Docker;
- deploy simulado para staging quando houver push na branch `develop`;
- deploy simulado para producao quando houver push na branch `main` ou `master`.

Para uma entrega real, os ambientes `staging` e `production` devem receber secrets proprios no GitHub Actions, como `DATASOURCE_URL`, `DATASOURCE_USERNAME`, `DATASOURCE_PASSWORD`, `APP_ADMIN_PASSWORD` e `JWT_SECRET`.

## Containerizacao

O `Dockerfile` usa build multi-stage:

- primeiro estagio: Maven com Java 21 para compilar e gerar o `.jar`;
- segundo estagio: imagem Java 21 JRE para executar a aplicacao.

Essa estrategia evita depender de um arquivo `target/*.jar` previamente gerado na maquina local.

## Ambientes

Arquivos de configuracao:

- `application.properties`: configuracao base por variaveis de ambiente;
- `application-local.properties`: ajustes locais;
- `application-staging.properties`: ajustes para homologacao;
- `application-prod.properties`: ajustes para producao;
- `application-test.properties`: configuracao de testes com H2 em memoria.

## Testes

Os testes usam o profile `test`, com banco H2 em memoria. Assim, o comando abaixo nao depende do Oracle remoto:

```bash
mvn test
```

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- Bean Validation
- Flyway
- Oracle JDBC
- H2 para testes
- Swagger/OpenAPI
- Maven
- Docker
- Docker Compose
- GitHub Actions

## Prints do funcionamento

Inclua aqui prints de:

- aplicacao rodando localmente;
- Swagger aberto;
- `mvn test` executado com sucesso;
- Docker Compose executando;
- workflow do GitHub Actions com build, testes e deploy staging/producao.

## Checklist de entrega

| Item | OK |
| --- | --- |
| Projeto compactado em ZIP com estrutura organizada | ☐ |
| Dockerfile funcional | ☐ |
| docker-compose.yml ou arquivos Kubernetes | ☐ |
| Pipeline com etapas de build, teste e deploy | ☐ |
| README.md com instrucoes e prints | ☐ |
| Documentacao tecnica com evidencias em PDF ou PPT | ☐ |
| Deploy realizado nos ambientes staging e producao | ☐ |
