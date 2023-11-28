FROM openjdk:17-oracle
ADD target/notification-producer.jar notification-producer.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","notification-producer.jar"]