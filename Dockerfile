FROM eclipse-temurin:17-jdk-alpine
COPY ./build/libs/*.jar books.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/books.jar"]