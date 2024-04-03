# Utilisation de l'image OpenJDK 17 avec Alpine Linux comme base
FROM openjdk:17-jdk-alpine

COPY target/patientInfo-0.0.1-SNAPSHOT.jar patient-info.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "patient-info.jar"]
