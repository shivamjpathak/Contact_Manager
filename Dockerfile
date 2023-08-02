FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY /target/smartContactManager-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]