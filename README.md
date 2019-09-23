# **Gatinha Mistério**
Repositório do projeto da disciplina DACA - UFCG 2019.2

Este projeto visa construir um back-end para um sistema de assinaturas de caixas de produtos de beleza. Mensalmente um cliente poderá fazer uma assinatura para uma caixa de produtos de beleza. 

## Especificação ##
[Documento de especificação do projeto](https://docs.google.com/document/d/1tK9l0PnHLUxGNgyqilfB2hwkxZk5ZDlOFLVRIJjArJo/edit?usp=sharing)

## Executando o projeto ##
Use o terminal/cmd para a execução dos passos a seguir:

1. Clone o repositório:

    `git clone https://github.com/tainahemmanuele/gatinhamisterio.git`

2. Acesse a pasta ***gm*** dentro da pasta ***gatinhamisterio*** do repositório clonado:

    `cd gm`

3. Execute o comando:
    
    `mvn spring-boot:run`

4. Depois de executado o comando, a API do projeto deve estar acessível em qualquer navegador através do endereço:

    `http://localhost:8080/swagger-ui.html#/`

Nesse link aparece as opções desejadas (**GET**, **POST**, **PUT**,**DELETE**) para todos os modelos do projeto.

5. Para parar a execução do projeto, basta executar o comando:

    `ctrl + c`

## Testando o projeto ##
1. Abra o terminal/cmd , acesse a pasta do projeto ( ***gatinhamisterio***) e a pasta  ***gm***

2.  Execute o comando :

    `mvn spring-boot:run`

3. Repita o passo 1, sem fechar o primeiro terminal aberto e execute o comando:

    `mvn test`

Todos os testes existentes no projeto devem ser executados. No fim da execução dos testes, o terminal/cmd que está executando os testes pode ser encerrado normalmente.

Para parar a execução do projeto no outro, basta executar o comando:

 `ctrl + c`

E encerrar o terminal/cmd.

## Arquitetura do projeto ##
A arquitetura do projeto **gatinha mistério** segue os princípios de REST. Usa ***modelo em três camadas***:

- A ***camada de apresentação*** é onde o usuário pode realizar consultas ao banco de dados, criar/modificar dados ou até mesmo apagar, além de outros recursos da aplicação. É representada pelas classes que possuem no nome a palavra: *controller*. **Ex:** *BoxController*.
- A ***camada de negócio*** é  onde está toda as funcionalidades do projeto e a lógica de negócio. É a intermediária entre o repositório (camada de acesso a dados) e a classe do controlador (camada de apresentação). As classes da camada de negócio devem oferecer as classes da camada de apresentação tudo o que precisa para atender às solicitações.É representada pelas classes que possuem no nome a palavra: *service*. **Ex:** *BoxService*
- A ***camada de dados*** é representada pelo o banco de dados, nesse projeto é usado o ***postgres*** .  A manipulação dos dados armazenados no banco é feita através da camada de apresentação, através de chamadas da API. É representada pelas classes que possuem no nome a palavra: *repository*. **Ex:** *BoxRepository*.

O back-end do **gatinha mistério** é composto por 5 entidades principais:

1. User
2. Subscription
3. Order
4. Box
5. Product

Usuário é a entidade que representa o cliente do sistema. Um cliente pode fazer assinaturas mensais através de uma order. Order é o mecanismo onde é validada a compra de uma Subscription (assinatura) . Há dois tipos de subscription: ***Fada Sensata*** e ***Cristal Lapidado*** . Uma subscription (independente do tipo) possui uma e somente uma caixa e uma caixa possui um conjunto de produtos. Cada produto que compõe uma caixa possui um tipo, que pode ser adicionado ao sistema conforme for aumentando a diversidade de produtos cadastrados.

### Diagrama de Entidade-Relacionamento ###
![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/Relacionamento.png)

### Diagrama de classes do model ###
![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/Class%20Diagram5.png)

### Diagrama de classes do controller ###
![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/Class%20Diagram7.png)

## Persistência ##
O projeto **gatinha mistério** faz uso de persistência usando como banco de dados o postgres e usando ORM, já que se trata de um banco de dados relacional. ORM é uma técnica de mapeamento objeto relacional que permite fazer uma relação dos objetos com os dados que os mesmos representam. Para que essa técnica funcione é necessário fazer o uso de JPA com Hibernate. Usando JPA com Hibernate é possível mapear uma classe de modelo de entidade para um banco de dados sem precisar ter se preocupar como o código SQL será gerado - ao usar Hibernate, é possível gerar código SQL para qualquer banco de dados.

## Autenticação ##
O projeto **gatinha mistério** faz uso de autenticação para identificar se o usuário que tenta fazer login no sistema é quem ele diz ser. Para auxiliar na tarefa de autenticação, foi usado o padrão JSON Web Token (JWT). O pacote **jwt** possui todas as classes e métodos necessários para que a autenticação funcione. Usar JWT permite autenticar um usuário e garantir que as demais requisições serão feitas de forma autenticada, além de que é possível restringir acesso a recursos e serviços com os mais variados níveis e tipos de permissões.

Funcionamente, a autenticação por JWT ocorre da seguinte forma: o usuário solicita acesso a API do sistema, comumente através do uso da funcionalidade de login, e a API emite um token (aqui usando o método *generateToken* da classe ***JwtTokenUtil***) , que identifica aquele usuário no sistema e esse token é entregue ao usuário (nesse caso, é criado um **JWTUser** usando o método *create* da classe ***JwtUserFactory***. Esse objeto irá possuir as informações de autenticação do usuário: o email, a senha e o token para que ele tenha acesso ao sistema).

Por fim, toda vez que o usuário solicitar um recurso que necessita a identificação do usuário, como *login* , o token deve ser apresentado para permitir o acesso. Para isso, foi criada a classe ***JwtAuthenticationFilter*** que a partir de uma requisição feita, usando o método  *doFilterInternal* , verifica a validação da autenticação dos usuários. Esse filtro também define a validação da autenticação dos usuários quando acessarem outras rotas, fora a rota de *login*. No especifico do projeto **gatinhamisterio**, a validação de autenticação ocorre apenas na funcionalidade de *login*.

Abaixo, o diagrama que demonstra o funcionamento da autenticação:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/auth_diagram.png)

