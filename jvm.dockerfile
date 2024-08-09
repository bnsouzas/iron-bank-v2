FROM debian:12-slim as base
RUN apt update \
    && rm /bin/sh && ln -s /bin/bash /bin/sh \
    && apt install -y curl wget unzip zip \
        build-essential zlib1g-dev \
    && rm -rf /var/lib/apt/lists/*

ARG JAVA_VERSION
ENV JAVA_VERSION 17.0.8-tem
ENV PATH /root/.sdkman/candidates/java/current/bin:$PATH
RUN curl -s "https://get.sdkman.io" | bash \
    && source "$HOME/.sdkman/bin/sdkman-init.sh" \
    && sdk install java $JAVA_VERSION

FROM base as builder
WORKDIR /app
COPY . .
RUN ./mvnw -DskipTests clean package

FROM openjdk:17-slim
WORKDIR /app
# Copy the built application from the previous image
COPY --from=builder /app/target/iron-bank-0.0.1-SNAPSHOT.jar ./app.jar
# Expose the application port
EXPOSE 8080
# Start the application
CMD ["java", "-jar", "app.jar"]