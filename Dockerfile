#Image de base
FROM  openjdk:17-alpine
LABEL maintainer="clarissetomavo.bi@gmail.com"
VOLUME /main-app
ADD target/formation-demo1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9090
# java -jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]