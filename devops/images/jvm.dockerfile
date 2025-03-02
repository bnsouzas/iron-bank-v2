FROM gcr.io/distroless/java21
COPY ./target/app.jar ./app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]