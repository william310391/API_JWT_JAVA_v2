#Copiando archivos y ejcuntando solo para ejecuion de dockerfile

# FROM eclipse-temurin:17-jdk-focal 
# WORKDIR /app

# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline
 
# COPY src ./src 
# CMD ["./mvnw", "spring-boot:run"]


# usando compilado

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
#RUN ./mvnw clean package
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]



# FROM adoptopenjdk/openjdk8 as builder

# WORKDIR /app
# COPY . /app

# RUN ./mvnw compile jar:jar

# FROM adoptopenjdk/openjdk8:jre

# COPY --from=builder /app/target/*.jar /server.jar

# CMD ["java", "-jar", "/server.jar"]



# FROM eclipse-temurin:17-jdk-jammy as builder
# WORKDIR /opt/app
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./
# RUN ./mvnw dependency:go-offline
# COPY ./src ./src
# RUN ./mvnw clean install





#para docker compose: mvn clean install
#docker build -t spring-boot-docker .
#docker run -t -d -p 9000:9000 --name jwtApi spring-boot-docker