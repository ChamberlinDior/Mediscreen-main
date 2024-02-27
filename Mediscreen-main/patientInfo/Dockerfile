# Utilisation de l'image OpenJDK 17 avec Alpine Linux comme base
FROM openjdk:17-jdk-alpine

# Copie du fichier JAR généré par Maven dans le répertoire racine de l'image Docker
COPY target/patientInfo-0.0.1-SNAPSHOT.jar .

# Exposition du port 8081 pour permettre l'accès à l'application depuis l'extérieur du conteneur
EXPOSE 8081

# Commande qui sera exécutée lorsqu'un conteneur basé sur cette image sera démarré
CMD ["java", "-jar", "patientInfo-0.0.1-SNAPSHOT.jar"]