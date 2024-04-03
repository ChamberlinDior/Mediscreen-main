# Utilisation de l'image OpenJDK 17 avec Alpine Linux comme base
FROM openjdk:17-jdk-alpine

COPY target/patientNote-0.0.1-SNAPSHOT.jar patient-note.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "patient-note.jar"]


