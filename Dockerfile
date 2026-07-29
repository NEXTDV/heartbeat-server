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
COPY --from=build /app/api/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
