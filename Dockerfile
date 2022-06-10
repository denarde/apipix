FROM openjdk:11
MAINTAINER Denarde
EXPOSE 8085
ADD target/apipix-0.0.1-SNAPSHOT.jar apipix.jar
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "apipix.jar"]