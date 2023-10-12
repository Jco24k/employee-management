FROM maven:3.8-openjdk-17-slim AS build

WORKDIR /usr/app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:22-ea-17-jdk-slim
WORKDIR /usr/app
COPY --from=build ["/usr/app/target/*.jar" ,"./app.jar"]
EXPOSE 9091
ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]