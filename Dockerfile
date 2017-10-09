FROM maven:3-jdk-8

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ADD . /usr/src/app

# Add REJIS cert to java cacerts
RUN keytool -noprompt -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -trustcacerts -importcert -alias rejisCert -file /usr/src/app/deploy/muniCourt.cer

RUN mvn clean install
RUN ln -sfn target/$(ls target | grep stlcourts-api-* | grep -v original) stlcourts-api.jar

CMD ["java","-jar","stlcourts-api.jar"]