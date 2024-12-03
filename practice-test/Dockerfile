# Image Dockerfile is your notebook
# Install Java
FROM eclipse-temurin:23-jdk

LABEL maintainer="calvinmilner"


# How to build the application
# Create a directory /app and change directory into /app
# Outside of /app
WORKDIR /app

# Inside of /app
# Copy files over src dst
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

# Build the application
# RUN ./mvnw package -Dmaven.test.skip=true
RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true
# RUN rm -r src don't use this

# If build is successful, jar will be in ./target

## How to run the application
# ENV SERVER_PORT=8080

# This is for railway
ENV PORT=8080

# What port does the application need
# EXPOSE ${SERVER_PORT}
EXPOSE ${PORT}

# Run the app
# ENTRYPOINT java -jar target/day12-0.0.1-SNAPSHOT.jar
ENTRYPOINT SERVER_PORT=${PORT} java -jar target/practice-test-0.0.1-SNAPSHOT.jar