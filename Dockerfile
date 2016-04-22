FROM java:8
VOLUME /tmp

RUN curl http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.38/mysql-connector-java-5.1.38.jar -o $CATALINA_HOME/lib/mysql-connector-java-5.1.38.jar

RUN apt-get install maven
RUN mvn package && java -jar target/stlcourts-api.jar

ADD stlcourts-api.jar app.jar

RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]