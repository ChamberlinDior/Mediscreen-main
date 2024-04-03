FROM openjdk:17-jdk-alpine

COPY target/patientReport-0.0.1-SNAPSHOT.jar patient-report.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "patient-report.jar"]