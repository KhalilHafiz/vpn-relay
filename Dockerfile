FROM gradle:8.4-jdk17-alpine AS builder

WORKDIR /app
COPY . .

RUN gradle shadowJar --no-daemon

FROM openjdk:17-alpine

WORKDIR /app
COPY --from=builder /app/build/libs/vpn-relay-all.jar .

ENV PORT=9090
CMD ["java", "-jar", "vpn-relay-all.jar"]
