# Recipe Index

This service was built to help index paper copies of recipes in a large volume

To start the service in developer mode, run the following command

`mvn spring-boot:run -Dspring.profiles.active=dev`

To start the service in test environment mode, you will need to first configure a MySQL database with the schema and user found in the `application-test.properties` file

You will also need to configure your system environment variables to contain the `RECIPE_PASSWORD` which matches your user password for the database

#Docker info

This code is published to the following docker hub repo

[https://hub.docker.com/r/grantharper/recipe-index](https://hub.docker.com/r/grantharper/recipe-index)

To run the container image with the embedded database on port 80 of the host machine, you can run the following command

`docker run -e "SPRING_PROFILES_ACTIVE=dev" -p 80:8080 -t grantharper/recipe-index:latest`

To build a local image 

`mvn install dockerfile:build`