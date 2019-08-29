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


- A ***camada de aplicação*** é onde o usuário pode realizar consultas ao banco de dados, criar/modificar dados ou até mesmo apagar, além de outros recursos da aplicação. É representada pelas classes do pacote *controller*.
- A ***camada de negócio*** é  onde está toda as funcionalidades do projeto e a lógica de negócio. É representada pelas classes do pacote *service*.
- A ***camada de dados*** é representada pelo o banco de dados (atualmente não há persistência em disco, apenas em memória, usando o H2) .  A manipulação dos dados armazenados no banco é feita através da camada de aplicação, através de chamadas da API. É representada pelas classes do pacote *repository*.

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


