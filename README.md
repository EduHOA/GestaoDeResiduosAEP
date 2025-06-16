# AEP Gestão de Resíduos

Sistema de gestão de resíduos desenvolvido para a AEP (Associação Empresarial de Portugal), permitindo o controle e monitoramento de resíduos industriais, incluindo coleta, transporte, tratamento e destinação final.

## 🚀 Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- MySQL 8.0
- Maven
- Swagger/OpenAPI 3.0
- JWT (JSON Web Tokens)

### Frontend
- React 18
- TypeScript
- Material-UI (MUI)
- React Router
- Axios
- React Query
- React Hook Form
- Yup (validação)

## 📋 Pré-requisitos

- Java 17 ou superior
- Node.js 18 ou superior
- MySQL 8.0
- Maven
- Docker e Docker Compose (opcional, para execução em containers)

## 🔧 Configuração do Ambiente

### 1. Configuração do Banco de Dados

#### Usando Docker (Recomendado)
```bash
# Iniciar o container MySQL
docker run --name aep-mysql -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql:8.0

# Criar o banco de dados
docker exec -it aep-mysql mysql -uroot -proot -e "CREATE DATABASE aep_residuos;"
```

#### Usando MySQL Local
1. Instale o MySQL 8.0
2. Crie um banco de dados chamado `aep_residuos`
3. Configure o usuário e senha no arquivo `application.properties`

### 2. Configuração do Backend

#### Variáveis de Ambiente
Crie um arquivo `.env` na raiz do projeto backend com as seguintes variáveis:
```properties
# Database
DB_HOST=localhost
DB_PORT=3306
DB_NAME=aep_residuos
DB_USER=root
DB_PASSWORD=root

# JWT
JWT_SECRET=sua_chave_secreta_aqui
JWT_EXPIRATION=86400000
```

#### Executando o Backend

##### Usando Maven
```bash
# Na pasta raiz do projeto
cd AepResiduos
./mvnw spring-boot:run
```

##### Usando Docker
```bash
# Na pasta raiz do projeto
docker-compose up -d
```

O backend estará disponível em `http://localhost:8080`

### 3. Configuração do Frontend

```bash
# Na pasta do frontend
cd frontend
npm install
npm start
```

O frontend estará disponível em `http://localhost:3000`

## 📚 Documentação da API

A documentação da API está disponível através do Swagger UI:
- URL: `http://localhost:8080/api/swagger-ui.html`
- Documentação OpenAPI: `http://localhost:8080/api/v3/api-docs`

## 🔐 Autenticação e Segurança

O sistema utiliza JWT (JSON Web Tokens) para autenticação. Para acessar as APIs protegidas:

1. Faça login usando a rota `/api/auth/login`
2. Use o token retornado no header `Authorization: Bearer {token}`

## 📦 Estrutura do Projeto

```
AepResiduos/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ecoapp/waste_management/
│   │   │       ├── config/         # Configurações do Spring
│   │   │       ├── controller/     # Controladores REST
│   │   │       ├── dto/           # Objetos de transferência de dados
│   │   │       ├── model/         # Entidades JPA
│   │   │       ├── repository/    # Repositórios JPA
│   │   │       ├── service/       # Lógica de negócios
│   │   │       └── security/      # Configurações de segurança
│   │   └── resources/
│   │       └── application.properties
│   └── test/                      # Testes unitários e de integração
├── frontend/                      # Aplicação React
├── docker-compose.yml
├── Dockerfile
└── pom.xml
```

## 🛠️ Funcionalidades Principais

### Gestão de Usuários
- Cadastro de usuários
- Autenticação e autorização
- Perfis de acesso (ADMIN, USER)

### Gestão de Resíduos
- Cadastro de resíduos
- Categorização
- Rastreamento
- Histórico de movimentações

### Gestão de Coletas
- Agendamento
- Acompanhamento
- Confirmação
- Relatórios

### Gestão de Transportadoras
- Cadastro
- Avaliação
- Documentação
- Histórico

### Gestão de Destinatários
- Cadastro
- Licenças
- Capacidade
- Histórico

### Relatórios e Dashboard
- Indicadores de performance
- Relatórios personalizados
- Exportação de dados
- Gráficos e visualizações

## 🔄 Fluxo de Trabalho

1. **Cadastro de Resíduo**
   - Empresa gera resíduo
   - Sistema registra tipo, quantidade e características
   - Gera código de rastreamento

2. **Agendamento de Coleta**
   - Sistema notifica transportadora
   - Transportadora confirma
   - Gera documento de transporte

3. **Transporte**
   - Transportadora coleta
   - Sistema registra movimentação
   - Atualiza status

4. **Destinação**
   - Destinatário recebe
   - Confirma recebimento
   - Gera certificado

5. **Documentação**
   - Sistema gera documentos
   - Arquiva comprovantes
   - Mantém histórico

## 🧪 Testes

```bash
# Executar testes do backend
./mvnw test

# Executar testes do frontend
cd frontend
npm test
```

## 📈 Monitoramento

O sistema inclui:
- Logs detalhados
- Métricas de performance
- Alertas de sistema
- Monitoramento de segurança

## 🔒 Segurança

- Autenticação JWT
- Criptografia de senhas
- Proteção contra CSRF
- Validação de dados
- Sanitização de inputs
- Headers de segurança
- Rate limiting

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 📞 Suporte

Para suporte, envie um email para [seu-email@dominio.com] ou abra uma issue no projeto.

## 🙏 Agradecimentos

- AEP (Associação Empresarial de Portugal)
- Equipe de desenvolvimento
- Contribuidores
- Comunidade open source 