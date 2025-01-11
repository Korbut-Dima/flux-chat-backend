# Stage 1: Build
FROM maven:3.8.5-openjdk-17 as build

WORKDIR /app

# Copy the source code to the container
COPY . .
# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM openjdk:17

EXPOSE 5000

# Copy the jar file from the build stage
COPY --from=build /app/target/flux-chat-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-jar", "/app/app.jar"]