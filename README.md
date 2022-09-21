### Data source
https://exchangerate.host
* BUG in endpoint https://api.exchangerate.host/latest?base=EUR is returning default date 2022-04-27
* Used example endpoint https://api.exchangerate.host/2022-09-19/?base=EUR&amount=1000000

### Dev Stack
* Java 17
* Spring Boot
* Gradle
* WebFlux
* Redis

### REDIS local dev
``` 
docker run -m 1024m --memory-reservation=512m -p 6379:6379 --name redis redis
``` 

### Gradle run
``` 
* ./gradlew bootRun
``` 

### Swagger / OpenAPI Doc
http://localhost:8080/swagger-ui/

### Postman Collection with examples
* in ./doc/ folder

