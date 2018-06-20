FROM java:8-jre

ADD ./target/ts-train-service-1.0.jar /app/
CMD ["java", "-Xmx100m", "-jar", "/app/ts-train-service-1.0.jar"]

EXPOSE 14567