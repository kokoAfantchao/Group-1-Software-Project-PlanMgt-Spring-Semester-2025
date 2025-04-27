
# Use Gradle to build the application
FROM gradle:8.2.1-jdk17 AS build

# Set the working directory for the build
WORKDIR /app

# Copy the Gradle project files
#COPY build.gradle settings.gradle ./
#COPY gradle ./gradle
#COPY src ./src
RUN pwd 
RUN ls -al  
# Build the application
RUN ./gradlew bootRun     

# Use an official OpenJDK runtime for the final image
FROM openjdk:18-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built Spring Boot JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Define the command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
