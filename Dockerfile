#Image de base
FROM  openjdk:11
LABEL maintainer="baucube.learn@gmail.com"
VOLUME /main-app
ADD target/formation-demo1-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
# java -jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
