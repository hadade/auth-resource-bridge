FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar resource-server.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "resource-server.jar"]
