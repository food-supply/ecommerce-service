FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-Xmx400m", "-jar", "app.jar"]
