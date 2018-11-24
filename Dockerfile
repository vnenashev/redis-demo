FROM maven:3.6.0-jdk-8 as build
COPY pom.xml /tmp/
WORKDIR /tmp/
RUN mvn dependency:go-offline
COPY src /tmp/src/
RUN mvn package

FROM openjdk:8-jre-alpine
COPY --from=build /tmp/target/*.jar /app.jar
EXPOSE 8080
VOLUME /config/
CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/app.jar", "--spring.config.additional-location=/config/"]
