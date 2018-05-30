-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: iotirebouchon
-- ------------------------------------------------------
-- Server version	5.7.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `vol`
--

DROP TABLE IF EXISTS `vol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vol` (
  `idVol` int(11) NOT NULL AUTO_INCREMENT,
  `nomVol` varchar(45) DEFAULT NULL,
  `nomPilote` varchar(45) DEFAULT NULL,
  `dateVol` date DEFAULT NULL,
  `dureDuVol` double DEFAULT NULL,
  `batterieLife` int(11) DEFAULT NULL,
  `puissanceMoteur` int(11) DEFAULT NULL,
  PRIMARY KEY (`idVol`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vol`
--

LOCK TABLES `vol` WRITE;
/*!40000 ALTER TABLE `vol` DISABLE KEYS */;
INSERT INTO `vol` VALUES (1,'test','tesPilote ','2015-04-24',2622,50,48),(2,'test','tes','2018-04-12',0,0,0),(3,'test','test','2018-04-20',0,0,0),(4,'test','tet','2018-04-05',0,0,0),(5,'tet','tet','2018-04-20',0,0,0),(6,'ett','tet','2018-03-28',0,0,0),(7,'tet','tet','2018-04-20',0,0,0),(8,'surbeaulie','thomas','2018-04-04',0,0,0),(9,'testNomVol','testNomPilote','2018-05-03',0,0,0),(10,'1er vol','blabla','2018-05-25',0,0,0),(11,'biteeeeeeedecedce','etet','2018-05-15',0,0,0),(12,'MAP DE TEST','zopok','2018-05-09',0,0,0);
/*!40000 ALTER TABLE `vol` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-22 20:57:24
