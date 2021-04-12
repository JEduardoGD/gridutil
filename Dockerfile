FROM openjdk:8-alpine

LABEL maintainer="eduardo_gd@hotmail.com"

VOLUME /tmp

ADD target/geo-*.jar geo.jar

WORKDIR /

ENTRYPOINT ["java","-jar", "/geo.jar"]