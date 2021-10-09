FROM openjdk:11-jdk-slim
RUN jlink --no-header-files --no-man-pages --compress=2 --strip-debug --add-modules java.base,java.logging,java.sql,java.naming,java.management,java.instrument,java.desktop,java.security.jgss --output /usr/lib/jvm/spring-boot-runtime
FROM debian:stretch-slim
COPY — from=0 /usr/lib/jvm/spring-boot-runtime /usr/lib/jvm/spring-boot-runtime/
WORKDIR /home/demo
ARG REVISION
COPY target/app-${REVISION}.jar app.jar
ENTRYPOINT [“/usr/lib/jvm/spring-boot-runtime/bin/java”,”-jar”,”app.jar”]