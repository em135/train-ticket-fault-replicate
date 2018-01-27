FROM java:8-jre

ADD ./target/ts-execute-service-1.0.jar /app/
CMD ["java", "-Xmx100m", "-jar", "/app/ts-execute-service-1.0.jar"]

EXPOSE 12386