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

## Testing
make a GET petition to http://localhost:8080/measuredistance/GRIDLOCATOR1/and/GRIDLOCATOR2

be sure your GRIDLOCATOR 1 and 2 have Maidenhead up to 10 position
