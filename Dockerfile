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
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
