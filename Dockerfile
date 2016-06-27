FROM maven:3-jdk-8

#RUN curl http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.38/mysql-connector-java-5.1.38.jar -o $CATALINA_HOME/lib/mysql-connector-java-5.1.38.jar

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ADD . /usr/src/app

RUN mvn test
RUN mvn install
RUN ln -sfn target/$(ls target | grep stlcourts-api-* | grep -v original) stlcourts-api.jar

CMD ["java","-jar","stlcourts-api.jar"]