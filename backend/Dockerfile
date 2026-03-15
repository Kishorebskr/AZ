FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Install Gradle 8.5 directly
RUN apt-get update && apt-get install -y wget unzip && \
    wget -q https://services.gradle.org/distributions/gradle-8.5-bin.zip -P /tmp && \
    unzip -q /tmp/gradle-8.5-bin.zip -d /opt/gradle && \
    ln -s /opt/gradle/gradle-8.5/bin/gradle /usr/bin/gradle && \
    rm /tmp/gradle-8.5-bin.zip

COPY . .
RUN gradle build -x test --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
