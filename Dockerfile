FROM openjdk:8
ADD target/BookStoreAdmin-0.0.1-SNAPSHOT.jar BookStoreAdmin-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","BookStoreAdmin-0.0.1-SNAPSHOT.jar"]