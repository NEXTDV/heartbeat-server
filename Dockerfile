FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY settings.gradle .
COPY build.gradle .
COPY api api
COPY domain domain
COPY infrastructure infrastructure
COPY config config
RUN ./gradlew :api:bootJar -x test --no-daemon

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/api/build/libs/ ./libs/
RUN find ./libs -name "*.jar" ! -name "*-plain.jar" -exec cp {} app.jar \; && rm -rf ./libs
RUN useradd -r -u 10001 appuser && chown appuser:appuser /app/app.jar
USER 10001
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
