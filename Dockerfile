FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/demo
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} demo.jar
ENTRYPOINT ["java","-jar","demo.jar"]
