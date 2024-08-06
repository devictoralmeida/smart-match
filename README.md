<div align="center">
  <h1>Smart Match API</h1>
</div>
Smart Match é uma API Rest, que foi desenvolvida para facilitar o gerenciamento de empresas, vagas e candidatos de uma
plataforma de RH. Com possibilidades de cadastramento de vagas, aplicação do candidato e filtro de busca pelo mesmo,
exibição do currículo do candidato e listagem de informações das vagas. Desenvolvido utilizando Java e o framework Spring, com banco de dados MySQL
, banco de dados H2 e cobertura de testes com JUnit, autenticação usando o Spring Security,
token JWT, encriptação de senhas utilizando BCrypt e documentação desenvolvida pelo Swagger, tornando a aplicação flexível e robusta.

# 📒 Índice

* [Descrição](#descrição)
* [Requisitos Funcionais](#requisitos)
* [Features](#features)
* [Tecnologias](#tecnologias)
* [Endpoints](#endpoints)
* [Instalação](#instalação)

# 📌 <span id="requisitos">Requisitos Funcionais</span>

- [x] Realizar o cadastro de um usuário representando a empresa<br>
- [x] Cadastro de vaga por um usuário com perfil de empresa<br>
- [x] Realizar o cadastro do usuário com perfil de candidato<br>
- [x] Listar o currículo do usuário com perfil de candidato<br>
- [x] Listar as vagas disponíveis para o usuário com perfil de candidato por um filtro<br>
- [x] Aplicar o usuário com perfil de candidato a uma vaga<br>

## Features

- [x] Autenticação de usuário utilizando JWT Token<br>
- [x] Exibição de informações do próprio usuário em sessão ativa<br>
- [x] Adicionando mapeamento de CORS<br>
- [x] Modelo de domínio complexo<br>
- [x] Projeção com SQL nativo<br>
- [x] Docker-compose com imagem para um banco de dados PostgreSQL<br>

# 💻 <span id="tecnologias">Tecnologias</span>

- **Java**
- **Spring**
- **Spring Web**
- **Spring Boot DevTools**
- **Spring Data JPA**
- **Spring Bean Validation**
- **JUnit**
- **JWT**
- **BCrypt**
- **Swagger**
- **MySQL**
- **PostgreSQL**
- **H2 Database**
- **Docker**

# 📍 <span id="endpoints">Endpoints</span>

| Endpoint                                | Resumo                                                                                                                     | Autenticação 
|-----------------------------------------|----------------------------------------------------------------------------------------------------------------------------|--------------
| <kbd>POST /companies/auth </kbd>        | Responsável por autenticar o usuário com perfil de empresa, gerando o Bearer Token *JWT*                                   | Sim          
| <kbd>POST /companies </kbd>             | Responsável por realizar o cadastro de um usuário representando a empresa                                                  | Não          
| <kbd>POST /companies/jobs </kbd>        | Responsável por realizar o cadastro de vaga por um usuário com perfil de empresa                                           | COMPANY      
| <kbd>POST /candidates/auth </kbd>       | Responsável por autenticar o usuário com perfil de candidato, gerando o Bearer Token *JWT*                                 | Sim          
| <kbd>GET /candidates </kbd>             | Responsável por listar o currículo da usuário com perfil de candidato                                                      | CANDIDATE    
| <kbd>POST /candidates </kbd>            | Responsável por realizar o cadastro do usuário com perfil de candidato                                                     | Não          
| <kbd>GET /candidates/jobs </kbd>        | Responsável por listar as vagas disponíveis para o usuário com perfil de candidato informando um *query param* como filtro | CANDIDATE    
| <kbd>POST /candidates/jobs/apply </kbd> | Responsável por aplicar o usuário com perfil de candidato a uma vaga, informando o ID                                      | CANDIDATE    
| <kbd>GET /swagger-ui/index.html </kbd>  | Responsável por servir a documentação dos recursos da API                                                                  
| <kbd>GET /h2-console </kbd>             | Responsável por acesso ao *H2 Database*                                                                                    

# 🚀 <span id="instalação">Instalação</span>

```bash
  # Clone este repositório:
  $ git clone https://github.com/devictoralmeida/smart-match.git
  $ cd ./smart-match

  # Instalar as dependências:
  $ mvn clean install
  
  # Crie os containers:
  $ docker-compose up -d

  # Executar:
  $ mvn spring-boot:run
```
