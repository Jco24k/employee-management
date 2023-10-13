FROM maven:3.8-openjdk-17-slim AS build

WORKDIR /usr/app
COPY . .
RUN mvn clean package -DskipTests


FROM openjdk:22-ea-17-jdk-slim

WORKDIR /usr/app
COPY --from=build ["/usr/app/target/*.jar" ,"./app.jar"]
COPY --from=build ["/usr/app/wait-for-it.sh","."]

ARG FILE="wait-for-it.sh"
RUN apt-get update
RUN apt install -y netcat-traditional
RUN chmod +x $FILE

ARG PORT=8080
ENV PORT=${PORT}
EXPOSE ${PORT}

ENTRYPOINT ["./wait-for-it.sh"]