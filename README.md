# Como Rodar o Projeto `api-agencia-viagem`

Este guia descreve como configurar e rodar o projeto Spring Boot com PostgreSQL usando Docker Compose.

---

## **Pré-requisitos**

Certifique-se de ter os seguintes softwares instalados em sua máquina:

- **Docker**: [Instruções de instalação](https://docs.docker.com/get-docker/)
- **Docker Compose**: Geralmente incluído com o Docker Desktop
- **Java 17**: [Instruções de instalação](https://adoptium.net/)
- **Maven**: [Instruções de instalação](https://maven.apache.org/install.html)

---

## **Configuração Inicial**

### Clonar o Repositório

Clone o repositório para sua máquina local:

```bash
git clone https://github.com/seu-repositorio/api-agencia-viagem.git
cd api-agencia-viagem
```

### Configuração do Banco de Dados no Docker

O projeto utiliza PostgreSQL como banco de dados. O arquivo `docker-compose.yml` na raiz do projeto já está configurado para criar um contêiner PostgreSQL.

**Arquivo `docker-compose.yml`:**

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    container_name: postgres_container
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: agencia
      POSTGRES_USER: root
      POSTGRES_PASSWORD: root
    volumes:
      - postgres_data:/var/lib/postgresql/data
    networks:
      - app_network

volumes:
  postgres_data:

networks:
  app_network:
    driver: bridge
```

---

## **Passo a Passo para Rodar o Projeto**

### 1. **Iniciar o Banco de Dados com Docker Compose**

No diretório do projeto, execute o seguinte comando para subir o contêiner PostgreSQL:

```bash
docker-compose up -d
```

Isso:
- Baixará a imagem do PostgreSQL.
- Criará o banco de dados `agencia` com o usuário e senha configurados.

### 2. **Construir o Projeto**

Compile o projeto e resolva as dependências com Maven:

```bash
mvn clean install
```

### 3. **Rodar a Aplicação**

Inicie o servidor Spring Boot:

```bash
mvn spring-boot:run
```

Se tudo estiver configurado corretamente, a aplicação estará disponível em:

```
http://localhost:8080
```

---

## **Testando a API**

Use ferramentas como **Postman**, **cURL** ou um navegador para testar os endpoints da API.

### Endpoints Disponíveis

1. **Cadastrar um destino:**
   - **Endpoint:** `POST /api/destinations`
   - **Corpo da Requisição (JSON):**
     ```json
     {
       "name": "Paris",
       "location": "França",
       "description": "Cidade Luz"
     }
     ```

2. **Listar destinos:**
   - **Endpoint:** `GET /api/destinations`

3. **Buscar destino por nome:**
   - **Endpoint:** `GET /api/destinations/search/name`
   - **Query Param:** `name`
   - **Exemplo:** `/api/destinations/search/name?name=Paris`

4. **Avaliar um destino:**
   - **Endpoint:** `PATCH /api/destinations/{id}/rate`
   - **Query Param:** `grade`
   - **Exemplo:** `/api/destinations/1/rate?grade=4.5`

5. **Excluir um destino:**
   - **Endpoint:** `DELETE /api/destinations/{id}`

---

## **Encerrando o Banco de Dados**

Para parar o contêiner PostgreSQL, execute:

```bash
docker-compose down
```

Para remover também os dados persistidos:

```bash
docker-compose down -v
```

---

## **Problemas Comuns**

1. **Erro de Conexão com o Banco de Dados:**
   - Certifique-se de que o contêiner do PostgreSQL está rodando.
   - Verifique se a URL do banco de dados, usuário e senha estão corretos no `application.properties`.

2. **Dependências Não Resolvidas:**
   - Execute:
     ```bash
     mvn dependency:purge-local-repository
     mvn clean install
     ```

3. **Porta 5432 Ocupada:**
   - Verifique se já existe outra instância do PostgreSQL rodando na porta 5432:
     ```bash
     sudo lsof -i :5432
     ```
   - Alterne a porta no `docker-compose.yml` e no `application.properties` se necessário.
