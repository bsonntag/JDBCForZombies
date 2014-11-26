JDBC For Zombies - V1
=====================

Introdução
----------

Nesta primeira fase vamos criar os `models` que vão ser utilizados pela aplicação e os `repositories` que vão tratar do acesso à base de dados.

Instruções
----------

1. **Zombified!**
    1. Cria a classe `Zombie` com os atributos `id`, `name` e `graveyard`.
    2. Cria a classe `ZombieRepository` que extende a classe abstrata `AbstractRepository` e, utilizando o teu IDE, gera os métodos que vais ter que implementar (`save`, `find`, `findAll`, `delete`, `deleteAll` e `count`).
    3. Começando pelo `findAll` implementa cada um dos métodos seguindo os seguintes passos:
        1. Cria uma `Connection` através do método `DriverManager.getConnection`.
        2. Cria um `Statement` (ou `PreparedStatement`) utilizando a `Connection`.
        3. Executa comandos na base de dados usando os métodos `executeQuery` ou `executeUpdate` do `Statement`.
        4. Utilizando o `ResultSet` retornado pelo método `executeQuery` constrói objetos da classe `Zombie`.

2. **Let'z tweet about it!**
    1. Cria a classe `Tweet` com os atributos `id`, `zombie`, `text` e `publishDate`.
    2. Cria a classe `TweetRepository` da mesma forma que criaste a classe `ZombieRepository`
    3. Implementa os métodos da classe `TweetRepository` tendo em atenção:
        - Conversão de `java.sql.Date` para `java.util.Date`, e vice-versa.
        - Construção de objectos da classe `Zombie` a partir do `zombie_id` obtido da base de dados.

3. **Where'z my tweetz!?**
    1. Adiciona o atributo `tweets` à classe `Zombie`.
    2. Adiciona o método `findByZombie` à classe `TweetRepository` e implementa-o.
