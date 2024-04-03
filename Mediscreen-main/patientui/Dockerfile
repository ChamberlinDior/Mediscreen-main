FROM openjdk:17-jdk-alpine

COPY target/patientui-0.0.1-SNAPSHOT.jar patient-ui.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar","-Dspring.profiles.active=prod", "patient-ui.jar"]