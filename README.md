# JSF Todo List

Uma lista de afazeres(Todo List) feita utilizando JSF (Java Server Faces), Hibernate, PostgreSQL e Bootstrap4.
Este projeto implementa uma lista de afazeres com o objetivo de criar um CRUD com o JSF.

Nesta lista, cada item da lista é identificado no Banco de Dados através de seu nome, que serve de chave primária, e contem apenas dois atributos: Nome, indicando o nome do item que será utilizando como chave primária no banco de dados; desc, o conteúdo do item da lista em específico.

## Sumário ##

1. [Criação do Banco de Dados](#database-creation)
2. [Configuração do projeto](#project-configuration)<br/>
3. [Hibernate Configuration](#hibernate-configuration)
4. [Project Build & Run](#project-build-and-run)
5. [Built With](#built-with)

### Instruções ###
### Database Creation ###

A primeira etapa para executar o projeto é gerar a tabela no banco de dados. Isto pode ser realizado de duas formas distintas:


* **Run query**: Criar a tabela de forma manual, através da interface SQL do PostgreSQL.

```
CREATE TABLE public.todo
(
  name character varying(20) NOT NULL,
  descr character varying(255),
  CONSTRAINT todo_pkey PRIMARY KEY (name)
)
```

* **Deixar que o Hibernate crie** - Desta forma o Hibernate deve ser informado de que pode criar as tabelas necessárias. A propriedade que regula esta atuação é “hbm2ddl.auto” que deve ser atribuída como “create”. Após a mudança, uma vez executado o programa, a propriedade deve ser novamente modificada para “update”, caso contrário o hibernate recriará a tabela a cada excução do  software. Segue os passos:


	Trocar a propriedade src/hibernate.cfg.xml hibernate.hbm2ddl.auto para create.
	Executar aplicação.
	Trocar src/hibernate.cfg.xmls hibernate.hbm2ddl.auto para update.


### Project Configuration ###

Para executar a aplicação, o único requisito é possuir o Servidor Apache Tomcat V9.0+ configurado, e então seguir as etapas:

```
1. Importar o projeto.
2. Atribuir Build Paths
3. Configurar Java Resources/src/hibernate.cfg.xml.
4. Executar index.xhtml no eclipse ou através do link configurado no Tomcat.
```

### Hibernate Configuration ###
As propriedades seguintes do Hibernate devem ser configuradas para cada configuração distinta de Banco de Dados no arquivo "src/hibernate.cfg.xml":

```
hibernate.connection.username: <Usuário BD>
hibernate.connection.password: <Password BD>
hibernate.connection.url: jdbc:postgresql://localhost:<postgres port> / <database name> (configurado para localhost, conferir se porta está liberada)
```

### Project Build and Run ###

#### Eclipse ####

No Eclipse, existem alguns ajustes necessários para utilizar o software


1. Configurar servlet Apache Tomcat Version 9.0+
2. Importar pasta como um projeto Eclipse.
3. Guarantir as seguintes BuildPaths:
	1. JSF (Java Server Faces - MyFaces or Mojarra)
	2. Hibernate
	3. Apache Tomcat v9.0
	4. Postgres JDBC (Postgres java driver)
	5. Java 9+

#### Others ####

Para outras IDEs, ou gerenciadores de builds, as buildpaths precisam ser configuradas conforme já mencionado, e o servlet Tomcat precisa estar configurado para o arquivo index.xhtml 

### Built With ###
 - JSF
 - PostgreSQL
 - Bootstrap 4
 - Hibernate
 