FROM hub.yingyingwork.com/base/jdk-8-sh:8u221
VOLUME /tmp
ADD target/demoip-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
