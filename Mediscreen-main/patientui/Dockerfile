#Utilise l'image openjdk:17-jdk-alpine comme image de base. C'est une image Alpine Linux avec OpenJDK 17.
FROM openjdk:17-jdk-alpine
#Copie le fichier JAR de l'application Spring Boot depuis le répertoire cible local (target) dans le répertoire racine du conteneur.
COPY target/patientui-0.0.1-SNAPSHOT.jar .
#Expose le port 8080 du conteneur, où l'application Spring Boot s'exécutera.
EXPOSE 8080
#Définit la commande par défaut à exécuter lorsque le conteneur démarre. Dans ce cas, il exécute l'application en exécutant la commande "java -jar" avec le nom du fichier JAR de l'application
CMD ["java", "-jar", "patientui-0.0.1-SNAPSHOT.jar"]