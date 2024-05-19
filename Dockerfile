# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the project's pom.xml and download project dependencies (layer caching)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw dependency:go-offline -B

# Copy the project source code
COPY src ./src

# Build the project
RUN ./mvnw package -DskipTests

# Copy the built jar file to the working directory
COPY target/*.jar app.jar

# Expose the port the application will run on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]