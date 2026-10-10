# Projeto - Cidades ESG Inteligentes

API Java Spring Boot para gerenciamento de empresas, setores, equipamentos, consumo de energia e alertas de consumo elevado.

O projeto usa Maven, Spring Boot, Oracle Database, Flyway, Spring Security, Swagger/OpenAPI, Docker, Docker Compose e GitHub Actions.

## Como executar localmente com Docker

### Requisitos

- Java 21
- Maven
- Docker
- Docker Compose
- Acesso ao banco Oracle informado pela FIAP

### Configurar variáveis de ambiente

Crie um arquivo `.env` a partir do exemplo:

```bash
cp .env.example .env
Preencha as variáveis no arquivo .env:
DATASOURCE_URL=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
DATASOURCE_USERNAME=seu_usuario_oracle
DATASOURCE_PASSWORD=sua_senha_oracle
APP_ADMIN_USERNAME=admin
APP_ADMIN_PASSWORD=sua_senha_admin
JWT_SECRET=seu_segredo
SERVER_PORT=8081
SPRING_PROFILES_ACTIVE=local
O arquivo .env não deve ser enviado para o GitHub, pois contém credenciais reais. Apenas o .env.example deve ser versionado.
Executar com Docker Compose
Com o .env preenchido, execute:
docker compose up --build
A API ficará disponível em:
http://localhost:8081
Swagger:
http://localhost:8081/swagger-ui/index.html
Executar localmente com Maven
Também é possível executar a aplicação com Maven:
mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"
Pipeline CI/CD
O pipeline foi implementado com GitHub Actions no arquivo:
.github/workflows/ci-cd.yml
O pipeline executa:
- checkout do código;
- configuração do Java 21;
- cache das dependências Maven;
- execução dos testes com mvn test;
- empacotamento da aplicação com mvn package;
- build da imagem Docker;
- deploy simulado para staging ou produção.
Ambientes do pipeline
O pipeline separa os ambientes por branch:
- develop: executa o deploy de staging.
- main ou master: executa o deploy de produção.
Para uma entrega real, os ambientes staging e production devem receber secrets próprios no GitHub Actions, como:
DATASOURCE_URL
DATASOURCE_USERNAME
DATASOURCE_PASSWORD
APP_ADMIN_PASSWORD
JWT_SECRET
Containerização
O Dockerfile usa build multi-stage:
- primeiro estágio: Maven com Java 21 para compilar e gerar o .jar;
- segundo estágio: imagem Java 21 JRE para executar a aplicação.
Essa estratégia evita depender de um arquivo target/*.jar previamente gerado na máquina local.
Docker Compose
O projeto possui arquivo compose.yaml para orquestrar a aplicação.
Ele configura:
- serviço da API;
- build a partir do Dockerfile;
- porta externa 8081 apontando para a porta interna 8080;
- variáveis de ambiente;
- rede Docker dedicada;
- healthcheck do container.
Comando principal:
docker compose up --build
Comando para validar a configuração:
docker compose --env-file .env.example config
Ambientes
Arquivos de configuração:
- application.properties: configuração base por variáveis de ambiente;
- application-local.properties: ajustes locais;
- application-staging.properties: ajustes para homologação;
- application-prod.properties: ajustes para produção;
- application-test.properties: configuração de testes com H2 em memória.
Testes
Os testes usam o profile test, com banco H2 em memória. Assim, o comando abaixo não depende do Oracle remoto:
mvn test
Foram adicionados testes para:
- ApplicationTests
- EmpresaServiceTest
- EquipamentoServiceTest
- SetorServiceTest
- ConsumoServiceTest
- AlertaServiceTest
Resultado esperado:
Tests run: 17, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
Prints do funcionamento
As evidências foram incluídas na documentação técnica em PPTX/PDF.
Foram registrados prints de:
- GitHub Actions executando o pipeline;
- build e testes no pipeline;
- deploy de staging;
- deploy de produção;
- mvn test com testes aprovados;
- mvn package -DskipTests com build aprovado;
- docker compose up --build;
- container em execução com docker ps;
- Swagger aberto em localhost:8081;
- endpoint GET /empresas;
- endpoint GET /equipamentos;
- endpoint GET /alertas/recentes.
Tecnologias utilizadas
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
Checklist de entrega
- [x] Projeto compactado em ZIP com estrutura organizada
- [x] Dockerfile funcional
- [x] Docker Compose configurado
- [x] Pipeline com etapas de build, teste e deploy
- [x] README.md com instruções e evidências
- [x] Documentação técnica com evidências em PPTX/PDF
- [x] Deploy realizado nos ambientes staging e produção
Observações para entrega
Antes de compactar o projeto, não incluir:
.env
target/
.idea/
.git/
O ZIP final deve conter:
Dockerfile
compose.yaml ou docker-compose.yml
src/
README.md
.env.example
.github/workflows/
pom.xml
documentação técnica em PPTX ou PDF
