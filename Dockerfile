# Use JDK image with Maven already installed
FROM maven:3.9.9-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (layer caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build JAR
COPY src ./src
RUN mvn clean package -DskipTests

# ---------------------------
# Run Stage (lightweight JRE)
# ---------------------------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/sip-planner.jar app.jar

# Expose app port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
