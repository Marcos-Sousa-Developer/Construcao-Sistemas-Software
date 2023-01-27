<p align="center">
    <img src="https://www.ichess.net/wp-content/uploads/2021/07/The-Competition-Series-Plastic-Chess-Pieces-Black.png" alt="Logo" width="80" height="80">
</p>

# <h1 align="center">Gestor de bloqueios a recursos para leituras e escritas</h1>
<h4 align="center">Projeto para a cadeira de Aplicações Distribuídas (Parte1) (2021/2022)</h4>

<hr>

# Objetivo
O objetivo geral do projeto será concretizar um gestor de bloqueios a recursos para leituras e escritas (Read-Write Locks). O seu propósito é controlar o acesso a um conjunto de recursos partilhados num sistema distribuído, onde diferentes clientes podem requerer o acesso aos mesmos de forma concorrente. <br>
Um recurso pode ser bloqueado para a escrita exclusivamente por um só cliente de cada vez, até um máximo de K bloqueios de escrita ao longo do tempo.
Ou seja, findo os K bloqueios de escrita permitidos, o recurso fica desabilitado (i.e., indisponível para novos bloqueios). Um recurso pode ser bloqueado para a leitura por um número qualquer de clientes, caso o recurso não esteja bloqueado para a escrita ou desabilitado. <br>
Basicamente, o gestor previne conflitos entre escritas de clientes diferentes, assim como previne conflitos entre operações de leitura e escrita enquanto o recurso está ativo. O gestor será concretizado num servidor escrito na linguagem Python 3. A Figura 1 ilustra a arquitetura a seguir no servidor de bloqueios (lock_server), bem como a estrutura de dados em Python que o suporta.

<hr>

# Instruções   

## Primeiro Passo 

#### **Run it on terminal** 
```bash
python3 lock_client.py
```
## Comando LOCK

Bloqueia um determinado recurso para leitura (R) ou escrita (W) durante um tempo de concessão específico (em segundos) para o cliente que está a enviar o pedido. Se o pedido for para um bloqueio de escrita (W), o recurso pode ser bloqueado para a escrita apenas
se ele estiver no estado UNLOCKED e o limite de bloqueios de escrita K do recurso não tiver sido atingido. Neste caso, o servidor deve passar o recurso para o estado LOCKED-W, incrementar o contador de bloqueios de escrita, calcular o limite de tempo de concessão (i.e., deadline = tempo atual + tempo de concessão), registar o tuplo (id do cliente, deadline) na lista de bloqueios de escrita e retornar OK. Caso o recurso esteja LOCKED-W, LOCKED-R
ou DISABLED, o servidor deve retornar NOK ao pedido de bloqueio para a escrita. <br>
Se o pedido for para um bloqueio de leitura (R), o recurso pode ser bloqueado para a leitura apenas se ele estiver no estado LOCKED-R ou UNLOCKED. <br> Neste caso, o servidor deve calcular o limite de tempo de concessão (i.e., novamente, deadline = tempo atual + tempo concessão),
registar o tuplo (id do cliente, deadline) na lista de bloqueios de leitura, passar o estado do recurso para LOCKED-R (caso ainda não esteja) e retornar OK. Caso o recurso esteja no estado LOCKED-W ou DISABLED, o servidor deve retornar NOK. <br>
Em ambos casos (i.e., bloqueios de leitura e escrita), se o pedido se referir a um recurso inexistente (i.e., um número de recurso menor que 1 ou maior que N), o servidor deverá retornar UNKNOWN RESOURCE.


#### **Run it on terminal** 
```bash
LOCK <R|W> <número do recurso> <limite de tempo>
```

## Comando UNLOCK

Remove um bloqueio de leitura (R) ou escrita (W) registado num determinado recurso para o cliente que está a enviar o pedido. Se o pedido for um desbloqueio de escrita, o recurso solicitado estiver no estado LOCKED-W e o cliente que está a bloquear a escrita neste recurso for o mesmo que o que está a pedir o desbloqueio, então o servidor deve desbloquear a escrita no recurso (através da remoção do respetivo tuplo da lista de bloqueios de escrita e a passagem do recurso para o estado UNLOCKED) e retornar OK. Caso o recurso esteja no estado UNLOCKED ou DISABLED ou ainda se ele estiver bloqueado para a escrita (LOCKED-W) por outro cliente, o servidor deve retornar NOK. <br>
Se o pedido for um desbloqueio de leitura, o recurso solicitado estiver no estado LOCKED-R e o cliente que está a pedir o desbloqueio pertencer à lista de bloqueios de leitura, então o servidor deve remover o respetivo tuplo da lista de bloqueios de leitura, verificar se não há outros clientes com bloqueios de leitura (neste caso, passar o recurso ao estado UNLOCKED) e retornar OK. Caso o recurso esteja no estado LOCKED-W ou UNLOCKED, ou ainda o cliente que está a pedir o desbloqueio de leitura não pertencer à lista de clientes com bloqueios de leitura ativos, o servidor deve retornar NOK. <br>
Por fim, se o pedido de desbloqueio (seja de leitura ou de escrita) referir-se a um recurso inexistente (e.g., um número de recurso menor que 1 ou maior que N), o servidor deverá retornar UNKNOWN RESOURCE. <br>
Note que segundo a Tabela 1, tanto no LOCK quanto no UNLOCK a diferença entre o comando apresentado ao cliente e a mensagem enviada ao servidor é o <id do cliente>, o qual cadaprograma cliente é responsável por inserir nos seus pedidos. Todos os outros comandos possuem o mesmo formato no comando e no pedido.  
    
#### **Run it on terminal** 
```bash
UNLOCK <R|W> <número do recurso>
```
    
## Comando STATUS

É utilizado para obter o estado atual de um determinado recurso. O servidor retorna o estado do recurso solicitado (i.e., UNLOCKED, LOCKED-W, LOCKED-R ou DISABLED). Se o pedido se referir a um recurso inexistente (e.g., um número de recurso menor que 1 ou maior que N), o servidor deverá retornar UNKNOWN RESOURCE. 

#### **Run it on terminal** 
```bash
STATUS <número do recurso>
```
    
## Comando STATS  
    
É utilizado para obter outras informações sobre o servidor de bloqueios e possui 3 formas. Na primeira (STATS K <número do recurso>), o servidor retorna o número de bloqueios de escrita realizados no recurso especificado. Na segunda (STATS N), o servidor retorna o número total de recursos que se encontram disponíveis atualmente (i.e., no estado UNLOCKED). Na terceira (STATS D), o servidor retorna o número total de recursos que se encontram desabilitados atualmente (i.e., no estado DISABLED).

#### **Run it on terminal** 
```bash
STATS K <número do recurso> || STATS N || STATS D
```

## Comando PRINT
É utilizado para obter uma visão geral do estado do serviço de gestão de bloqueios

#### **Run it on terminal** 
```bash
PRINT
```   
## Comando SLEEP 
    
Faz com que o cliente adormeça durante um determinado tempo (em segundos). Ou seja, o cliente esperará este tempo antes de interpretar o próximo comando. 

#### **Run it on terminal** 
```bash
SLEEP <limite de tempo>
```   
    
## Comando EXIT 
    
Encerra a execução do cliente. Pode-se assumir que todas as execuções terão um comando EXIT no final.
 
#### **Run it on terminal** 
```bash
EXIT
```
