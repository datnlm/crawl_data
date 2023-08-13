FROM openjdk:18
WORKDIR /app
COPY ./target/crawl_data-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080

CMD ["java", "-jar", "crawl_data-0.0.1-SNAPSHOT.jar"]
