# Gradle 8.12.0 JDK 21
FROM gradle:8.12.0-jdk21 as build
COPY --chown=gradle:gradle . /project/src
WORKDIR /project/src
RUN gradle build

# JDK 21
FROM openjdk:21-jdk

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Create dir /app
RUN mkdir /app

# Copy jar from build to /app dir
COPY --from=build /project/src/build/libs/ms-games-1.0.0.jar /app/app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]