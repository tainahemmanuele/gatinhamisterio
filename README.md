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
- A ***camada de dados*** é representada pelo o banco de dados, nesse projeto é usado o ***H2*** .  A manipulação dos dados armazenados no banco é feita através da camada de apresentação, através de chamadas da API. É representada pelas classes que possuem no nome a palavra: *repository*. **Ex:** *BoxRepository*.

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

### Cadastrando um usuário ###
**OBS:** Para todas as operações envolvendo a API do projeto, é usado o programa *Postman*.

Para cadastrar um usuário no sistema, no campo **url**, coloque: localhost:8080/register,  escolha a opção **POST** e coloque no **Body** os dados referentes a: CPF, email, id (pode se qualquer valor, apenas para referencia no banco de dados) , name , password e role (ADMIN OU CLIENT). Depois selecione a opção **Send** , conforme o exemplo abaixo:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/register_1.png)

O usuário foi cadastrado com sucesso, se aparecer no Postman a seguinte tela:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/register_2.png)

## Persistência ##
O projeto **gatinha mistério** faz uso de persistência usando como banco de dados o H2 e usando ORM, já que se trata de um banco de dados relacional. ORM é uma técnica de mapeamento objeto relacional que permite fazer uma relação dos objetos com os dados que os mesmos representam. Para que essa técnica funcione é necessário fazer o uso de JPA com Hibernate. Usando JPA com Hibernate é possível mapear uma classe de modelo de entidade para um banco de dados sem precisar ter se preocupar como o código SQL será gerado - ao usar Hibernate, é possível gerar código SQL para qualquer banco de dados.

## Autenticação ##
O projeto **gatinha mistério** faz uso de autenticação para identificar se o usuário que tenta fazer login no sistema é quem ele diz ser. Para auxiliar na tarefa de autenticação, foi usado o padrão JSON Web Token (JWT). O pacote **jwt** possui todas as classes e métodos necessários para que a autenticação funcione. Usar JWT permite autenticar um usuário e garantir que as demais requisições serão feitas de forma autenticada, além de que é possível restringir acesso a recursos e serviços com os mais variados níveis e tipos de permissões.

Funcionamente, a autenticação por JWT ocorre da seguinte forma: o usuário solicita acesso a API do sistema, comumente através do uso da funcionalidade de login, e a API emite um token (aqui usando o método *generateToken* da classe ***JwtTokenUtil***) , que identifica aquele usuário no sistema e esse token é entregue ao usuário (nesse caso, é criado um **JWTUser** usando o método *create* da classe ***JwtUserFactory***. Esse objeto irá possuir as informações de autenticação do usuário: o email, a senha e o token para que ele tenha acesso ao sistema).

Por fim, toda vez que o usuário solicitar um recurso que necessita a identificação do usuário, como */login , o token deve ser apresentado para permitir o acesso. Para isso, foi criada a classe ***JwtAuthenticationFilter*** que a partir de uma requisição feita, usando o método  *doFilterInternal* , verifica a validação da autenticação dos usuários. Esse filtro também define a validação da autenticação dos usuários quando acessarem outras rotas, fora a rota de *login*. No especifico do projeto **gatinhamisterio**, a validação de autenticação ocorre apenas na funcionalidade de */login*.

Abaixo, o diagrama que demonstra o funcionamento da autenticação:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/auth_diagram.png)

### Operação de Login e Uso do Token ###
Após cadastrar o usuário, para logar no sistema e ter acesso, no *Postman*, no campo **url**, coloque: localhost:8080/login,  escolha a opção **GET** e coloque em **Headers** o email do usuário e o password, e selecione **Send** conforme o exemplo abaixo:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/login_1.png)

Depois, em **Headers** -> **Temporary Headers**, no campo **Authorization**, copie o valor que começa com *Bearer* e cole no campo **Token** em **Authorization**:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/login_2.png)

Depois disso, já é possível usar o sistema.

## Autenticação ##
O projeto **gatinha mistério** faz uso de autorização na rota **/user** com o objetivo de filtrar os dados a serem exibidos conformem o tipo de usuário (**admin** ou **client**).

Se um usuário do tipo **admin** acessar a rota **/user**, além das informações do seu usuário irá aparecer as informações referentes a todos os usuários do sistema, já que como administrador ele deve poder controlar todos os cadastros feitos.

Se um usuário do tipo **client** acessar a rota **/user**, apenas as informações referentes ao seu usuário serão mostradas.

As configurações de autorização estão localizadas na classe ***WebSecurityConfig*** . No método *configure*, é feita as operações necessárias para permitir o acesso as rotas e a quais tipos de operações deve ser dado o acesso. É verificada a autenticação do usuário antes da autorização ser feita (uma vez que o usuário, independente de ser **client** ou **admin** deve estar logado no sistema para ter acesso a rota) e que tipo de usuário ele é.


## Desempenho ##
Para melhorar o desempenho da aplicação foi adotado o uso de cache. Aqui é usado o cache padrão de Spring Boot.
O cache está habilitado em todo o projeto **gatinha mistério**, porém foi implementado apenas na classe **ProductService**, com o objetivo de diminuir o tempo de requisição ao fazer **GET** em */product*. Qualquer alteração feita (GET, POST, UPDATE ,DELETE), referente aos produtos, irá atualizar o cache.

Conforme a especificação da disciplina de DACA, foi implementada uma espera de 0.5s na operação **GET** */product*. O método que faz isso chama-se *simulateSlowService* .

Sem o uso de cache ( e no primeiro acesso, antes que o cache seja criado) a operação de **GET** em *http://localhost:8080/product* dura em torno de 0.5s:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/product_no_use_cache.png)

Após o uso de cache o tempo cai para em torno de 27ms:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/img/product_use_cache.png)


### JMeter ###
Para testar o desempenho da aplicação e descobrir os limites de  throughput (quando houver no gráfico uma assintota horizontal) , tanto sem o uso de cache, como com o uso de cache, foi usado o JMeter.

O plano de testes levou em consideração:

- Acesso a url: http:localhost:8080/product
- Operação de GET nessa url
- Tanto para o não uso de cache, como para o uso de cache, foi considerado o atraso de 0.5s na operação de GET em /product

Todos os artefatos gerados, tal como o plano de testes usado, pode ser encontrado em:

[Testes - JMeter](https://github.com/tainahemmanuele/gatinhamisterio/tree/master/Testes%20-%20JMeter)

#### Testes de Desempenho - Sem Uso de Cache ####
Na **primeira execução**, foram usadas as configurações de ***Threads**: 5* e ***Tempo:**  60s*, que é o padrão do JMeter. Após a execução, o gráfico de resultados gerados foi:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Sem%20Cache/Configura%C3%A7%C3%B5es%20Padr%C3%A3o%20-%205%20Threads/Gr%C3%A1fico%20de%20Resultados_5_threads.png)

Aqui, percebe-se que começa a aparecer o que será uma assintota, mas ainda não chegou em nenhum limite. O valor da vazão é de: 178,42/minuto.

No gráfico de tempo de resposta, percebe-se que ao longo do tempo do teste sendo executado, o tempo da requisição, que começou alto e foi caindo ao decorrer do tempo, se torna constante:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Sem%20Cache/Configura%C3%A7%C3%B5es%20Padr%C3%A3o%20-%205%20Threads/Response%20Time%20Graph_5_threads.png)

No gráfico agregado, há a média entre o número de amostras e o tempo que ocorreram as requisições:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Sem%20Cache/Configura%C3%A7%C3%B5es%20Padr%C3%A3o%20-%205%20Threads/Gr%C3%A1fico%20Agregado_5_threads.png)

Na **segunda execução**, foram usadas as configurações de ***Threads:** 10* e ***Tempo:** 120s*. Após a execução, o gráfico de resultados gerados foi:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Sem%20Cache/10%20Threads/Gr%C3%A1fico%20de%20Resultados_10_threads.png)

Nesse gráfico já percebe-se uma assintota horizontal, ainda incompleta, o que significa que  com 10 threads já se chega próximo ao limite de throughput da aplicação. O valor da vazão é de:  372,72 minuto.

No gráfico de tempo de resposta, percebe-se que ao longo do tempo do teste sendo executado, o tempo da requisição, diminuiu em relação ao primeiro teste, mas se manteve constante durante toda a execução:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Sem%20Cache/10%20Threads/Response%20Time%20Graph_10_threads.png)

No gráfico agregado, a média de tempo de requisição continuou a mesma em relação a primeira execução do teste (506ms), mas a taxa de erro caiu para 0%, além da vazão ter aumentado:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Sem%20Cache/10%20Threads/Gr%C3%A1fico%20Agregado_10_threads.png)


Na **terceira execução**, foram usadas as configurações de ***Threads:** 20* e ***Tempo:** 180s*. Após a execução, o gráfico de resultados gerados foi:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Sem%20Cache/20%20Threads/Gr%C3%A1fico%20de%20Resultados_20_threads.png)

Nesse gráfico, a assintota horizontal aparece, logo, o limite de vazão da aplicação é atingido quando há 2263 amostras fazendo requisições ao mesmo tempo. O valor da vazão é de : 752,511/minuto .

No gráfico de tempo de resposta, não houve mudanças no tempo, em relação a execução anterior do teste, se manteve constante.

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Sem%20Cache/20%20Threads/Response%20Time%20Graph_20_threads.png)


No gráfico agregado,a única modificação expressiva veio em relação a vazão:
![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Sem%20Cache/20%20Threads/Gr%C3%A1fico%20Agregado_20_threads.png)


####  Testes de Desempenho - Com Uso de Cache ####
Na **primeira execução**, foram usadas as configurações de ***Threads**: 5* e ***Tempo:**  60s*, que é o padrão do JMeter. Após a execução, o gráfico de resultados gerados foi:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/Configura%C3%A7%C3%B5es%20Padr%C3%A3o%20-%205%20Threads/Gr%C3%A1fico%20de%20Resultados_5_threads_cache.png)

Percebe-se que por conta do uso de cache, ainda não se chega a um gráfico que mostre a vazão da aplicação. O gráfico é constante, e a vazão é de : 268,64/minuto.

No gráfico de tempo de resposta, percebe-se que começa alto o tempo da requisição, cai, mas volta a aumentar conforme aumenta o número de requisições ao longo do tempo.

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/Configurações%20Padrão%20-%205%20Threads/Response%20Time%20Graph_5_threads_cache.png)

No gráfico agregado, percebe-se que apesar do número de amostras ser alto, em torno de 268, havendo várias requisições a /product, a média de tempo de requisição foi em torno de 6ms, já que com o uso de cache a aplicação acaba não retornando uma página nova a cada requisição.

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/Configurações%20Padrão%20-%205%20Threads/Gráfico%20Agregado_5_threads_cache.png)

Na **segunda execução**, foram usadas as configurações de ***Threads:** 10* e ***Tempo:** 120s*. Após a execução, o gráfico de resultados gerados foi:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/10%20Threads/Gr%C3%A1fico%20de%20Resultados_10_threads_cache.png)

No gráfico, já percebe-se uma assintota horizontal,ainda pequena em relação ao mesmo teste executado sem o uso de cache. A vazão é de: 551,014/minuto

No gráfico de tempo de resposta percebe-se que da mesma forma que anteriormente, o tempo da requisição começa alto, cai e aumenta de novo, mas ainda continua baixo, em relação ao tempo das primeiras requisições executadas no teste.

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/10%20Threads/Response%20Time%20Graph_10_threads_cache.png)

No gráfico agregado, a média de tempo de reqisição das amostra cai para 4ms e a vazão começa a aumentar:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/10%20Threads/Gr%C3%A1fico%20Agregado_10_threads_cache.png)

Na **terceira execução**, foram usadas as configurações de ***Threads:** 20* e ***Tempo:** 180s*. Após a execução, o gráfico de resultados gerados foi:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/20%20Threads/Gr%C3%A1fico%20de%20Resultados_20_threads_cache.png)

No gráfico, já existe uma assintota horizontal, mas pequena em relação ao mesmo teste executado na aplicação sem o uso de cache. A vazão é de : 1.112,099/minuto

No gráfico de tempo de resposta, percebe-se que o tempo cai nas primeiras requisições, e fica aumentando e diminuindo conforme vai aumentando o número de requisições feitas ao longo do tempo. Como há o uso de cache, enquanto o cache não expira para as amostras o tempo tende a ser constante, quando ele expira, aumenta, já que precisa acessar o banco de dados novamente para obter o resultado da requisição.

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/20%20Threads/Response%20Time%20Graph_20_threads.png)

No gráfico agregado, o número de amostras usadas aumenta e o tempo médio das requisições ficam em torno de 3ms:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/20%20Threads/Gr%C3%A1fico%20Agregado_20_threads_cache.png)

Na **quarta execução**, foram usadas as configurações de ***Threads:** 40* e ***Tempo:** 240s*. Após a execução, o gráfico de resultados gerados foi:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/40%20Threads/Gr%C3%A1fico%20de%20Resultados_40_threads_cache.png)

A assintota horizontal no gráfico aumentou em relação a execução anterior do teste, mas ainda não foi expressiva. A vazão é de 2.238,103/minuto.

No gráfico de tempo de resposta, o comportamento foi igual a da execução anterior do teste, onde cada vez que aumenta a quantidade de requisições, o tempo de resposta aumenta , fica constante por conta do uso do cache, e quando ele expira volta a aumentar.

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/40%20Threads/Response%20Time%20Graph_40_threads_cache.png)

No gráfico agregado o tempo médio das requisições fica em torno de 3ms (como na execução anterior do teste):

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/40%20Threads/Gráfico%20Agregado_40_threads_cache.png)


Na **quinta execução**, foram usadas as configurações de ***Threads:** 80* e ***Tempo:** 300s*. Após a execução, o gráfico de resultados gerados foi:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/80%20Threads/Gr%C3%A1fico%20de%20Resultados_80_threads_cache.png)

No gráfico, a assintota horizontal apresenta-se de forma mais clara em relação as execuções anteriores do teste. O limite de vazão da aplicação é atingido quando há 22464 amostras fazendo requisições ao mesmo tempo. O valor da vazão é de : 4.493,174/minuto .

No gráfico de tempo de resposta, o comportamento foi igual a execução anterior do teste:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/80%20Threads/Response%20Time%20Graph_80_threads_cache.png)

No gráfico agregado , mesmo aumentando o número de amostras, o tempo médio das requisições fica em torno de 3ms:

![](https://github.com/tainahemmanuele/gatinhamisterio/blob/master/Testes%20-%20JMeter/Com%20Cache/80%20Threads/Gr%C3%A1fico%20Agregado_80_threads_cache.png)











