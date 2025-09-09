
FROM eclipse-temurin:17-jdk-jammy

# Copy JAR into container
COPY target/sip-planner.jar /app/sip-planner.jar

WORKDIR /app

# Expose default Spring Boot port
EXPOSE 8080

ENTRYPOINT ["java","-jar","sip-planner.jar"]
