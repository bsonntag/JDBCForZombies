JDBC For Zombies
================

Introdução
----------

JDBC (Java Database Connectivity) é uma tecnologia de conectividade de bases de dados.
Esta tecnologia é uma API para a linguagem de programação Java que permite aceder a bases de dados relacionais.
Fornece interfaces e classes com métodos para consultar e modificar os dados.

O que vais aprender durante o turorial?
---------------------------------------

Com este tutorial vais aprender a estabelecer ligações a bases de dados relacionais utilizando a linguagem de programação Java, assim como consultar e modificar os dados.
Vais também saber como organizar o código de acesso a dados em classes próprias, utilizando DAOs (Data Access Object).

Pré-requisitos
--------------

Neste tutorial vamos usar uma base de dados MySQL, pelo que deves ter instalado este motor de bases de dados.

Para criar a base de dados, abrir um terminal e executar:

> mysql -h localhost -u root -p

Introduzir a password do utilizador 'root' e, uma vez no cliente de MySQL introduzir os seguintes comandos:

> create user 'jdbcuser'@'%' identified by '123456';

> create database zombiedb character set utf8;

> grant all privileges on zombiedb.* to 'jdbcuser'@'%';

> flush privileges;

Para criar as tabelas:

> mysql -h localhost -u jdbcuser -p

E introduzir os comandos:

> create table zombies (id integer not null auto_increment primary key, name varchar(20), graveyard varchar(50));

> create table tweets (id integer not null auto_increment primary key, zombie_id integer not null, text varchar(200), pub_date date, foreign key (zombie_id) references zombies(id));

Como começar?
-------------

Podes começar [aqui](https://github.com/bsonntag/JDBCForZombies/tree/master/JDBCForZombies_v1).

Sobre
-----

Este tutorial foi criado para o workshop de JDBC do [Cesium](http://www.cesium.di.uminho.pt/), e foi inspirado em [Rails For Zombies](http://railsforzombies.org/).

![Figura 1 - Cartaz do Workshop](http://www.cesium.di.uminho.pt/system/images/170/original/swingjdbc-c%C3%B3pia%202.jpg?1416857491)
