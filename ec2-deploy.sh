#!/bin/bash
cd /home/ubuntu/recipe-index
git pull
mvn clean install
mvn spring-boot:run -Dspring.profiles.active=dev > output.log 2>&1 &
