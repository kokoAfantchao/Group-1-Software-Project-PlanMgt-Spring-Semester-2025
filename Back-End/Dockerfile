
# Use Gradle to build the application
FROM gradle:8.2.1-jdk17 AS build

# Set the working directory for the build
WORKDIR /app

# Copy the Gradle project files
COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY gradlew ./gradlew
RUN chmod +x ./gradlew
COPY src ./src
RUN pwd 
RUN ls -al  
# Build the application
RUN ./gradlew build      

# Use an official OpenJDK runtime for the final image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

RUN pwd 
RUN ls -al 
# Copy the built Spring Boot JAR file from the build stage
#COPY --from=build /app/build/libs/*.jar app.jar
COPY --from=build /app/build/libs/*.war app.war

# Expose the default Spring Boot port
EXPOSE 8080

# Define the command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.war"]
