FROM library/openjdk:17-jdk-alpine
COPY target/url-shortener-1.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
