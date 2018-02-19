#!/bin/bash
cd /home/ubuntu/recipe-index
git pull
mvn clean install

# command to start app. doesn't work during instance spinup
# mvn spring-boot:run -Dspring.profiles.active=prod > output.log 2>&1 &

# to kill a process running on a given tcp port
# find java process
# sudo netstat -plten |grep java
# kill process
# sudo kill <pid>