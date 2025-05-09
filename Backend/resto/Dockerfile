# Étape 1: Construction du projet
FROM maven:3.8.8-eclipse-temurin-21-alpine AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier pom.xml et télécharger les dépendances Maven
COPY pom.xml .

# Télécharger les dépendances Maven
RUN mvn dependency:go-offline

# Copier tout le code source dans le conteneur
COPY src ./src

# Construire le JAR de l'application Spring Boot
RUN mvn clean package -DskipTests

# Étape 2: Image de production avec Java 21
FROM eclipse-temurin:21-jre AS runtime

# Définir le répertoire de travail pour le runtime
WORKDIR /app

# Copier le JAR généré depuis l'étape de build
COPY --from=build /app/target/*.jar /app/app.jar

# Exposer le port de Spring Boot
EXPOSE 8085

# Commande pour lancer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
