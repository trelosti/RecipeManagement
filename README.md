# Back end

## Environment:

```
Language:   Java 17
DB:         MySQL 8.0
Deploy:     Docker 20.10.17 
```

## Build application

> `mvn clean package`

## Deploy locally

1. Run `mvn clean package`

2. Prepare `.env.local` file in project directory, with environment variables. Like in example:
```text
 DB_USER={{mysql user name}}
 DB_PASSWORD={{mysql password for user}}
```

3. Build the Docker image for your application by running the following command in project directory:
> `docker-compose up --build`

This will start the Spring Boot application and any other dependencies in the docker-compose.yml file. You can access the application at http://localhost:8080.

## StyleGuides

To run style check it is needed to execute `mvn checkstyle:checkstyle`
#### Dependencies

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-checkstyle-plugin</artifactId>
</plugin>
```

## Tests

To run test it is needed to execute `mvn test`.  

#### Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <scope>test</scope>
</dependency>
```

