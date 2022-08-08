FROM  openjdk:17-oracle
RUN mkdir /opt/app
COPY example-0.0.1-SNAPSHOT.jar /opt/app
EXPOSE 9091:9091
CMD ["java", "-jar", "/opt/app/example-0.0.1-SNAPSHOT.jar"]