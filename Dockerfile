FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
# Following are the commands for building image and running the docker image.
# docker build -t springio/gs-spring-boot-docker .
# docker run -p 8080:8080 springio/gs-spring-boot-docker

# FOR PUSING
# docker tag clickpay/first-image qaximalee/clickpay
# docker push qaximalee/clickpay:clickpay/first-image