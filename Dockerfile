FROM openjdk:8-jre-alpine

EXPOSE 8080

WORKDIR /src/app

COPY ./target/JavaApp-*.jar /usr/app/

CMD java -jar JavaApp-*.jar