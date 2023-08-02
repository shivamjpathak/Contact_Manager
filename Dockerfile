FROM openjdk:17.0.1-jdk-slim	
EXPOSE 8080
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]