FROM maven:3.8-openjdk-17-slim AS build

WORKDIR /usr/app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:22-ea-17-jdk-slim

WORKDIR /usr/app
COPY --from=build ["/usr/app/target/*.jar" ,"./app.jar"]

ARG PORT=8080
ENV PORT=${PORT}
EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]