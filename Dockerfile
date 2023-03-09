FROM openjdk:17-jdk
WORKDIR /Users/laisg/Desktop/prueba/search
COPY target/search-0.0.1-SNAPSHOT.jar /app
CMD ["java", "-jar", "app/search-0.0.1-SNAPSHOT.jar"]