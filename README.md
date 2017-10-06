# Recipe Index

This service was built to help index paper copies of recipes in a large volume

To start the service in developer mode, run the following command

`mvn spring-boot:run -Dspring.profiles.active=dev`

To start the service in test environment mode, you will need to first configure a MySQL database with the schema and user found in the `application-test.properties` file

You will also need to configure your system environment variables to contain the `RECIPE_PASSWORD` which matches your user password for the database