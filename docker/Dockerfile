# Dockerfile
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY payment-web/target/payment-web-*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
