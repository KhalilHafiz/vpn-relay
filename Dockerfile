FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle installDist

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/build/install/vpn-relay/ ./
CMD ["./bin/vpn-relay"]