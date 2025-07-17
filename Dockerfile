# Use lightweight JDK image
FROM openjdk:17-alpine

# Set working directory
WORKDIR /app

# Copy everything into the image
COPY . .

# Build the Kotlin project
RUN ./gradlew clean shadowJar

# Use environment variable PORT or fallback to 9090
ENV PORT=9090

# Run the server
CMD ["java", "-jar", "build/libs/vpn-relay-all.jar"]
