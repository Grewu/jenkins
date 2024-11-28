FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY build/libs/config-1.0.0.jar .

ENTRYPOINT ["java", "-jar", "config-1.0.0.jar"]
