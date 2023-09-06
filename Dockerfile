FROM eclipse-temurin:17-jdk-focal
 
WORKDIR /app
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
 
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]


# FROM eclipse-temurin:17-jdk-jammy as builder
# WORKDIR /opt/app
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline
# COPY ./src ./src
# RUN ./mvnw clean install



# FROM eclipse-temurin:17-jdk-alpine
# VOLUME /tmp
# RUN ./mvnw clean package
# COPY target/*.jar app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]


#docker build -t spring-boot-docker .
#docker run -t -d -p 9000:9000 --name jwtApi spring-boot-docker