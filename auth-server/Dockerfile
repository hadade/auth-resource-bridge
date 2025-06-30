FROM eclipse-temurin:21-jre-alpine

WORKDIR /app
CMD ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar auth-server.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "auth-server.jar"]
