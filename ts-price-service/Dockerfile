FROM java:8-jre

ADD ./target/ts-price-service-1.0.jar /app/
CMD ["java", "-Xmx100m", "-jar", "/app/ts-price-service-1.0.jar"]

EXPOSE 16579