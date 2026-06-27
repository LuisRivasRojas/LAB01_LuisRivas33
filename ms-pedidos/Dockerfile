# Etapa 1: build con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: imagen final ligera
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Puerto configurable via variable de entorno (default 8082)
ENV SERVER_PORT=8082
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]
