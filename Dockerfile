FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY build/libs/Senla-Course-1.0.0.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Senla-Course-1.0.0.jar"]
