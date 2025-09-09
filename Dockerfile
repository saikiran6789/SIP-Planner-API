FROM eclipse-temurin:17-jdk-jammy

VOLUME /tmp
COPY target/sip-planner.jar app.jar

ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","/app.jar"]
