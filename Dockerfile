
# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# pom.xml + src copy
COPY pom.xml .
COPY src ./src

# Build jar
RUN mvn clean package -DskipTests


# ---------- Run stage ----------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Build stage la irundhu jar copy
COPY --from=build /app/target/habitmanager-0.0.1-SNAPSHOT.jar app.jar

# Spring Boot port
EXPOSE 8081

# Run app
ENTRYPOINT ["java","-jar","app.jar"]
