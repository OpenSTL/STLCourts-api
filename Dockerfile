FROM maven:3-jdk-8

#RUN curl http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.38/mysql-connector-java-5.1.38.jar -o $CATALINA_HOME/lib/mysql-connector-java-5.1.38.jar

RUN mvn install
CMD ["java","-jar","target/stlcourts-api.jar"]