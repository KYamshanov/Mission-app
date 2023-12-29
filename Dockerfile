FROM openjdk:17
WORKDIR /app/
ADD /jsBackend/build/libs/jsBackend-all.jar application.jar
COPY missionAppJs/build/distributions .
EXPOSE 80
EXPOSE 422
ENTRYPOINT ["java","-jar","application.jar"]