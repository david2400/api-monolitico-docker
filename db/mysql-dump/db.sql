CREATE DATABASE  IF NOT EXISTS `pragmaApi` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pragmaApi`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: pragmaApi   Server: 127.0.0.1
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(254) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qsstlki7ni5ovaariyy9u8y79` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,NULL,'Bogotá'),(2,NULL,'Medellín'),(3,NULL,'Cali'),(4,NULL,'Barranquilla'),(5,NULL,'Cartagena de Indias'),(6,NULL,'Soacha'),(7,NULL,'Cúcuta'),(8,NULL,'Soledad'),(9,NULL,'Bucaramanga'),(10,NULL,'Bello'),(11,NULL,'Armenia'),(12,NULL,'Pereira'),(13,NULL,'Buga'),(14,NULL,'Villavicencio'),(15,NULL,'Ibagué'),(16,NULL,'Santa Marta'),(17,NULL,'Valledupar'),(18,NULL,'Manizales'),(19,NULL,'Montería'),(20,NULL,'Neiva'),(21,NULL,'Pasto');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `age` varchar(3) NOT NULL,
  `identification` varchar(20) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `mail` varchar(100) NOT NULL,
  `name` varchar(45) NOT NULL,
  `id_city` bigint DEFAULT NULL,
  `id_identification_type` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_powwvjq5dtrded35jufhbmcsd` (`identification`),
  UNIQUE KEY `UK_sl0fhqdec6m4pbqb5qaqk43qk` (`mail`),
  KEY `FKno3ipwh8byidyggyq38jm1o1k` (`id_city`),
  KEY `FK89gyvo16dm3hvhchr7qkae03` (`id_identification_type`),
  CONSTRAINT `FK89gyvo16dm3hvhchr7qkae03` FOREIGN KEY (`id_identification_type`) REFERENCES `identification_type` (`id`),
  CONSTRAINT `FKno3ipwh8byidyggyq38jm1o1k` FOREIGN KEY (`id_city`) REFERENCES `city` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'20','1007730758','Posada','santi.posada.1321@gmail.com','Santiago',4,1),(5,'18','7544031','rodriguez','sebastian@gmail.com','sebastian',1,1),(10,'48','1234567','hurtado','margarita@gmail.com','margarita',9,1),(11,'87','123456-12345','ramirez','pedro@gmail.com','pedro',1,2),(14,'40','7439567','hurtado','jose@gmail.com','jose',8,1),(17,'20','7514671','guitierrez','stefsa@gmail.com','estefania',1,1);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `identification_type`
--

DROP TABLE IF EXISTS `identification_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `identification_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(254) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ikexgbwwmavc02oa8k3rp8ucd` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `identification_type`
--

LOCK TABLES `identification_type` WRITE;
/*!40000 ALTER TABLE `identification_type` DISABLE KEYS */;
INSERT INTO `identification_type` VALUES (1,NULL,'Cédula de Ciudadania'),(2,NULL,'Tarjeta de Identidad'),(3,NULL,'Cédula Digital');
/*!40000 ALTER TABLE `identification_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-30 11:22:18
