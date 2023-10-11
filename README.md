# LottoGame

## Functionalities of the Lotto Game Application
- Browsing previous winning numbers
- Ticket generating from user numbers
- Scheduler is generating winning numbers once a week
- Request validation


## Tech

LottoGame is developed using following technologies: <br>

Core: <br>
![image](https://img.shields.io/badge/17-Java-orange?style=for-the-badge) &nbsp;
![image](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring) &nbsp;
![image](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white) &nbsp;

Testing:<br>
![image](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge) &nbsp;
![image](https://img.shields.io/badge/Testcontainers-9B489A?style=for-the-badge) &nbsp;


## Installation and run

### Requirements:
- Docker

### To run the application:
- Just run following command, and wait for containers to be pulled up and started.

``
docker compose up
``

= Alternatively you can run docker-compose file through you IDE

After everything builds and ready, you can start application and test using [Postman](https://www.postman.com/)
or use <a href="http://localhost:8080/swagger-ui/index.html#/">Swagger</a>. Please note, that lottery results are generated
each Saturday at 12:00.<br>


## Rest-API Endpoints

Service url: http://localhost:8080

| HTTP METHOD | Endpoint           | Action                                   |
|-------------|--------------------|------------------------------------------|
| POST        | /inputNumbers      | To input 6 distinct numbers              |
| GET         | /results/{id}      | To retrieve lottery results for given ID |
