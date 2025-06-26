# Projetos de Desenvolvimento

Aqui estão três propostas de projetos, cada um com diferentes níveis de complexidade e requisitos, projetados para aprimorar suas habilidades de desenvolvimento.

---

## Projeto 1: Sistema de Gestão de Consultas (Nível Médio)

**Público-alvo:** Clínicas ou consultórios.

**Objetivo:** Desenvolver uma aplicação para agendar, listar e cancelar consultas.

### Funcionalidades Obrigatórias:

* **Autenticação e Autorização:**
    * Registro e login de usuários com **Spring Security**.
    * Dois perfis de usuário: **PACIENTE** e **MÉDICO**.
* **Perfil PACIENTE:**
    * Marca e cancela suas próprias consultas.
    * Visualiza apenas suas próprias consultas.
* **Perfil MÉDICO:**
    * Visualiza todas as consultas.
    * Pode alterar o estado da consulta (pendente, concluída, cancelada).
* **Validação de Dados:** Validação para data, hora e descrição da consulta.
* **Página de Erro Personalizada:** Tratamento de erros com uma página específica.
* **Menu Dinâmico:** Menu de navegação que se adapta ao perfil do usuário, utilizando **Thymeleaf**.

### Bônus:

* Filtro de consultas por data.
* Dashboard exibindo o número de consultas por estado.

---

## Projeto 2: Biblioteca Digital (Nível Avançado)

**Objetivo:** Gerenciar empréstimos de livros entre usuários.

### Funcionalidades:

* **Autenticação e Roles:**
    * Login e registro de usuários com os perfis: **USER** e **ADMIN**.
* **Perfil USER:**
    * Pode visualizar livros disponíveis.
    * Pode requisitar empréstimos de livros.
* **Perfil ADMIN:**
    * Pode adicionar, editar e remover livros.
* **Gestão de Livros:** Cada livro possui título, autor, categoria e estoque.
* **Sistema de Empréstimos:**
    * Validação para garantir que só é possível requisitar se houver estoque.
    * Cada usuário só pode visualizar seus próprios empréstimos.
* **Segurança:** Páginas protegidas com **hasRole()** e **@PreAuthorize**.
* **Log de Ações (Opcional):** Registro de ações importantes realizadas na aplicação.

### Bônus:

* Paginação e pesquisa de livros.
* Notificação de devolução próxima (via mensagem simples).

---

## Projeto 3: Projeto Livre (Com Orientações Obrigatórias)

O/Os aluno/os pode/m escolher o tema do projeto, mas deve/m seguir as regras mínimas abaixo.

### Regras Mínimas do Projeto:

* **Autenticação com Spring Security:**
    * Funcionalidades de login e logout com gerenciamento de sessão.
    * Mínimo de 2 perfis de usuário (ex: **USER** e **ADMIN**).
* **Validação de Dados:**
    * Uso de **@Valid**, **BindingResult** e exibição de mensagens de erro no **Thymeleaf**.
* **Estrutura MVC:**
    * Pelo menos 2 entidades com relacionamento (ex: `User` -> `Posts`).
    * Utilização de templates com **Thymeleaf**.
* **Autorização:**
    * Pelo menos uma página protegida por **role**.
    * Uso de **@PreAuthorize** ou `.hasRole()` na configuração de segurança.
* **Página de Erro Personalizada:** Para erros como 404 (página não encontrada) ou acesso negado.
* **Design Coerente:**
    * Menus visíveis apenas conforme o perfil do usuário.
    * Uso mínimo de **CSS** ou **Bootstrap** para um design funcional.

### Exemplos de Temas Livres:

* Sistema de receitas culinárias.
* Gestor de eventos pessoais.
* Aplicação de registro de treinos desportivos.
* Agenda pessoal com tarefas.

---

## Entrega Esperada:

* **Código-fonte:** Em uma pasta `.zip` ou em um repositório Git.
* **Capturas de Ecrã:** Imagens da aplicação em funcionamento.
* **Documento PDF Simples com:**
    * Descrição do projeto.
    * Tecnologias utilizadas.
    * Instruções de como executar a aplicação.
    * Divisão de responsabilidades (caso seja um trabalho em grupo).