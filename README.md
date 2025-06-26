# DOCUMENTAÇÃO - Stock Manager V.0.01 alpha

## O problema
Essa aplicação, além de ser uma requisição para a matéria de JAVA & SpringBoot tem como objetivo resolver um problema pessoal de gestão de estoque de alimentos e prazos de validade.
O principal objetivo é monitorar os alimentos que tenho em casa e suas validades, para que assim eu saiba o que tenho que consumir antes de estregar e o que tenho que comprar quando for ao mercado.

### Requisitos mínimos do Projeto:

* **Autenticação com Spring Security:**
    * Funcionalidades de login e logout com gerenciamento de sessão. ✅
    * Mínimo de 2 perfis de usuário (ex: **USER** e **ADMIN**). ✅
* **Validação de Dados:**
    * Uso de **@Valid**, **BindingResult** e exibição de mensagens de erro no **Thymeleaf**. ✅
* **Estrutura MVC:**
    * Pelo menos 2 entidades com relacionamento (ex: `User` -> `Posts`). ✅
    * Utilização de templates com **Thymeleaf**. ✅
* **Autorização:**
    * Pelo menos uma página protegida por **role**. ✅
    * Uso de **@PreAuthorize** ou `.hasRole()` na configuração de segurança. ✅
* **Página de Erro Personalizada:** Para erros como 404 (página não encontrada) ou acesso negado. ✅
* **Design Coerente:**
    * Menus visíveis apenas conforme o perfil do usuário. ✅
    * Uso mínimo de **CSS** ou **Bootstrap** para um design funcional. ✅


## Futuras melhorias
- Adicionar a um banco de dados REAL em um servidor - Desafio - Aprender como fazer.;
- Adicionar a possibilidade de entregar ao usuario uma lista de mercado automatica com base nos produtos que ele tem acabando/perto de vencer - Desafio - Aprender como fazer.;
- Mandar notificação ao usuário quando algo no estoque está próximo a data de validade  - Desafio - Aprender como fazer;
- Refatorar o código - Desafio - Ter tempo;
- Quando eu pensar em algo mais que eu precise, coloco aqui...



## Corpo do Projeto

<details>

``` bash
Corpo do projeto
├── README.md
└── stock-manager
    ├── HELP.md
    ├── mvnw
    ├── mvnw.cmd
    ├── notas.md
    ├── pom.xml
    ├── requisitos.md
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   └── com
    │   │   │       └── manager
    │   │   │           └── stock_manager
    │   │   │               ├── config
    │   │   │               │   ├── DataInitializer.java
    │   │   │               │   └── SecurityConfig.java
    │   │   │               ├── controller
    │   │   │               │   ├── AdminController.java
    │   │   │               │   ├── AuthController.java
    │   │   │               │   └── ProdutoController.java
    │   │   │               ├── model
    │   │   │               │   ├── Categoria.java
    │   │   │               │   ├── Produto.java
    │   │   │               │   ├── Role.java
    │   │   │               │   └── User.java
    │   │   │               ├── repository
    │   │   │               │   ├── CategoriaRepository.java
    │   │   │               │   ├── ProdutoRepository.java
    │   │   │               │   ├── RoleRepository.java
    │   │   │               │   └── UserRepository.java
    │   │   │               ├── service
    │   │   │               │   └── UserDetailsServiceImpl.java
    │   │   │               └── StockManagerApplication.java
    │   │   └── resources
    │   │       ├── application.properties
    │   │       ├── static
    │   │       └── templates
    │   │           ├── admin
    │   │           │   └── user-list.html
    │   │           ├── dashboard.html
    │   │           ├── login.html
    │   │           ├── produtos
    │   │           │   ├── access-denied.html
    │   │           │   ├── add-edit.html
    │   │           │   └── list.html
    │   │           └── register.html
    │   └── test
    │       └── java
    │           └── com
    │               └── manager
    │                   └── stock_manager
    │                       └── StockManagerApplicationTests.java
    └── target
        ├── classes
        │   ├── application.properties
        │   ├── com
        │   │   └── manager
        │   │       └── stock_manager
        │   │           ├── config
        │   │           │   ├── DataInitializer.class
        │   │           │   └── SecurityConfig.class
        │   │           ├── controller
        │   │           │   ├── AdminController.class
        │   │           │   ├── AuthController.class
        │   │           │   └── ProdutoController.class
        │   │           ├── model
        │   │           │   ├── Categoria.class
        │   │           │   ├── Produto.class
        │   │           │   ├── Role.class
        │   │           │   └── User.class
        │   │           ├── repository
        │   │           │   ├── CategoriaRepository.class
        │   │           │   ├── ProdutoRepository.class
        │   │           │   ├── RoleRepository.class
        │   │           │   └── UserRepository.class
        │   │           ├── service
        │   │           │   └── UserDetailsServiceImpl.class
        │   │           └── StockManagerApplication.class
        │   └── templates
        │       ├── admin
        │       │   └── user-list.html
        │       ├── dashboard.html
        │       ├── login.html
        │       ├── produtos
        │       │   ├── access-denied.html
        │       │   ├── add-edit.html
        │       │   └── list.html
        │       └── register.html
        ├── generated-sources
        │   └── annotations
        ├── generated-test-sources
        │   └── test-annotations
        └── test-classes
            └── com
                └── manager
                    └── stock_manager
                        └── StockManagerApplicationTests.class

44 directories, 55 files
```

</details>

### pasta ./config

<!-- ******* DataInitializer.java  *******  -->
<details>
<summary><strong>DataInitializer.java</strong></summary>

## Visão Geral

O ficheiro `DataInitializer.java` é uma classe de configuração do Spring Boot (`@Configuration`) responsável por popular a base de dados com um conjunto de dados iniciais assim que a aplicação é iniciada.

O seu principal objetivo é garantir que, em ambientes de desenvolvimento ou de demonstração, a aplicação já comece com dados utilizáveis, como:
- [x] Perfis de utilizador (Roles)
- [x] Contas de utilizador com palavras-passe codificadas
- [x] Categorias de produtos
- [x] Produtos de exemplo

Isto é alcançado através da implementação de um `Bean` do tipo `CommandLineRunner`, que o Spring Boot executa automaticamente após o contexto da aplicação ter sido completamente carregado.

---

## Como Funciona

A classe utiliza o mecanismo `CommandLineRunner` para executar a lógica de inicialização.

1.  **Injeção de Dependências**: A classe recebe os `Repositories` necessários (`UserRepository`, `RoleRepository`, etc.) e o `PasswordEncoder` para codificar as palavras-passe de forma segura.

2.  **Execução na Inicialização**: O método `initData` retorna uma instância de `CommandLineRunner`. O código dentro da expressão lambda `args -> { ... }` é executado na inicialização da aplicação.

3.  **Lógica Idempotente**: A criação de `Roles` e `Users` utiliza o padrão `findByName().orElseGet(...)`. Isto torna a operação **idempotente**, ou seja, o código verifica primeiro se o dado já existe na base de dados e só o cria se não existir. Isto previne a criação de dados duplicados a cada reinício da aplicação.

4.  **Inicialização Condicional de Produtos**: A criação de `Categorias` e `Produtos` está dentro de um bloco condicional `if (produtoRepository.count() == 0)`. Isto garante que os produtos de exemplo **só são criados uma única vez**, quando a tabela de produtos está vazia.

---

## Dados Inseridos

Abaixo está um resumo dos dados que este inicializador cria.

#### Perfis (Roles)
- `ROLE_ADMIN`
- `ROLE_USER`

#### Utilizadores (Users)

| Username | Password (antes da codificação) | Role(s) |
| :--- | :--- | :--- |
| `admin` | `adminpass` | `ROLE_ADMIN`, `ROLE_USER` |
| `ana.silva` | `anapass` | `ROLE_USER` |
| `bruno.costa`| `brunopass` | `ROLE_USER` |
| `carla.dias` | `carlapass` | `ROLE_USER` |

#### Categorias
- Eletrônicos
- Vestuário
- Material de Escritório

---

## Como Utilizar ou Desativar

**Utilização**: Não é necessária nenhuma ação. A classe é detetada automaticamente pelo Spring Boot e executada na inicialização.

**Desativação**: Se precisar de executar a aplicação contra uma base de dados de produção sem estes dados de exemplo, pode desativar este inicializador de duas formas:
1.  Comente ou remova a anotação `@Configuration` da classe.
2.  Utilize Perfis do Spring (`@Profile("dev")`) para que a classe seja ativada apenas no perfil de desenvolvimento.

</details>

<details>

<!-- ******* SECURITY CONFIG!  *******  -->
<summary><strong>SecurityConfig.java</strong></summary>

## Visão Geral

O ficheiro `SecurityConfig.java` é a classe central de configuração do **Spring Security** para a aplicação. A sua responsabilidade é definir todas as regras de segurança web, incluindo:

* Quais URLs são públicas e quais são protegidas.
* As permissões necessárias para aceder a URLs específicas (e.g., apenas para administradores).
* Como funciona o processo de login e logout.
* O mecanismo de codificação de palavras-passe.
* A configuração de segurança a nível de métodos.

---

## Principais Anotações

* `@Configuration`: Marca a classe como uma fonte de beans de configuração para o Spring.
* `@EnableWebSecurity`: Habilita o suporte de segurança web do Spring Security.
* `@EnableMethodSecurity`: Ativa a segurança a nível de método, permitindo o uso de anotações como `@PreAuthorize` e `@PostAuthorize` diretamente nos métodos de services ou controllers.

---

## Configuração do `SecurityFilterChain`

Este é o bean principal que define a cadeia de filtros de segurança para as requisições HTTP. A configuração é feita da seguinte forma:

#### 1. Desativação de CSRF e Headers
* **CSRF (`Cross-Site Request Forgery`)**: Está desativado (`csrf().disable()`) para permitir o funcionamento correto da consola H2. Em produção, esta configuração deve ser revista e ativada com as devidas configurações.
* **Headers**: A opção `frameOptions().sameOrigin()` é configurada para permitir que a consola H2, que é renderizada dentro de um `<iframe>`, seja exibida corretamente.

#### 2. Regras de Autorização de Requisições (`authorizeHttpRequests`)
As URLs da aplicação são protegidas com as seguintes regras, na ordem em que são avaliadas:

| Padrão da URL | Permissão Requerida | Descrição |
| :--- | :--- | :--- |
| `/h2-console/**` | Acesso Público (`permitAll`) | Permite o acesso à consola da base de dados H2. |
| `/css/**`, `/js/**`, `/images/**` | Acesso Público (`permitAll`) | Permite o carregamento de ficheiros estáticos (CSS, JS, Imagens). |
| `/`, `/register`, `/error` | Acesso Público (`permitAll`) | Permite o acesso à página inicial, de registo e de erro. |
| `/admin/**` | `ROLE_ADMIN` | Exige que o utilizador tenha o perfil de **ADMIN** para aceder a qualquer URL sob `/admin/`. |
| `/api/**` | `ROLE_ADMIN` | Exige que o utilizador tenha o perfil de **ADMIN** para aceder a qualquer endpoint da API. |
| **Qualquer outra URL** | Autenticação (`authenticated`) | Todas as outras páginas que não se enquadram nas regras acima exigem que o utilizador esteja autenticado. |

#### 3. Configuração de Login (`formLogin`)
* **Página de Login**: O formulário de login está disponível na URL `/login`.
* **Redirecionamento Pós-Login**: Após um login bem-sucedido, o utilizador é redirecionado para a página `/dashboard`.
* **Acesso**: A página de login é pública (`permitAll`).

#### 4. Configuração de Logout (`logout`)
* **URL de Logout**: O logout é acionado ao aceder à URL `/logout`.
* **Redirecionamento Pós-Logout**: Após o logout, o utilizador é redirecionado para `/login?logout`, que exibe uma mensagem de sucesso.

#### 5. Tratamento de Exceções (`exceptionHandling`)
* Se um utilizador autenticado tentar aceder a uma página para a qual não tem permissão (e.g., um `USER` a tentar aceder a `/admin`), ele será redirecionado para a página `/access-denied`.

---

## Outros Beans de Configuração

* **`PasswordEncoder`**:
    * Define um bean que utiliza o algoritmo `BCryptPasswordEncoder`.
    * Este é o mecanismo padrão e seguro utilizado pela aplicação para codificar as palavras-passe dos utilizadores antes de as guardar na base de dados e para as verificar durante o processo de login.

* **`AuthenticationManager`**:
    * Expõe o `AuthenticationManager` do Spring Security como um bean.
    * Este gestor é a peça central que processa os pedidos de autenticação (e.g., utilizador e palavra-passe) no sistema.

</details>

<!-- ********* CONTROLLER!!! ********** -->
### pasta ./controller

<details>
<summary><strong>AdminController.java</strong></summary>

## Visão Geral

O `AdminController` é um controlador Spring MVC (`@Controller`) responsável por gerir as rotas e funcionalidades da secção de administração da aplicação web.

As suas principais responsabilidades são:
* Receber requisições HTTP para as rotas que começam com `/admin`.
* Interagir com a camada de dados (repositórios) para buscar informações.
* Enviar dados para os templates da view (HTML) para serem renderizados.
* Retornar os nomes dos ficheiros de view que devem ser exibidos ao utilizador.

Todas as rotas neste controlador estão protegidas pela configuração de segurança (`SecurityConfig.java`), exigindo que o utilizador tenha o perfil `ROLE_ADMIN` para lhes aceder.

---

## Anotações Principais

* **`@Controller`**: Indica que esta classe é um controlador no padrão MVC. Ela lida com as requisições do utilizador e retorna o nome de uma `View` (página HTML) para ser renderizada, em vez de retornar dados JSON (o que seria feito por um `@RestController`).
* **`@RequestMapping("/admin")`**: Define um prefixo base para todas as rotas mapeadas dentro desta classe. Ou seja, qualquer `@GetMapping`, `@PostMapping`, etc., será relativo a `/admin`.

---

## Mapeamento de Rotas

### `GET /admin/users`

* **Método**: `showUserList(Model model)`
* **Descrição**: Esta rota é responsável por exibir uma página HTML com a lista de todos os utilizadores registados no sistema.

#### Funcionamento Detalhado:
1.  **Recebe a Requisição**: O método é acionado quando o servidor recebe uma requisição `GET` para a URL `/admin/users`.
2.  **Busca os Dados**: Utiliza o `UserRepository` (injetado no construtor) para chamar o método `findAll()`. Esta chamada executa uma consulta à base de dados para obter uma lista de todas as entidades `User`.
3.  **Adiciona Dados ao Modelo**: A lista de utilizadores (`userList`) é adicionada a um objeto `Model` sob o nome `"users"`. O `Model` serve como uma ponte para transportar dados do controlador para a view.
4.  **Retorna a View**: O método retorna a `String` `"admin/user-list"`. O Spring Boot (juntamente com o motor de templates, como o Thymeleaf) interpreta este valor como o caminho para o ficheiro de template a ser renderizado, que se encontra em: `src/main/resources/templates/admin/user-list.html`. Este ficheiro HTML irá então usar o atributo `"users"` para exibir a lista dinamicamente numa tabela ou noutro formato.

---

## Dependências

* **`UserRepository`**: Uma interface do Spring Data JPA que fornece métodos para interagir com a tabela de utilizadores na base de dados (e.g., `findAll()`, `findById()`, etc.). É injetada no controlador para permitir o acesso aos dados dos utilizadores.

</details>

<details>
<summary><strong>AuthController.java</strong></summary>

## Visão Geral

O `AuthController` é um controlador Spring MVC (`@Controller`) que gere os endpoints públicos e essenciais relacionados com a autenticação e o ciclo de vida da sessão do utilizador. As suas responsabilidades incluem:

* Exibir as páginas de login e registo.
* Processar a submissão de novos registos de utilizadores.
* Exibir o painel principal (dashboard) após o login.
* Apresentar uma página de erro para acessos não autorizados.

Este controlador é o ponto de entrada para os utilizadores interagirem com o sistema, seja para entrar ou para criar uma nova conta.

---

## Dependências

* **`UserRepository`**: Utilizado para verificar a existência de utilizadores e para guardar novos registos na base de dados.
* **`RoleRepository`**: Utilizado para encontrar ou criar o perfil (`Role`) padrão que será atribuído aos novos utilizadores.
* **`PasswordEncoder`**: Essencial para codificar as palavras-passe dos utilizadores de forma segura antes de serem guardadas, garantindo que nunca sejam armazenadas em texto simples.

---

## Mapeamento de Rotas

### `GET /login`

* **Método**: `login()`
* **Descrição**: Simplesmente retorna a `String` `"login"`, instruindo o Spring/Thymeleaf a renderizar a página de login localizada em `src/main/resources/templates/login.html`.

### `GET /register`

* **Método**: `showRegistrationForm(Model model)`
* **Descrição**: Exibe o formulário para um novo utilizador se registar.
* **Funcionamento**:
    1.  Cria um objeto `User` vazio.
    2.  Adiciona este objeto ao `Model` com o nome `"user"`. Isto é essencial para o Thymeleaf fazer o *binding* dos campos do formulário ao objeto `user` (`th:object="${user}"`).
    3.  Retorna a `String` `"register"` para renderizar o template `register.html`.

### `POST /register`

* **Método**: `registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model)`
* **Descrição**: Processa os dados submetidos a partir do formulário de registo. Este é o endpoint mais complexo do controlador.

#### Funcionamento Detalhado:
1.  **Validação de Dados**: Graças à anotação `@Valid`, o Spring valida os dados do formulário com base nas anotações de validação no modelo `User` (e.g., `@NotEmpty`, `@Size`). Se houver erros (como campos em branco), o `BindingResult` captura-os e o método retorna o utilizador para a página `"register"`, exibindo as mensagens de erro.
2.  **Verificação de Nome de Utilizador**: O método consulta a base de dados para verificar se o `username` escolhido já existe. Se existir, adiciona uma mensagem de erro específica ao `Model` e retorna o utilizador para a página `"register"`.
3.  **Codificação da Palavra-passe**: Se a validação e a verificação passarem, a palavra-passe do utilizador é codificada usando o `passwordEncoder.encode()`.
4.  **Atribuição de Perfil (Role)**: O sistema procura o perfil `"ROLE_USER"`. Se não o encontrar na base de dados, cria-o e guarda-o. Em seguida, atribui este perfil ao novo utilizador.
5.  **Gravação do Utilizador**: O objeto `User`, agora completo e seguro, é guardado na base de dados com `userRepository.save()`.
6.  **Redirecionamento**: Após o sucesso, o método retorna `"redirect:/login?registered"`. Isto instrui o navegador a fazer uma nova requisição para a página de login. O parâmetro `?registered` pode ser usado no template de login para mostrar uma mensagem de "Registo efetuado com sucesso!".

### `GET /dashboard`

* **Método**: `dashboard()`
* **Descrição**: Retorna a `String` `"dashboard"`, que renderiza a página principal do painel do utilizador (`dashboard.html`), a qual é tipicamente acedida após um login bem-sucedido.

### `GET /access-denied`

* **Método**: `accessDenied()`
* **Descrição**: Retorna a `String` `"error/access-denied"`, renderizando uma página de erro personalizada (`access-denied.html`) quando um utilizador tenta aceder a um recurso para o qual não tem autorização. Este endpoint é configurado no `SecurityConfig` como a página padrão para erros de acesso negado (HTTP 403).

</details>

<details>
<summary><strong>ProdutoController.java</strong></summary>

## Visão Geral

O `ProdutoController` é o controlador Spring MVC responsável por todas as operações de **CRUD (Criar, Ler, Atualizar, Excluir)** para a entidade `Produto`. Ele gere a interação do utilizador com os produtos, desde a listagem até à sua criação, edição e remoção.

Uma característica central deste controlador é a sua robusta lógica de **permissões e posse**, que garante que:
1.  **Utilizadores Administradores (`ROLE_ADMIN`)**: Têm uma visão completa e podem gerir todos os produtos no sistema.
2.  **Utilizadores Comuns (`ROLE_USER`)**: Podem ver e gerir **apenas os produtos que eles próprios criaram**.

A segurança é reforçada com a anotação `@PreAuthorize("isAuthenticated()")` em várias rotas, garantindo que apenas utilizadores autenticados possam aceder às funcionalidades de gestão de produtos.

---

## Mapeamento de Rotas

### `GET /produtos/list`
* **Método**: `listProdutos(Model model, Authentication authentication)`
* **Descrição**: Exibe a lista de produtos. O conteúdo da lista depende do perfil do utilizador autenticado.

#### Funcionamento Detalhado:
1.  O sistema obtém o utilizador atualmente autenticado.
2.  Verifica se o utilizador possui o perfil `ROLE_ADMIN`.
    * **Se for Admin**: A lista conterá **todos os produtos** existentes na base de dados.
    * **Se for um Utilizador comum**: A lista conterá **apenas os produtos criados por esse utilizador**.
3.  A lista de produtos é adicionada ao `Model` e a página `produtos/list.html` é renderizada.

### `GET /produtos/new`
* **Método**: `showAddForm(Model model)`
* **Segurança**: Requer que o utilizador esteja autenticado (`@PreAuthorize`).
* **Descrição**: Exibe o formulário para adicionar um novo produto.
* **Funcionamento**:
    1.  Adiciona um novo objeto `Produto` vazio ao `Model` para o *binding* do formulário.
    2.  Adiciona a lista de todas as `Categorias` existentes ao `Model` para preencher uma lista de seleção no formulário.
    3.  Renderiza o template `produtos/add-edit.html`.

### `POST /produtos/save`
* **Método**: `saveProduto(...)`
* **Segurança**: Requer que o utilizador esteja autenticado (`@PreAuthorize`).
* **Descrição**: Processa a submissão do formulário para criar um novo produto ou atualizar um existente.

#### Funcionamento Detalhado:
1.  **Verificação de Posse (para Edição)**:
    * Se o `produto` submetido já tem um ID, o sistema considera que é uma **operação de edição**.
    * Antes de prosseguir, ele verifica se o utilizador autenticado é o dono do produto original ou se é um `ADMIN`.
    * Caso contrário, uma exceção de acesso negado (`HttpStatus.FORBIDDEN`) é lançada, impedindo a edição não autorizada.
2.  **Gestão de Categoria**: Permite que o utilizador selecione uma categoria existente ou crie uma nova dinamicamente. Se nenhuma categoria for fornecida, um erro de validação é adicionado.
3.  **Validação de Dados**: Valida os dados do produto com base nas anotações do modelo (`@Valid`). Se houver erros, o formulário é exibido novamente com as mensagens de erro.
4.  **Associação de Utilizador**: O produto é associado ao utilizador que está autenticado no momento da gravação.
5.  **Gravação**: O produto é guardado (ou atualizado) na base de dados.
6.  **Redirecionamento**: O utilizador é redirecionado para a página de listagem (`/produtos/list`).

### `GET /produtos/edit/{id}`
* **Método**: `showEditForm(@PathVariable("id") Long id, ...)`
* **Segurança**: Requer que o utilizador esteja autenticado (`@PreAuthorize`).
* **Descrição**: Exibe o formulário de edição para um produto específico, após verificar as permissões.

#### Funcionamento Detalhado:
1.  Busca o produto pelo `id` fornecido na URL.
2.  **Verificação de Posse**: Confirma se o utilizador autenticado é o dono do produto ou um `ADMIN`. Se não for, lança uma exceção de acesso negado (`HttpStatus.FORBIDDEN`).
3.  Se a verificação for bem-sucedida, o produto encontrado e a lista de categorias são adicionados ao `Model`.
4.  Renderiza o mesmo template usado para adicionar (`produtos/add-edit.html`), mas preenchido com os dados do produto a ser editado.

### `GET /produtos/delete/{id}`
* **Método**: `deleteProduto(@PathVariable("id") Long id, ...)`
* **Segurança**: Requer que o utilizador esteja autenticado (`@PreAuthorize`).
* **Descrição**: Exclui um produto específico, após verificar as permissões.

#### Funcionamento Detalhado:
1.  Busca o produto pelo `id`.
2.  **Verificação de Posse**: Realiza a mesma verificação de posse do endpoint de edição. Se o utilizador não for o dono nem um `ADMIN`, lança uma exceção de acesso negado.
3.  Se autorizado, o produto é excluído da base de dados.
4.  O utilizador é redirecionado para a página de listagem (`/produtos/list`).

</details>

### Pasta ./model

<details>
<summary><strong>Categoria.java</strong></summary>

## Visão Geral

A classe `Categoria.java` é uma **entidade JPA** (`@Entity`) que representa uma categoria de produtos no sistema. O seu principal objetivo é agrupar produtos relacionados, como por exemplo "Eletrónicos", "Vestuário" ou "Material de Escritório".

Cada instância desta classe corresponde a uma linha na tabela `categoria` da base de dados. A classe utiliza extensivamente anotações do **Lombok** para reduzir código repetitivo (boilerplate), como getters, setters e construtores.

---

## Anotações Principais

### Anotações JPA/Jakarta Persistence
* **`@Entity`**: Marca a classe como uma entidade que será mapeada para uma tabela na base de dados.
* **`@Id`**: Especifica que o atributo `id` é a chave primária da tabela.
* **`@GeneratedValue(strategy = GenerationType.IDENTITY)`**: Configura a estratégia de geração da chave primária. `IDENTITY` significa que a responsabilidade de gerar o valor do ID é delegada à base de dados (geralmente através de uma coluna auto-incrementável).

### Anotações Lombok
* **`@Data`**: Uma anotação "tudo em um" que gera automaticamente os métodos `getters`, `setters`, `toString()`, `equals()` e `hashCode()` para todos os atributos da classe.
* **`@NoArgsConstructor`**: Cria um construtor vazio (sem argumentos), que é frequentemente exigido pelo JPA para a criação de instâncias de entidades.
* **`@AllArgsConstructor`**: Cria um construtor que aceita todos os atributos da classe como argumentos, útil para criar instâncias da entidade de forma concisa.

---

## Atributos

| Nome do Atributo | Tipo | Descrição |
| :--- | :--- | :--- |
| `id` | `Long` | O identificador único da categoria. É a chave primária e o seu valor é gerado automaticamente pela base de dados. |
| `nome` | `String` | O nome da categoria (ex: "Eletrónicos"). |

---

## Relacionamentos

* **`Produto` (One-to-Many)**: Uma `Categoria` pode estar associada a vários `Produto`s. Este relacionamento é definido na entidade `Produto` através de uma anotação `@ManyToOne`, indicando que muitos produtos podem pertencer a uma categoria.

</details>

<details>
<summary><strong>Produto.java</strong></summary>

## Visão Geral

A classe `Produto.java` é a entidade JPA central do sistema, representando um produto no inventário. Ela é responsável por mapear os dados de um produto para a tabela `produto` na base de dados.

Esta entidade é rica em validações de dados, utilizando anotações da especificação **Jakarta Bean Validation** para garantir a integridade e a consistência dos dados antes de serem persistidos. Além disso, estabelece os relacionamentos fundamentais com as entidades `Categoria` e `User`.

---

## Anotações Principais

### Anotações JPA/Jakarta Persistence
* **`@Entity`**: Marca a classe como uma entidade que será mapeada para uma tabela.
* **`@Id`**, **`@GeneratedValue`**: Definem o atributo `id` como a chave primária auto-incrementável, gerida pela base de dados.
* **`@ManyToOne`**: Utilizada para definir relacionamentos "muitos-para-um". Um produto pertence a uma categoria e a um utilizador.
* **`@JoinColumn`**: Especifica a coluna de chave estrangeira (`categoria_id` e `user_id`) na tabela `produto` que estabelece a ligação com as outras tabelas.
* **`fetch = FetchType.LAZY`**: Usado no relacionamento com `User`, esta é uma otimização de performance que instrui o JPA a não carregar os dados do utilizador associado até que estes sejam explicitamente acedidos.

### Anotações Lombok
* **`@Data`**, **`@NoArgsConstructor`**, **`@AllArgsConstructor`**: Geram automaticamente o código repetitivo, como getters, setters, construtores e outros métodos essenciais.

### Anotações Jakarta Bean Validation
* Esta classe utiliza diversas anotações como `@NotBlank`, `@NotNull`, `@Min`, `@PastOrPresent` e `@Future` para impor regras de negócio e garantir a qualidade dos dados. As mensagens de erro personalizadas ajudam a fornecer feedback claro ao utilizador.

---

## Atributos

| Nome do Atributo | Tipo | Validação / Regras | Descrição |
| :--- | :--- | :--- | :--- |
| `id` | `Long` | - | Identificador único (chave primária) do produto. |
| `nome` | `String` | `@NotBlank` | O nome do produto. Não pode ser nulo nem vazio. |
| `descricao` | `String` | - | Uma descrição opcional e mais detalhada do produto. |
| `marca` | `String` | `@NotBlank` | A marca do produto. Não pode ser nula nem vazia. |
| `dataDeAbertura` | `LocalDate`| `@PastOrPresent` | A data em que o produto foi adicionado/aberto. Não pode ser uma data futura. |
| `dataDeValidade`| `LocalDate`| `@Future` | A data de validade do produto. Deve ser uma data futura. (Opcional) |
| `quantidade` | `int` | `@Min(0)` | A quantidade do produto em stock. Não pode ser um número negativo. |
| `preco` | `double` | `@Min(0)` | O preço de venda do produto. Não pode ser um número negativo. |
| `categoria` | `Categoria`| `@NotNull` | A categoria à qual o produto pertence. É um campo obrigatório. |
| `user` | `User` | - | O utilizador que registou o produto. É um campo obrigatório e a sua ligação é feita de forma "lazy" (preguiçosa). |

---

## Relacionamentos

* **`Categoria` (Many-to-One)**: Define que **muitos** `Produto`s podem pertencer a **uma** `Categoria`. A coluna `categoria_id` na tabela `produto` armazena a chave estrangeira para a `categoria`.

* **`User` (Many-to-One)**: Define que **muitos** `Produto`s podem ser registados por **um** `User`. A coluna `user_id` na tabela `produto` é uma chave estrangeira para o `user` e não pode ser nula, garantindo que cada produto tenha sempre um proprietário.

</details>


<details>
<summary><strong>Role.java</strong></summary>

## Visão Geral

A classe `Role.java` é uma entidade JPA (`@Entity`) que representa um perfil de utilizador dentro do sistema. A sua principal finalidade é ser utilizada pelo **Spring Security** para controlar o acesso e as permissões dos utilizadores.

Cada instância desta classe corresponde a um perfil específico, como por exemplo `ROLE_ADMIN` ou `ROLE_USER`, e é mapeada para uma linha na tabela `role` da base de dados.

---

## Anotações Principais

### Anotações JPA/Jakarta Persistence
* **`@Entity`**: Marca a classe como uma entidade que será mapeada para uma tabela na base de dados.
* **`@Id`**: Especifica que o atributo `id` é a chave primária da tabela.
* **`@GeneratedValue(strategy = GenerationType.IDENTITY)`**: Configura a geração da chave primária para ser delegada à base de dados (auto-incrementável).

### Anotações Lombok
* **`@Data`**: Gera automaticamente os métodos `getters`, `setters`, `toString()`, `equals()` e `hashCode()`.
* **`@NoArgsConstructor`**: Cria um construtor vazio (sem argumentos), necessário para o JPA.
* **`@AllArgsConstructor`**: Cria um construtor que aceita todos os atributos como argumentos.

---

## Atributos

| Nome do Atributo | Tipo | Descrição |
| :--- | :--- | :--- |
| `id` | `Long` | O identificador único (chave primária) do perfil. |
| `name` | `String` | O nome do perfil. Por convenção do Spring Security, os nomes devem começar com o prefixo `ROLE_` (ex: `ROLE_ADMIN`, `ROLE_USER`). |

---

## Relacionamentos

* **`User` (Many-to-Many)**: A entidade `Role` tem um relacionamento **muitos-para-muitos** com a entidade `User`. Isto significa que um `Role` pode ser atribuído a vários `User`s, e um `User` pode ter vários `Role`s. Este relacionamento é geralmente configurado na entidade `User` através da anotação `@ManyToMany`, que cria uma tabela de junção (ex: `user_roles`) para gerir as associações.

</details>

### Pasta ./repository

<details>
<summary><strong>CategoriaRepository.java</strong></summary>

## Visão Geral

`CategoriaRepository` é uma interface que atua como a ponte entre a lógica da aplicação e a tabela `categoria` na base de dados. Ela faz parte da camada de acesso a dados e é construída sobre o framework **Spring Data JPA**.

A principal característica desta interface é que, ao estender `JpaRepository`, ela herda um conjunto completo de funcionalidades de **CRUD (Create, Read, Update, Delete)** sem que seja necessário escrever uma única linha de código de implementação. O Spring gera a implementação necessária em tempo de execução.

---

## O Poder do `JpaRepository`

A declaração `extends JpaRepository<Categoria, Long>` instrui o Spring Data JPA a:
* Gerir a entidade `Categoria`.
* Reconhecer que a chave primária da entidade `Categoria` é do tipo `Long`.

Isto desbloqueia imediatamente os seguintes métodos (entre outros):

* **Para Gravar ou Atualizar**:
    * `save(Categoria entity)`: Persiste uma nova entidade ou atualiza uma já existente.

* **Para Ler Dados**:
    * `findById(Long id)`: Procura uma entidade pelo seu ID.
    * `findAll()`: Retorna todas as entidades da tabela.
    * `count()`: Conta o número total de registos.

* **Para Excluir Dados**:
    * `deleteById(Long id)`: Remove um registo pelo seu ID.
    * `delete(Categoria entity)`: Remove o registo correspondente à entidade fornecida.

---

## Extensibilidade com Métodos Personalizados

O corpo desta interface está vazio, o que indica que, de momento, apenas as operações padrão do `JpaRepository` são utilizadas.

No entanto, a grande vantagem do Spring Data JPA é a facilidade de adicionar consultas personalizadas. Bastaria declarar um novo método seguindo as convenções de nomenclatura do framework, e o Spring criaria a consulta SQL correspondente.

**Exemplo de como seria adicionada uma consulta personalizada:**
```java
// Dentro da interface CategoriaRepository
// Esta declaração criaria uma consulta para buscar todas as categorias cujos nomes começam com o texto fornecido.
List<Categoria> findByNomeStartingWith(String prefixo);
```

</details>

<details>
<summary><strong>ProdutoRepository.java</strong></summary>

## Visão Geral

`ProdutoRepository` é a interface de repositório do Spring Data JPA responsável por todas as interações com a base de dados relativas à entidade `Produto`. Ela serve como a camada de abstração de dados, permitindo que a aplicação realize operações de **CRUD (Create, Read, Update, Delete)** na tabela `produto` de forma simples e eficiente.

Além dos métodos CRUD padrão herdados de `JpaRepository`, esta interface define um método de consulta personalizado para satisfazer uma necessidade específica da aplicação: buscar produtos por utilizador.

---

## Herança do `JpaRepository`

Ao estender `JpaRepository<Produto, Long>`, a interface `ProdutoRepository` herda automaticamente um vasto conjunto de métodos para manipulação de dados, incluindo:

* **`save(Produto entity)`**: Salva ou atualiza um produto.
* **`findById(Long id)`**: Busca um produto pelo seu ID.
* **`findAll()`**: Retorna uma lista com todos os produtos.
* **`deleteById(Long id)`**: Exclui um produto pelo seu ID.
* **`count()`**: Retorna o número total de produtos.

---

## Métodos Personalizados

Esta interface define um método de consulta derivado, onde o Spring Data JPA cria a implementação automaticamente com base no nome do método.

### `List<Produto> findByUser(User user)`

* **Descrição**: Este método foi criado para encontrar e retornar uma lista de todos os `Produto`s que estão associados a uma entidade `User` específica.
* **Mecanismo**: O Spring Data JPA analisa o nome do método `findByUser`. Ele entende que deve criar uma consulta que seleciona todos os registos da entidade `Produto` onde o campo `user` (que é uma coluna de chave estrangeira) corresponde ao objeto `User` passado como parâmetro. Essencialmente, executa uma consulta semelhante a `SELECT * FROM produto WHERE user_id = ?`.
* **Utilização**: É um método crucial para a lógica de permissões da aplicação, permitindo, por exemplo, que um utilizador veja apenas os produtos que ele próprio registou.

---

## Como é Utilizado

O `ProdutoRepository` é injetado em componentes como o `ProdutoController`. Nele, os seus métodos são utilizados para:
* Obter a lista completa de produtos para um administrador (usando `findAll()`).
* Obter a lista de produtos pertencentes a um utilizador específico para um utilizador comum (usando o método personalizado `findByUser(currentUser)`).

</details>

<details>
<summary><strong>RoleRepository.java
</strong></summary>

## Visão Geral

`RoleRepository` é a interface do Spring Data JPA que define a camada de acesso a dados para a entidade `Role`. É um componente fundamental para a camada de segurança da aplicação, pois permite consultar, criar e gerir os perfis de utilizador (e.g., `ROLE_ADMIN`, `ROLE_USER`) que determinam as permissões no sistema.

Assim como outros repositórios do projeto, ela estende `JpaRepository` para obter as funcionalidades de CRUD padrão e adiciona um método de consulta personalizado para atender a uma necessidade específica da aplicação.

---

## Herança do `JpaRepository`

Ao estender `JpaRepository<Role, Long>`, a interface `RoleRepository` herda automaticamente métodos para manipulação de dados, tais como:

* **`save(Role entity)`**: Salva ou atualiza um perfil.
* **`findById(Long id)`**: Busca um perfil pelo seu ID.
* **`findAll()`**: Retorna uma lista com todos os perfis.
* **`deleteById(Long id)`**: Exclui um perfil pelo seu ID.

---

## Métodos Personalizados

Esta interface define um método de consulta derivado, onde o Spring Data JPA cria a implementação automaticamente com base no nome do método.

### `Optional<Role> findByName(String name)`

* **Descrição**: Este método permite buscar uma entidade `Role` específica através do seu campo `name`. É a forma principal de obter um perfil conhecido, como "ROLE_ADMIN".
* **Mecanismo**: O Spring Data JPA interpreta o nome do método `findByName` e gera uma consulta SQL correspondente (semelhante a `SELECT * FROM role WHERE name = ?`) em tempo de execução.
* **Tipo de Retorno `Optional<Role>`**: O uso de `Optional` é uma boa prática de programação. Em vez de retornar `null` se nenhum perfil com o nome especificado for encontrado, o método retorna um `Optional` vazio. Isto força o código que chama o método a lidar explicitamente com o caso em que o perfil pode não existir, prevenindo `NullPointerExceptions` e tornando o código mais robusto e seguro.

---

## Como é Utilizado

O `RoleRepository` é um componente essencial em várias partes da aplicação:

* **No `DataInitializer`**: É utilizado para verificar se os perfis `ROLE_ADMIN` e `ROLE_USER` já existem na base de dados antes de tentar criá-los, utilizando a lógica `.orElseGet()`.
* **No `AuthController`**: Durante o processo de registo de um novo utilizador, este repositório é usado para buscar o perfil padrão `ROLE_USER` e atribuí-lo ao novo utilizador.

</details>

### pasta ./service

<details>
<summary><strong>UserDetailsServiceImpl.java</strong></summary>

## Visão Geral

`UserDetailsServiceImpl` é uma classe de serviço (`@Service`) e um componente central da integração com o **Spring Security**. A sua única e principal responsabilidade é implementar a interface `UserDetailsService` do Spring Security.

Esta classe atua como uma ponte entre o modelo de dados de utilizador da sua aplicação (a entidade `User`) e o mecanismo de autenticação do Spring Security. Quando um utilizador tenta fazer login, o Spring Security invoca este serviço para carregar os detalhes desse utilizador a partir da base de dados.

---

## Implementação da Interface `UserDetailsService`

Para se integrar com o Spring Security, a classe implementa o método `loadUserByUsername`. Este método é o coração do serviço.

### Método `loadUserByUsername(String username)`

* **Descrição**: Este método é chamado pelo Spring Security durante o processo de autenticação. Ele recebe o `username` que o utilizador inseriu no formulário de login e deve retornar um objeto do tipo `UserDetails`, que contém as informações necessárias para que o Spring Security valide as credenciais.

* **Fluxo de Execução**:
    1.  Utiliza o `UserRepository` para procurar na base de dados um utilizador com o `username` fornecido.
    2.  **Caso de Falha**: Se nenhum utilizador for encontrado, o método lança uma exceção `UsernameNotFoundException`. Isto sinaliza ao Spring Security que a autenticação falhou porque o utilizador não existe.
    3.  **Caso de Sucesso**: Se o utilizador for encontrado, o método **não** retorna a entidade `User` da sua aplicação diretamente. Em vez disso, ele cria e retorna uma nova instância de `org.springframework.security.core.userdetails.User`, que é a implementação padrão de `UserDetails` do Spring Security.
    4.  Este objeto `UserDetails` é construído com três informações essenciais:
        * O nome de utilizador.
        * A **palavra-passe codificada (hashed)** que está na base de dados.
        * Uma coleção de permissões (`GrantedAuthority`), que são obtidas a partir dos `Role`s do utilizador.

### Método Auxiliar `mapRolesToAuthorities(Collection<Role> roles)`

* **Descrição**: Este é um método privado que atua como um "tradutor" ou "adaptador".
* **Função**: A sua única função é converter a coleção de entidades `Role` da sua aplicação numa coleção de `GrantedAuthority`, que é o formato que o Spring Security entende para representar permissões. Ele faz isso pegando o nome de cada `Role` (e.g., "ROLE_ADMIN") e envolvendo-o num objeto `SimpleGrantedAuthority`.

---

## Como se Encaixa na Aplicação

Este serviço é a cola que une o modelo de dados de utilizador da aplicação (`com.manager.stock_manager.model.User`) ao mecanismo de autenticação interno do Spring Security.

Quando um utilizador faz login, o Spring Security:
1.  Chama este serviço para obter os dados do utilizador.
2.  Pega na palavra-passe retornada por este serviço.
3.  Usa o `PasswordEncoder` para comparar a palavra-passe da base de dados com a que foi fornecida no login.
4.  Usa a lista de `GrantedAuthority` para tomar decisões de autorização (e.g., permitir ou negar o acesso a certas páginas).

</details>