FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY --from=build /target/smartContactManager-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]