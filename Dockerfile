FROM adoptopenjdk/openjdk11:alpine
EXPOSE 8085
ADD target/book-shop-0.0.1-SNAPSHOT.jar book-shop-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","book-shop-0.0.1-SNAPSHOT.jar"]
