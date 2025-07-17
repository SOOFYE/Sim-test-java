# Use a lightweight OpenJDK image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the project jar file (update name as per your jar output)
COPY target/Simplifi-test-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8000

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
