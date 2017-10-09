/*
SQLyog Ultimate v11.11 (32 bit)
MySQL - 5.5.11 : Database - odr
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`odr` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `odr`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(89) DEFAULT NULL,
  `emailid` varchar(78) DEFAULT NULL,
  `password` varchar(67) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `account` */

insert  into `account`(`id`,`name`,`emailid`,`password`) values (1,'Sivani','siva@gmail.com','siva1234'),(2,'dddffgf','it@gmail.com','ssssss');

/*Table structure for table `fileplaceing` */

DROP TABLE IF EXISTS `fileplaceing`;

CREATE TABLE `fileplaceing` (
  `id` varchar(90) DEFAULT NULL,
  `location` varchar(900) DEFAULT NULL,
  `replacing` varchar(900) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `fileplaceing` */

insert  into `fileplaceing`(`id`,`location`,`replacing`) values ('29','51,79,18,80,85,',''),('35','44,64,76,91,32,7,62,51,10,50,70,11,96,24,','59,89,22,71,19,34,68,29,16,78,30,82,47,6,'),('36','13,81,79,45,11,85,44,83,35,52,100,','59,42,19,47,4,96,67,63,88,84,39,'),('37','90,22,56,32,29,82,1,17,96,44,64,','57,66,3,6,59,9,40,75,77,58,42,'),('38','11,42,85,6,88,9,44,66,24,48,96,','14,29,57,13,10,82,32,17,3,7,30,'),('39','98,22,35,46,93,85,20,18,86,5,79,','6,77,71,90,51,94,34,9,17,30,69,'),('40','1,48,61,84,39,86,79,41,73,45,50,','40,75,46,23,66,12,5,8,38,70,32,'),('41','16,7,72,22,37,76,71,15,100,43,23,','93,58,78,11,8,92,33,38,86,74,98,'),('42','46,56,48,54,24,43,35,96,82,52,84,','42,59,53,63,32,93,13,47,14,98,44,'),('43','80,78,83,32,25,84,12,26,34,92,14,','61,42,9,59,68,7,19,90,58,8,39,'),('44','30,58,28,38,46,','86,85,2,40,'),('47','71,11,33,24,8,56,25,76,42,51,77,','44,100,92,61,2,90,28,5,31,30,86,'),('51','69,19,55,','13,1,20,'),('52','12,79,46,6,31,50,69,54,14,39,9,','13,2,28,52,81,98,82,27,48,59,89,'),('53','81,80,74,25,43,26,65,29,57,9,20,','88,91,68,52,92,40,6,58,90,44,47,'),('57','',''),('58','',''),('59','',''),('60','','40,70,17,67,98,37,57,16,86,58,26,'),('61','','65,23,3,82,74,83,66,4,26,63,75,'),('63','99,12,64,54,10,58,14,23,59,26,56,','25,87,33,3,55,66,68,7,76,22,44,'),('64','92,84,1,83,23,24,53,87,91,36,63,','45,64,20,38,48,51,95,78,14,46,93,'),('65','1,4,','30,59,25,96,88,'),('66','62,82,86,54,70,50,','81,76,2,'),('67','77,10,15,','8,47,71,'),('68','46,47,37,38,44,34,66,25,75,28,21,','30,69,53,59,48,14,2,82,60,52,86,'),('78','32,85,11,44,48,55,78,81,57,86,28,','66,25,65,10,17,4,37,93,41,84,82,'),('80','','6,76,56,27,57,42,30,33,48,65,44,'),('81','58,23,77,2,34,4,50,13,97,10,14,','91,43,27,67,1,49,12,19,81,82,73,'),('83','90,56,82,47,69,75,63,72,70,81,15,','12,46,2,92,66,80,1,19,3,57,40,'),('86','20,55,95,61,4,8,40,32,96,81,77,','38,93,22,91,63,67,76,12,80,100,85,'),('90','76,39,15,95,90,46,11,98,29,14,93,85,57,24,41,','66,67,38,4,30,43,86,62,65,96,63,16,34,83,94,'),('91','98,50,19,60,82,54,12,40,4,77,9,72,39,58,68,','13,83,27,32,33,90,65,100,45,34,75,79,56,63,46,'),('92','70,72,6,55,21,22,97,94,48,96,89','2,100,73,33,25,86,23,87,13,17,18'),('93','19,62,89,44,96,34,85,30,29,98,42','100,86,15,81,43,18,65,16,6,23,24'),('94','92,14,71,60,79,22,4,9,34,81,16','41,96,40,87,5,52,75,30,50,70,95'),('95','77,9,88,60,51,17,70,5,99,24,74','36,27,48,87,1,81,7,58,15,73,84');

/*Table structure for table `servernode` */

DROP TABLE IF EXISTS `servernode`;

CREATE TABLE `servernode` (
  `id` int(79) NOT NULL AUTO_INCREMENT,
  `dis` varchar(677) DEFAULT NULL,
  `capacity` varchar(566) DEFAULT NULL,
  `color` varchar(455) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=latin1;

/*Data for the table `servernode` */

insert  into `servernode`(`id`,`dis`,`capacity`,`color`) values (1,'108','268','open color'),(2,'39','179','open color'),(3,'43','213','open color'),(4,'101','251','open color'),(5,'76','266','open color'),(6,'81','231','open color'),(7,'120','300','open color'),(8,'41','221','open color'),(9,'63','213','open color'),(10,'76','226','open color'),(11,'75','225','open color'),(12,'110','260','open color'),(13,'58','198','open color'),(14,'93','223','open color'),(15,'48','238','open color'),(16,'59','249','open color'),(17,'87','237','open color'),(18,'109','299','open color'),(19,'57','197','open color'),(20,'52','232','open color'),(21,'94','334','open color'),(22,'126','286','open color'),(23,'102','262','open color'),(24,'77','207','open color'),(25,'47','177','open color'),(26,'58','268','open color'),(27,'117','317','open color'),(28,'82','262','open color'),(29,'56','236','open color'),(30,'104','224','open color'),(31,'57','257','open color'),(32,'105','235','open color'),(33,'38','198','open color'),(34,'57','187','open color'),(35,'32','232','open color'),(36,'83','273','open color'),(37,'125','325','open color'),(38,'128','288','open color'),(39,'79','249','open color'),(40,'58','198','open color'),(41,'121','321','open color'),(42,'104','274','open color'),(43,'77','257','open color'),(44,'68','188','open color'),(45,'90','290','open color'),(46,'60','210','open color'),(47,'66','236','open color'),(48,'125','265','open color'),(49,'39','269','open color'),(50,'105','245','open color'),(51,'123','303','open color'),(52,'96','246','open color'),(53,'93','293','open color'),(54,'90','300','open color'),(55,'98','288','open color'),(56,'114','274','open color'),(57,'121','241','open color'),(58,'126','246','open color'),(59,'61','201','open color'),(60,'111','311','open color'),(61,'58','228','open color'),(62,'115','305','open color'),(63,'33','183','open color'),(64,'116','326','open color'),(65,'126','286','open color'),(66,'112','262','open color'),(67,'45','235','open color'),(68,'52','232','open color'),(69,'58','258','open color'),(70,'52','202','open color'),(71,'92','272','open color'),(72,'112','312','open color'),(73,'47','237','open color'),(74,'32','242','open color'),(75,'129','289','open color'),(76,'60','220','open color'),(77,'120','280','open color'),(78,'78','248','open color'),(79,'114','284','open color'),(80,'124','274','open color'),(81,'118','238','open color'),(82,'90','210','open color'),(83,'101','281','open color'),(84,'90','250','open color'),(85,'128','278','open color'),(86,'48','168','open color'),(87,'73','950','open color'),(88,'68','248','open color'),(89,'80','300','open color'),(90,'120','250','open color'),(91,'81','271','open color'),(92,'100','280','open color'),(93,'64','244','open color'),(94,'127','327','open color'),(95,'128','328','open color'),(96,'82','222','open color'),(97,'40','6980','open color'),(98,'54','224','open color'),(99,'58','278','open color'),(100,'39','219','open color'),(101,'150','350','open color');

/*Table structure for table `ufile` */

DROP TABLE IF EXISTS `ufile`;

CREATE TABLE `ufile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(788) DEFAULT NULL,
  `fname` varchar(788) DEFAULT NULL,
  `des` varchar(788) DEFAULT NULL,
  `file` varchar(788) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=latin1;

/*Data for the table `ufile` */

insert  into `ufile`(`id`,`uid`,`fname`,`des`,`file`) values (1,'null','Account details','III year fees collection information','F://Files//clone doubt.txt'),(2,'null','clone','DESseses','F://Files//doubtns3withoutfcm.txt'),(3,'null','clone','DESseses','F:ODRwebFilesdoc changes.txt'),(4,'null','clone','DESseses','F:ODRwebFileslicence.txt'),(5,'null','clone','DESseses','F:/ODR/web/Files/adv of aggregate signature .txt'),(6,'null','clone','DESseses','F://ODR//web//Files//adv of aggregate signature .txt'),(7,'null','clone','DESseses','F://ODR//web//Files//adv of aggregate signature .txt'),(8,'null','clone','DESseses','F://ODR//web//Files//Sans I Am Licence.txt'),(9,'siva@gmail.com ','Klmon Filter','It is used for accurately identified the diseases','F://ODR//web//Files//file.txt'),(10,'siva@gmail.com ','kumara','III year fees collection information','F://ODR//web//Files//formulasymbol.txt'),(11,'siva@gmail.com ','bala','DESseses','F://ODR//web//Files//hai.txt'),(12,'siva@gmail.com ','banu','DESseses','F://ODR//web//Files//secrete.txt'),(13,'siva@gmail.com ','Sample','Roughdata','E://ODR//web//Files//input1.txt'),(14,'siva@gmail.com ','Sample','Roughdata','E://ODR//web//Files//malicious.txt'),(15,'siva@gmail.com ','CSP','Roughdata','E://ODR//web//Files//ShoesDB.txt'),(16,'siva@gmail.com ','CSP','Roughdata','E://ODR//web//Files//ShoesDB.txt'),(17,'siva@gmail.com ','CSP','Roughdata','E://ODR//web//Files//ShoesDB.txt'),(18,'siva@gmail.com ','CSP','Book','E://ODR//web//Files//out.txt'),(19,'siva@gmail.com ','CSP','Book','E://ODR//web//Files//out.txt'),(20,'siva@gmail.com ','karunya','Book','E://ODR//web//Files//ShoesDB.txt'),(21,'siva@gmail.com ','gomathi','fgdssgf','E://ODR//web//Files//ShoesDB.txt'),(22,'siva@gmail.com ','gomathi','fgdssgf','E://ODR//web//Files//vg.txt'),(23,'siva@gmail.com ','pons','fgdssgf','E://ODR//web//Files//vg.txt'),(24,'siva@gmail.com ','pons','sdsdf','E://ODR//web//Files//vg.txt'),(25,'siva@gmail.com ','pons','sdsdf','E://ODR//web//Files//vg.txt'),(26,'siva@gmail.com ','pons','sdsdf','E://ODR//web//Files//ShoesDB.txt'),(27,'siva@gmail.com ','knv','hhh','E://ODR//web//Files//vg.txt'),(28,'siva@gmail.com ','dsfdg','wwww','E://ODR//web//Files//out.txt'),(29,'siva@gmail.com ','hjhj','jkjkj','E://ODR//web//Files//vg.txt'),(30,'siva@gmail.com ','fcg','gf','E://ODR//web//Files//input1.txt'),(31,'siva@gmail.com ','fcg','gf','E://ODR//web//Files//out.txt'),(32,'siva@gmail.com ','fcg','gf','E://ODR//web//Files//out.txt'),(33,'siva@gmail.com ','bbn','jjjj','E://ODR//web//Files//vg.txt'),(34,'siva@gmail.com ','hdjghj','hssghdsg','E://ODR//web//Files//output.txt'),(35,'siva@gmail.com ','ggh','fgd','E://ODR//web//Files//secrete.txt'),(36,'siva@gmail.com ','karunya','jjjj','E://ODR//web//Files//ShoesDB.txt'),(37,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//test.txt.txt'),(38,'siva@gmail.com ','student','III year fees collection information','E://ODR//web//Files//test.txt.txt'),(39,'siva@gmail.com ','bala','DESseses','E://ODR//web//Files//test.txt'),(40,'siva@gmail.com ','banu','III year fees collection information','E://ODR//web//Files//test.txt'),(41,'siva@gmail.com ','kumara','It is used for accurately identified the diseases','E://ODR//web//Files//test.txt'),(42,'siva@gmail.com ','banu','III year fees collection information','E://ODR//web//Files//weather.txt'),(43,'siva@gmail.com ','banu','III year fees collection information','E://ODR//web//Files//ii.txt'),(44,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//ii.txt'),(45,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//test.txt'),(46,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//test.txt'),(47,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//test.txt'),(48,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//sonartest.txt'),(49,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//abc.txt'),(50,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//abc.txt'),(51,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//input.txt'),(52,'siva@gmail.com ','banu','III year fees collection information','E://ODR//web//Files//out.txt'),(53,'siva@gmail.com ','banu','DESseses','E://ODR//web//Files//output.txt'),(54,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//out.txt'),(55,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//out.txt'),(56,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//sonar_test.txt'),(57,'siva@gmail.com ','banu','DESseses','E://ODR//web//Files//sonartest.txt'),(58,'siva@gmail.com ','student','III year fees collection information','E://ODR//web//Files//sonar_test.txt'),(59,'siva@gmail.com ','banu','DESseses','E://ODR//web//Files//secrete.txt'),(60,'siva@gmail.com ','student','DESseses','E://ODR//web//Files//test.txt.txt'),(61,'siva@gmail.com ','student','DESseses','E://ODR//web//Files//test.txt.txt'),(62,'siva@gmail.com ','banu','DESseses','E://ODR//web//Files//secrete.txt'),(63,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//test.txt.txt'),(64,'siva@gmail.com ','bala','It is used for accurately identified the diseases','E://ODR//web//Files//sonartest.txt'),(65,'siva@gmail.com ','bala','DESseses','E://ODR//web//Files//weather.txt'),(66,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//test.txt'),(67,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//test.txt'),(68,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//test.txt'),(69,'siva@gmail.com ','banu','DESseses','E://ODR//web//Files//input.txt'),(70,'siva@gmail.com ','banu','DESseses','E://ODR//web//Files//output.txt'),(71,'siva@gmail.com ','bala','DESseses','E://ODR//web//Files//test.txt'),(72,'siva@gmail.com ','bala','DESseses','E://ODR//web//Files//out.txt'),(73,'siva@gmail.com ','banu','III year fees collection information','E://ODR//web//Files//test.txt'),(74,'siva@gmail.com ','bala','DESseses','E://ODR//web//Files//sonartest.txt'),(75,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//weather.txt'),(76,'siva@gmail.com ','kumara','DESseses','E://ODR//web//Files//weather.txt'),(77,'siva@gmail.com ','banu','III year fees collection information','E://ODR//web//Files//weather.txt'),(78,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//weather.txt'),(79,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//secrete.txt'),(80,'siva@gmail.com ','kumara','DESseses','E://ODR//web//Files//test.txt.txt'),(81,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//test.txt.txt'),(82,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//weather.txt'),(83,'siva@gmail.com ','kumara','DESseses','E://ODR//web//Files//weather.txt'),(84,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//weather.txt'),(85,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//weather.txt'),(86,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//referenceurl.txt'),(87,'siva@gmail.com ','kumara','III year fees collection information','E://ODR//web//Files//test.txt.txt'),(88,'siva@gmail.com ','bala','It is used for accurately identified the diseases','E://ODR//web//Files//test.txt.txt'),(89,'siva@gmail.com ','banu','DESseses','E://ODR//web//Files//test.txt.txt'),(90,'siva@gmail.com ','kumara','DESseses','E://ODR//web//Files//test.txt.txt'),(91,'siva@gmail.com ','banu','DESseses','E://ODR//web//Files//test.txt.txt'),(92,'siva@gmail.com ','banu','DESseses','E://ODR//web//Files//test.txt.txt'),(93,'siva@gmail.com ','banu','III year fees collection information','E://ODR//web//Files//test.txt.txt'),(94,'siva@gmail.com ','bala','III year fees collection information','E://ODR//web//Files//test.txt.txt'),(95,'siva@gmail.com ','banu','III year fees collection information','E://ODR//web//Files//weather.txt');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
