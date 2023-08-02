FROM openjdk:17.0.1-jdk-slim
ARG JAR_FILE=target/*.jar	
EXPOSE 8080
COPY ./ContactManager/target/smartContactManager-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]