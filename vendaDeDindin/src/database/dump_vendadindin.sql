-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: vendadindin
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.28-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `dindin`
--

CREATE DATABASE IF NOT EXISTS vendadindin;
use vendadindin;

DROP TABLE IF EXISTS `dindin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dindin` (
  `sabor` varchar(70) NOT NULL,
  `custo` double(4,2) unsigned DEFAULT NULL,
  `valor` double(4,2) unsigned DEFAULT NULL,
  `quantidadeEstoque` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`sabor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dindin`
--

LOCK TABLES `dindin` WRITE;
/*!40000 ALTER TABLE `dindin` DISABLE KEYS */;
INSERT INTO `dindin` VALUES ('abacate',0.76,3.50,9),('chocolate',0.55,2.00,13),('coco',0.39,2.00,19),('maracujá',0.72,3.00,18),('morango',0.75,3.00,20),('uva',0.53,2.50,10);
/*!40000 ALTER TABLE `dindin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dindinvendido`
--

DROP TABLE IF EXISTS `dindinvendido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dindinvendido` (
  `idVenda` int(10) unsigned NOT NULL,
  `saborDindin` varchar(70) NOT NULL,
  `quantidade` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`idVenda`,`saborDindin`),
  KEY `saborDindin` (`saborDindin`),
  CONSTRAINT `dindinvendido_ibfk_1` FOREIGN KEY (`idVenda`) REFERENCES `venda` (`idVenda`),
  CONSTRAINT `dindinvendido_ibfk_2` FOREIGN KEY (`saborDindin`) REFERENCES `dindin` (`sabor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dindinvendido`
--

LOCK TABLES `dindinvendido` WRITE;
/*!40000 ALTER TABLE `dindinvendido` DISABLE KEYS */;
INSERT INTO `dindinvendido` VALUES (1,'abacate',1),(1,'chocolate',2),(1,'coco',6),(1,'maracujá',3),(1,'morango',3),(2,'abacate',2),(2,'coco',5),(2,'uva',5),(3,'uva',1),(4,'coco',5),(5,'morango',1),(6,'morango',1),(7,'morango',4),(8,'chocolate',2),(8,'maracujá',5),(8,'uva',2),(9,'uva',11),(10,'abacate',1),(11,'morango',10);
/*!40000 ALTER TABLE `dindinvendido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venda`
--

DROP TABLE IF EXISTS `venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venda` (
  `idVenda` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `valorTotal` double(10,2) unsigned DEFAULT NULL,
  `desconto` double(10,2) unsigned DEFAULT NULL,
  `dataVenda` date DEFAULT NULL,
  `estado` enum('operante','indeferida') DEFAULT 'operante',
  PRIMARY KEY (`idVenda`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venda`
--

LOCK TABLES `venda` WRITE;
/*!40000 ALTER TABLE `venda` DISABLE KEYS */;
INSERT INTO `venda` VALUES (1,37.50,0.00,'2023-12-17','operante'),(2,29.50,0.00,'2023-12-17','indeferida'),(3,2.50,0.00,'2023-12-17','operante'),(4,10.00,0.00,'2023-12-17','operante'),(5,3.00,0.00,'2023-12-17','indeferida'),(6,3.00,0.00,'2023-12-17','operante'),(7,12.00,0.00,'2023-12-17','operante'),(8,24.00,0.00,'2023-12-17','operante'),(9,27.50,3.00,'2023-12-17','operante'),(10,3.50,0.50,'2023-12-17','operante'),(11,30.00,0.00,'2023-12-17','operante');
/*!40000 ALTER TABLE `venda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'vendadindin'
--

--
-- Dumping routines for database 'vendadindin'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-18  1:29:26
