FROM maven:3-jdk-8

COPY deploy/muniCourt.cer $JAVA_HOME/jre/lib/security
RUN keytool -noprompt -keystore cacerts -storepass changeit -trustcacerts -importcert -alias rejisCert -file $JAVA_HOME/jre/lib/security/muniCourt.cer

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ADD . /usr/src/app

RUN mvn clean install
RUN ln -sfn target/$(ls target | grep stlcourts-api-* | grep -v original) stlcourts-api.jar

CMD ["java","-jar","stlcourts-api.jar"]