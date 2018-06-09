# Recipe Index

This web application was built to help search recipes stored in an elasticsearch index

## Prerequisites

Use the `recipe-ocr` project to populate a `recipe` elasticsearch index with recipes

Update the property file for the desired environment to point to the running elasticsearch server/cluster

## Environments

### Development

To start the service in developer mode, run the following command

`mvn spring-boot:run -Dspring.profiles.active=dev`

OR 

`./gradlew bootRun -Dspring.profiles.active=dev`

OR

`java -Dspring.profiles.active=dev -jar recipe-index-<version>.jar`

### Test

To start the service in test environment mode, you will need to first configure a MySQL database with the schema and user found in the `application-test.properties` file

You will also need to configure your system environment variables to contain the `RECIPE_PASSWORD` which matches your user password for the database

Then run the startup script

`./start-test-env.sh`

If you need to kill the process and have exited the window where it is running, you can run the following commands on Windows.

`netstat -ano | findstr :<port>` gives the PID of the process

`taskkill /PID <the-PID> /F` kills the process

For Linux:

`fuser -k <port>/tcp`




## Deprecated Docker Image

An image is published to the following docker hub repo

[https://hub.docker.com/r/grantharper/recipe-index](https://hub.docker.com/r/grantharper/recipe-index)

To run the container image with the embedded database on port 80 of the host machine, you can run the following command

`docker run -e "SPRING_PROFILES_ACTIVE=dev" -p 80:8080 -t grantharper/recipe-index:latest`

To build a local image 

`mvn install dockerfile:build`



