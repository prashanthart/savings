FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-ea-28-jdk-slim-buster
COPY --from=build /target/savings-0.0.1-SNAPSHOT.jar savings.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","savings.jar" ]