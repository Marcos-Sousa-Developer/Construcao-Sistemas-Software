<p align="center">
    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGF-bBS1d--DM592E0LbOLxAuLU9AdUVgfvg&usqp=CAU" alt="Logo" width="200">
</p>

# <h1 align="center">ChessBook</h1>
<h4 align="center">Projeto para a cadeira de Construção de Sistemas de Software (2022/2023)</h4>

<hr>

# Introdução 
O xadrez é um jogo de tabuleiro disputado entre dois jogadores e que simula uma guerra entre dois reinos. <br>
É um dos jogos mais populares do mundo. Milhões de pessoas jogam tanto de uma forma amigável como competitiva. <br>  

# Objetivo
ChessBook é uma aplicação que permite a duas jogadores jogar xadrez, remotamente. <br>
Os jogadores combinam uma partida e aleatoriamente é decidido quem joga com as peças brancas com as peças pretas. <br>
Um jogador indica ao oponente a sua jogada mas este não vê imediatamente qual o movimento efetuado. Quando decide examinar a jogada recebida, abre a notificação e descobre a jogada. Começa então a contagem do seu tempo de jogo, até enviar uma jogada de volta.

<hr>

# Instruções   

#### Instalar o Tomcat

```bash
sudo apt-get install tomcat9
```

#### Configurar o servidor para poder usar o “manager”

```bash
sudo nano /etc/tomcat9/tomcat-users.xml
``` 

#### Incluir os seguintes detalhes

```
<role rolename="admin-gui" />
<role rolename="manager-gui" />
<user username="your_user_name" password="your_password" roles="admin-gui,manager-gui"/> 
```
#### Reiniciar o servidor

```bash
sudo systemctl restart tomcat9.service
```  
#### Compile source code and also package

```bash
mvn clean && mvn package
```  
#### Aceder à aplicação 

http://localhost:8080/manager/ 

#### Deployment

**1º:** Usando o manager, na secção “WAR file to deploy” <br>
**2º:** Clicar em browse <br>
**3º:** Selecionar o ficheiro ChessBookWEB-0.0.1-SNAPSHOT.war (dentro da pasta target)  <br>
**4º:** Aceder http://localhost:8080/ChessBookWEB-0.0.1-SNAPSHOT/








