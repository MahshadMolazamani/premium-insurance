FROM maven:3.8.8-eclipse-temurin-21 AS build

WORKDIR /home/app/code
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk
WORKDIR /home/app/code
COPY --from=build /home/app/code/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
