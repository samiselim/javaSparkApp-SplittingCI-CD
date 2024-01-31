FROM openjdk:8-jre-alpine

WORKDIR /src/app

COPY ./target/JavaApp-*.jar /usr/app/

CMD java -jar JavaApp-*.jar