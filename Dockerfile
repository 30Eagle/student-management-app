# ------------------
# 1. Build Stage: Compiles the Java application using Maven
# ------------------
FROM maven:3.8.7-openjdk-17 AS build
WORKDIR /app

# Copy the entire project into the container's build environment
COPY . .

# Run the Maven package command to build the JAR file
# This is equivalent to the Build Command we discussed
RUN mvn clean package -DskipTests

# ------------------
# 2. Package Stage: Creates a lean image to run the application
# ------------------
FROM openjdk:17-jdk-slim
WORKDIR /app

# Expose the standard Spring Boot port
EXPOSE 8080

# Copy the executable JAR from the 'build' stage into the final image
# NOTE: The JAR name 'StudentManag.jar' MUST match your project's output name
COPY --from=build /app/target/StudentManag.jar StudentManag.jar

# Define the command to run the application when the container starts
# This is equivalent to the Start Command you entered in Render
ENTRYPOINT ["java", "-jar", "StudentManag.jar"]