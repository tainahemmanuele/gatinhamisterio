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

Nesse link aparece as opções desejadas (GET, POST, PUT,DELETE) para todos os modelos do projeto.

5. Para para a execução do projeto, basta executar o comando:

    `ctrl + c`

# Arquitetura do projeto #
O back-end do gatinha mistério é composto por 5 entidades principais:
1. User
2. Subscription
3. Order
4. Box
5. Product

Usuário é a entidade que representa o cliente do sistema. Um cliente pode fazer assinaturas mensais através de uma order. Uma subscription possui uma e somente uma caixa e uma caixa possui um conjunto de produtos.

### Diagrama de Entidade-Relacionamento ###
![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/Relacionamento.png)

### Diagrama de classes do model ###
![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/Class%20Diagram5.png)

### Diagrama de classes do controller ###
![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/Class%20Diagram7.png)


