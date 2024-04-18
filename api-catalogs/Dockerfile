ARG MSVC_NAME=api-catalogs
#FROM openjdk:21-oraclelinux8
#FROM eclipse-temurin
#FROM eclipse-temurin:21.0.2_13-jdk
FROM eclipse-temurin:21.0.2_13-jdk-alpine as builder
ARG MSVC_NAME
WORKDIR /app/$MSVC_NAME
#COPY ./target/api-catalogs-0.0.1-SNAPSHOT.jar .
#Descarga de dependencias con Maven
COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
RUN chmod +x ./mvnw
COPY ./$MSVC_NAME/pom.xml .
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target
#RUN ./mvnw dependency:go-offline
#Compilar fuentes y empaquetar
COPY ./$MSVC_NAME/src ./src
RUN ./mvnw clean package -DskipTests
FROM eclipse-temurin:21.0.2_13-jdk-alpine
ARG MSVC_NAME
WORKDIR /app
RUN mkdir ./logs
#ARG MSVC_NAME=api-catalogs
ARG TARGET_FOLDER=/app/$MSVC_NAME/target
COPY --from=builder $TARGET_FOLDER/api-catalogs-0.0.1-SNAPSHOT.jar .
ARG PORT_APP=8003
ENV PORT $PORT_APP
EXPOSE $PORT
#ENTRYPOINT ["java", "-jar", "api-catalogs-0.0.1-SNAPSHOT.jar"]
#CMD sleep 20 && java -jar api-catalogs-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "api-catalogs-0.0.1-SNAPSHOT.jar"]