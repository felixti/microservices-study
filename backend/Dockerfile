FROM maven:3-jdk-13 as builder
USER root
WORKDIR /app
COPY pom.xml /app
COPY src /app/src/
RUN mvn package --debug

FROM openjdk:13-jdk-alpine
WORKDIR /app
EXPOSE 8080
COPY --from=builder /app/target/category-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "category-0.0.1-SNAPSHOT.jar"]



