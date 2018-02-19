DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `book_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `author` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

insert into book(book_id, author, title) values(1, 'various', 'Sur La Table');

ALTER TABLE `recipe` ADD COLUMN `book_id` bigint(20) DEFAULT NULL;
ALTER TABLE `recipe` ADD KEY `FK5lvil0p1r9il8eutbrd3ojevv` (`book_id`);
ALTER TABLE `recipe` ADD CONSTRAINT `FK5lvil0p1r9il8eutbrd3ojevv` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`);

update recipe set book_id=1 where book_id is null;


