# RDS Maintenance Info

## Dump database

Connect to EC2 instance with mysql client installed 

Run the following command:

`mysqldump --result-file=dump.sql --user=<username> --password --host=<hostname> --protocol=tcp --port=3306 --default-character-set=utf8 --single-transaction=TRUE --skip-triggers recipe`