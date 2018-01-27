FROM java:8-jre

ADD ./target/ts-register-service-1.0.jar /app/
CMD ["java", "-Xmx100m", "-jar", "/app/ts-register-service-1.0.jar"]

EXPOSE 12344