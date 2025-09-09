# SIP Planner - Spring Boot (Maven)

Small, production-lean service exposing SIP planning and portfolio health endpoints.

# Project Structure
sip-planner-maven/
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ com/example/sipplanner/
│  │  │      ├─ SipPlannerApplication.java
│  │  │      ├─ api/
│  │  │      │   ├─ GlobalExceptionHandler.java
│  │  │      │   ├─ SipController.java
│  │  │      │   ├─ SipRequest.java
│  │  │      │   └─ SipResponse.java
│  │  │      └─ service/
│  │  │          └─ SipService.java
│  │  └─ resources/
│  │      └─ application.properties
│  └─ test/
│     └─ java/
│         └─ com/example/sipplanner/service/
│             └─ SipServiceTest.java
├─ pom.xml
├─ Dockerfile
├─ README.md
└─ postman_collection.json


## What you get
- `POST /v1/sip/calc` - calculate SIP corpus
- `GET /v1/health` - service status + commit hash (via `GIT_COMMIT` env)
- `GET /v1/metrics` - simple counters (requests served, avg latency)
- Simple portfolio health check method in service
- Validation, logging, basic error handling
- Unit tests for core calculation logic
- Dockerfile + Makefile
- Postman collection (postman_collection.json)

## Quick start (Java 17 required)
Build:
```bash
mvn -U -DskipTests=false clean package
```

Run:
```bash
java -jar target/sip-planner-0.1.0.jar
# or with env commit
GIT_COMMIT=$(git rev-parse --short HEAD || echo unknown) java -DGIT_COMMIT=$GIT_COMMIT -jar target/sip-planner-0.1.0.jar
```

Docker:
```bash
docker build -t sip-planner:latest .
docker run -p 8080:8080 -e GIT_COMMIT=demo sip-planner:latest
```

Test (unit tests + coverage via jacoco):
```bash
mvn test
# report in target/site/jacoco/index.html
```

API examples:
```bash
curl -s -X POST http://localhost:8080/v1/sip/calc -H "Content-Type: application/json" -d '{"monthlyInvestment":5000,"annualRatePercent":12,"years":10}'
curl http://localhost:8080/v1/health
curl http://localhost:8080/v1/metrics
```

## Project structure
Standard maven layout under `src/main/java/com/example/sipplanner`.

## Notes on production-readiness
- Env-based config: uses Spring Boot conventions (application.properties / env overrides)
- Structured logging via SLF4J
- ControllerAdvice for consistent error responses
- In-memory metrics (AtomicLong) — replace with Prometheus or Micrometer for prod
- For deployment: container image + use cloud provider's build & deploy (Render, Railway)

