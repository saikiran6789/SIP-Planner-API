# Build Stage
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build jar
COPY src ./src
RUN mvn clean package spring-boot:repackage -DskipTests

# Run Stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the bootable JAR
COPY --from=build /app/target/sip-planner.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
