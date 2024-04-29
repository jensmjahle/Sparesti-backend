FROM maven:3.9.5-eclipse-temurin-21 as build
WORKDIR /app
COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY src src
RUN mvn package -DskipTests

FROM build as test
RUN mvn test

FROM build
COPY --from=build /app/target/sparesti-0.0.1-SNAPSHOT.jar SparestiApplication.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","SparestiApplication.jar"]
