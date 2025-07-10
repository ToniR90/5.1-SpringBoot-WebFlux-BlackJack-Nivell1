FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY ./target/blackjack-0.0.1-SNAPSHOT.jar blackjack.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "blackjack.jar"]