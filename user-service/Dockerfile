FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/service2-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} service2.jar
ENTRYPOINT ["java", "-jar", "/service2.jar"]
