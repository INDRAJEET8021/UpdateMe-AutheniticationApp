# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and the pom.xml to the container
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download the dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code to the container
COPY src ./src

# Build the application
RUN ./mvnw package -DskipTests

# Copy the jar file to the container
COPY target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Define the command to run the app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
