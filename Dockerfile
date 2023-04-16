FROM maven:3.6.3 AS build

# Copy pom.xml to the image
COPY pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml verify clean --fail-never

# Copy the source code
COPY src /home/app/src
RUN mvn -f /home/app/pom.xml package

#FROM tomcat:latest
FROM tomcat:8.0-alpine

LABEL maintainer="emme"
COPY --from=build /home/app/target/Project30.war /usr/local/tomcat/webapps/
EXPOSE 8080

CMD ["catalina.sh", "run"]
