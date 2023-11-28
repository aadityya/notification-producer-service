FROM openjdk:17-oracle
ADD target/notification-producer-with-binder.jar notification-producer-with-binder.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","notification-producer-with-binder.jar"]