# Use official Gradle image with JDK
FROM gradle:8.5.0-jdk17

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Install xargs (needed by Render)
USER root
RUN apt-get update && apt-get install -y findutils

# Run the Kotlin app directly using Gradle
CMD ["gradle", "run"]
