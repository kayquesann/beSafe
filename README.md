<h1 align="center">Oralytics</h1>


![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)

## Integrantes do Grupo

Kayque Ferreira dos Santos

André Alves da Silva

Gabriel de Souza Grego

## Descrição

BeSafe é um portal criado para oferecer apoio, orientação e informações confiáveis às pessoas afetadas por enchentes.
Na BeSafe, as pessoas podem encontrar materiais educativos e dicas práticas sobre como agir antes, durante e depois
de uma enchente. Nossa missão é empoderar a população com informações que ajudam na prevenção, no enfrentamento e
na recuperação, protegendo vidas, lares e comunidades.

## Video demonstração do projeto
[VIDEO DEMONSTRAÇÃO](https://youtu.be/Ldp2I_8-uuw)

## Video PITCH do projeto
[VIDEO PITCH](https://youtu.be/OKqI3D1RpQE)

🚀 Acesse a Aplicação
Você pode acessar a aplicação diretamente através do IP público abaixo:

👉 http://20.246.224.138:8080/


## Instruções para Rodar a Aplicação

### Pré-requisitos

- Java 17+
- Oracle Database
- Gradle 7+
- IDE (como IntelliJ ou Eclipse)
- Docker (+container com RabbitMQ)

### Configuração

1. Clone o repositório:

```
git clone https://github.com/kayquesann/beSafe.git
```


2. Configure as variáveis de ambiente dos arquivos application.properties dos dois módulos para a conexão com o banco de dados Oracle e com o RabbitMQ
. Como padrão, o Rabbit usa "guest" como login e senha.

```
spring.application.name=beSafe
spring.datasource.url= ${url_banco}
spring.datasource.username=${oracle_user)
spring.datasource.password=${oracle_password}
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=${rabbitUsername}
spring.rabbitmq.password=${rabbitPassword}
```

3. Suba um container com RabbitMQ no Docker digitando no cmd o comando abaixo.  É necessário ter o Docker instalado na sua máquina.

```
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management.
```

6. Execute a aplicação, rodando os dois módulos do repositório (src e logsCadastro)

