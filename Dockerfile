FROM maven:3-jdk-8

COPY deploy/muniCourt.cer $JAVA_HOME/jre/lib/security
RUN \
    cd $JAVA_HOME/jre/lib/security \
    && keytool -keystore cacerts -storepass yourSTLCourts -noprompt -trustcacerts -importcert -alias rejisCert -file muniCourt.cer

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

ADD . /usr/src/app

RUN mvn clean install
RUN ln -sfn target/$(ls target | grep stlcourts-api-* | grep -v original) stlcourts-api.jar

CMD ["java","-jar","stlcourts-api.jar"]