FROM openjdk:21-slim as builder
WORKDIR /app
COPY . .
RUN ./mvnw -DskipTests clean package

FROM openjdk:21-slim
WORKDIR /app
# Copy the built application from the previous image
COPY --from=builder /app/target/iron-bank-0.0.1-SNAPSHOT.jar ./app.jar
# Expose the application port
EXPOSE 8080
# Start the application
CMD ["java", "-jar", "app.jar"]