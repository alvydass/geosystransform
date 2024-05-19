# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file and download the project dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project source into the working directory
COPY src ./src

# Copy the frontend resources
COPY frontend ./frontend

# Package the application
RUN mvn clean package -Pproduction -DskipTests

# Stage 2: Create a runtime image using JDK
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the previous build stage
COPY --from=build /app/target/transform-0.0.1-SNAPSHOT.jar /app/transform-0.0.1-SNAPSHOT.jar

# Ensure the frontend resources are copied
COPY --from=build /app/frontend /app/frontend

# Expose port 8080
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/transform-0.0.1-SNAPSHOT.jar"]
