# Utilisation de l'image OpenJDK version 17 avec Alpine comme base
FROM openjdk:17-jdk-alpine

# Copie du fichier JAR généré dans le répertoire cible de l'image
COPY target/patientNote-0.0.1-SNAPSHOT.jar .

# Exposition du port 8082 (celui défini dans l'application Spring Boot)
EXPOSE 8082

# Commande pour exécuter l'application lors du démarrage du conteneur
CMD ["java", "-jar", "patientNote-0.0.1-SNAPSHOT.jar"]

