FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS=""

# Add a jhipster user to run our application so that it doesn't need to run as root
RUN adduser -D -s /bin/sh jhipster
WORKDIR /home/jhipster


# fix dateZone
RUN apk add -U tzdata
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ADD entrypoint.sh entrypoint.sh
RUN chmod 755 entrypoint.sh && chown jhipster:jhipster entrypoint.sh

# Add rule folder
RUN mkdir -p /app/aiops/java/log && chown jhipster:jhipster /app/aiops/java/log

USER jhipster


ADD *.jar app.jar

ENTRYPOINT ["./entrypoint.sh"]

EXPOSE 8082

