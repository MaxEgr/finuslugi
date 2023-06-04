FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests
CMD ["java", "-jar", "target/your-app.jar"]
