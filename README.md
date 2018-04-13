# XY-INC

> API Rest para manipular e visualizar dados.

* Permitir que o usuário Insira um Point Of Interst;
* O Usuário consegue listar todos os Point Of Interest;
* O usuário lista os pontos em uma distância mínima fornecida por ele;

## Installation

1. Primeiro instale o Maven Docker e Java 8:

#### Linux

```sh
  $ sudo apt-get install maven
  $ sudo apt-get install docker-ce
```

2. Entre no diretório de clone e use o comando:

```sh
    $ docker run -d \
		  -p 2012:3306 \
		 --name mysql-docker \
		 -e MYSQL_ROOT_PASSWORD=root \
		 -e MYSQL_DATABASE=poi_db \
		 -e MYSQL_USER=poi_usr \
		 -e MYSQL_PASSWORD=poi_pw \
			mysql:latest
```
Isso criará um cointainer MySql.
 3. Logo em seguida rode o comando para buildar:
 ```sh
	$ mvn clean install -DskipTests
 ```
 4. Utilize o comando a seguir para criar uma imagem Docker da solução:
 ```sh
 	$ docker build -f src/main/docker/Dockerfile -t xy-inc .
 ```
 5. Por ultimo linke os containers:
 ```sh
 	$ docker run -t --name xy-inc --link mysql-docker:mysql -p 8087:8080 xy-inc
```

## Testes Usando o cliente HTTP

* O serviço de listagem de todos os pontos é feito através da url
```
http://localhost:8087/api/pois
```
* Para cadastrar um Point Of Interest basta chamar a url:
```
http://localhost:8087/api/pois
```
E passar o objeto com as coordenadas que deseja inserir, por exemplo:
```
{
		"x": 23,
		"y": 6,
		"name": "Supermercado"
}
```
* Você também consegue listar os estabelecimentos mais próximos de um dado ponto, basta enviar a coordenada X e Y do ponto e a distância máxima a qual vc deseja buscar usando a url:
```
	http://localhost:8087/api/pois/{x}/{y}/proximmity/{max}
	http://localhost:8087/api/pois/20/10/proximmity/10
```

## Testes Unitários

* Para executar os testes é necessário que descomente a linha 4 e comente a linha 1 do arquivo  ```/src/main/resources/application.properties``` e utilize a IDE de preferência para realizar a cobertura dos mesmos.
* Após a mudança execute mvn verify.

