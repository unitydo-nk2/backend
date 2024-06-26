FROM openjdk:21-ea-17-jdk-slim AS build
WORKDIR /build/
COPY pom.xml /build/
COPY src /build/src/
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean;
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:21
WORKDIR /app

#ARG JAR_FILE=/app/target/.jar

COPY --from=build /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT exec java -jar app.jar
