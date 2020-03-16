FROM jdk:8
MAINTAINER lovely1244966299@126.com
ADD target/demo-2.0.jar demo.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/demo.jar","--spring.profiles.active=prod"]