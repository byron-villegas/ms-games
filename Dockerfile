# Gradle 8.12.0 JDK 21
FROM gradle:8.12.0-jdk21 as build

# Set work dir
WORKDIR /project

COPY --chown=gradle:gradle . /project
RUN gradle build

# JDK 21
FROM openjdk:21-jdk

# Set work dir
WORKDIR /app

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Copy jar from build to /app dir
COPY --from=build /project/build/libs/ms-games-1.0.0.jar /app/app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]