# Use the OpenJDK base image
FROM openjdk:21-slim-bullseye

# Set the working directory
WORKDIR /app

# Install Maven
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*

# Copy the pom.xml and any other necessary files first
COPY pom.xml .

# Copy the entire source code
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Copy the built JAR file to the final location
RUN mv target/bibliotheque-0.0.1-SNAPSHOT.jar bibliotheque.jar

# Expose the application port
EXPOSE 8080

# Set the entry point
ENTRYPOINT ["java", "-jar", "bibliotheque.jar"]
