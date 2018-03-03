# AWS Deployment

## RDS Database Setup

Created a dev micro instance in the AWS console

Connected via a server already in the VPC so that the database isn't publicly accessible with mysql client installed

Connect to the database with the following command

`mysql -u <db-username> -p -h <db-hostname>`

Change databases to recipe

`use recipe;`

Load the data by copying and pasting the database export from my local machine

Create the database user

`create user recipe_user identified by '<password>';`

Grant permissions for the recipe_user to access the recipe database

* `grant select on recipe.* to 'recipe_user';`
* `grant update on recipe.* to 'recipe_user';`
* `grant delete on recipe.* to 'recipe_user';`
* `grant insert on recipe.* to 'recipe_user';`

Add the user for the application

``` sql
CREATE TABLE `recipe_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(60) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jq3rw6d5b3ith3gismoykevsu` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO `recipe_user` VALUES (1,'hashed-password','username');

``` 

## Elastic Beanstalk 

* `eb init`

* `eb create`

Created a Java-SE environment with the config identified in the `Procfile` and `Buildfile`. Additional information can be found in the [AWS docs](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/java-se-procfile.html).

Configured reverse proxy to redirect to https using `.ebextensions/nginx/conf.d/myconf.conf` 

* `eb deploy`

To deploy changes that are staged in git but not commited
* `eb deploy --staged`

Add environment variables for the database config that is listed in `application-prodeb.properties`

Store the config changes made such as changing the health check url, updating the security groups, and adding environment variables by running the following command

`eb config save --cfg <config-name>`

This will store a file in `.elasticbeanstalk/saved_configs/<config-name>.yml` which will be used when creating the environment from scratch

Tear down the environment using `eb terminate`

Then re-create using the config `eb create --cfg savedconfig`


## EC2 

Initially attempted this approach, but the reverse proxy was easier to configure using elastic beanstalk as outlined above.

Start a ubuntu EC2 instance that is accessible over port 8080 from an elastic load balancer

Run the following commands to install java and maven

* `sudo add-apt-repository ppa:webupd8team/java`
* `sudo apt-get update`
* `sudo apt-get install oracle-java8-installer`
* `sudo /bin/sh -c 'echo "JAVA_HOME=\"/usr/lib/jvm/java-8-oracle\"" >> /etc/environment'`
* `source /etc/environment`
* `echo "The JAVA_HOME variable is set to $JAVA_HOME"`
* `sudo apt install maven`

Copy the prod properties to the server

* `scp -i recipe-index.pem src/main/resources/application-prod.properties ubuntu@<dns-address>:~`

In order to make this work, I would also need to setup a reverse proxy server on the same machine with a rule to redirect traffic to https, or figure out how to configure the embedded spring boot tomcat to perform this operation.


