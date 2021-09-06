FROM openjdk:8-jdk-alpine

COPY build/libs/apas-0.0.1-SNAPSHOT.jar apas.jar

ENTRYPOINT ["java","-jar","/apas.jar"]