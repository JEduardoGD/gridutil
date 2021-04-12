# gridutil
measure the distance between two grids locator

- Java 8
- Maven 3.0.6
- Springboot 2.4.4

Getting grid location information: https://en.wikipedia.org/wiki/Maidenhead_Locator_System

This is a rest service

## Compilation
```bash
mvn clean package
```

## Running
```bash
java -jar target/geo-1.0.0.jar
```

## Sonarqube
On docker-compose-sonar.yml there is a sonarqube instance to test, run it with
```bash
docker-compose -f docker-compose-sonar.yml up
```
With maven you can test the code
```bash
maven sonar.sonar
```


## Testing
make a GET petition to http://localhost:8080/measuredistance/GRIDLOCATOR1/and/GRIDLOCATOR2

be sure your GRIDLOCATOR 1 and 2 have the Maidenhead up to 10 position

## Docker

### Build image
```bash
docker build -t gridlocator .
```
### Creating container and run
```bash
docker run -p 8080:8080 gridlocator
```

## Running on docker-compose
Important: you first have to make the docker image
```bash
docker-compose up
```

Running as daemon
```bash
docker-compose up -d
```


# Thanks

feel free of make suggestions and pull request to improve this code.