FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/content-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} content-service.jar
ENTRYPOINT ["java", "-jar", "/content-service.jar"]
