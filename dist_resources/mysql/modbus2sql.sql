GRANT ALL PRIVILEGES ON *.* TO 'modbus2sql'@'localhost' IDENTIFIED BY 'modicon' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON *.* TO 'modbus2sql'@'%' IDENTIFIED BY 'modicon' WITH GRANT OPTION;
FLUSH PRIVILEGES;
CREATE DATABASE modbus2sql;
USE modbus2sql;

-- MySQL dump 10.13  Distrib 5.1.47, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: modbus2sql
-- ------------------------------------------------------
-- Server version	5.1.47

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
-- Table structure for table `alarmannunciator`
--

DROP TABLE IF EXISTS `alarmannunciator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alarmannunciator` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `warnregister` int(11) NOT NULL DEFAULT '0',
  `warnregisterbit` int(11) NOT NULL DEFAULT '0',
  `shutdownregister` int(11) NOT NULL,
  `shutdownregisterbit` int(11) NOT NULL,
  `tripregister` int(11) NOT NULL,
  `tripregisterbit` int(11) NOT NULL,
  `slaveID` int(11) NOT NULL,
  `sendresetflag` int(11) NOT NULL,
  `sendresetreg` int(11) NOT NULL,
  `sendresetbit` int(11) NOT NULL,
  `recvresetflag` int(11) NOT NULL,
  `recvresetreg` int(11) NOT NULL,
  `recvresetbit` int(11) NOT NULL,
  `hmiresetflag` int(11) NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarmannunciator`
--

LOCK TABLES `alarmannunciator` WRITE;
/*!40000 ALTER TABLE `alarmannunciator` DISABLE KEYS */;
/*!40000 ALTER TABLE `alarmannunciator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alarmflags`
--

DROP TABLE IF EXISTS `alarmflags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alarmflags` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `controllertype` varchar(30) NOT NULL,
  `warningregister` int(11) NOT NULL,
  `warningbit` int(11) NOT NULL,
  `shutdownregister` int(11) NOT NULL,
  `shutdownbit` int(11) NOT NULL,
  `tripregister` int(11) NOT NULL,
  `tripbit` int(11) NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarmflags`
--

LOCK TABLES `alarmflags` WRITE;
/*!40000 ALTER TABLE `alarmflags` DISABLE KEYS */;
/*!40000 ALTER TABLE `alarmflags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alarmlog`
--

DROP TABLE IF EXISTS `alarmlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alarmlog` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `curdate` date NOT NULL,
  `curtime` time NOT NULL,
  `slaveid` int(11) NOT NULL,
  `slavename` varchar(30) NOT NULL,
  `alarmdesc` varchar(256) NOT NULL,
  `alarmtype` varchar(20) NOT NULL,
  `alarmacknowledged` tinyint(4) NOT NULL,
  `alarmreset` tinyint(4) NOT NULL,
  `alarmnotifications` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarmlog`
--

LOCK TABLES `alarmlog` WRITE;
/*!40000 ALTER TABLE `alarmlog` DISABLE KEYS */;
/*!40000 ALTER TABLE `alarmlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alarmtypes`
--

DROP TABLE IF EXISTS `alarmtypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `alarmtypes` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `controllertype` varchar(60) NOT NULL,
  `alarmstring` varchar(256) NOT NULL,
  `modbusregister` int(11) NOT NULL,
  `startbit` int(11) NOT NULL,
  `bitqty` int(11) NOT NULL,
  `modbusregisterdescription` int(11) NOT NULL,
  `registerqtydescription` int(11) NOT NULL,
  `valuedisabled` int(11) NOT NULL,
  `valuehealthy` int(11) NOT NULL,
  `valuewarning` int(11) NOT NULL,
  `valueshutdown` int(11) NOT NULL,
  `valuetrip` int(11) NOT NULL,
  `valueunimplemented` int(11) NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM AUTO_INCREMENT=87 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarmtypes`
--

LOCK TABLES `alarmtypes` WRITE;
/*!40000 ALTER TABLE `alarmtypes` DISABLE KEYS */;
/*!40000 ALTER TABLE `alarmtypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `controllertypes`
--

DROP TABLE IF EXISTS `controllertypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `controllertypes` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `controllertype` varchar(30) NOT NULL,
  `longname` varchar(150) NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `controllertypes`
--

LOCK TABLES `controllertypes` WRITE;
/*!40000 ALTER TABLE `controllertypes` DISABLE KEYS */;
/*!40000 ALTER TABLE `controllertypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registerblocks`
--

DROP TABLE IF EXISTS `registerblocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registerblocks` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `controllertype` varchar(50) NOT NULL,
  `page` int(11) DEFAULT NULL,
  `registerstart` int(11) DEFAULT NULL,
  `registerend` int(11) DEFAULT NULL,
  `description` varchar(250) DEFAULT NULL,
  `modbusFunctionType` int(11) NOT NULL DEFAULT '3',
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registerblocks`
--

LOCK TABLES `registerblocks` WRITE;
/*!40000 ALTER TABLE `registerblocks` DISABLE KEYS */;
/*!40000 ALTER TABLE `registerblocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registerdetail`
--

DROP TABLE IF EXISTS `registerdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registerdetail` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT COMMENT 'AUTO CREATES',
  `controllertype` varchar(50) NOT NULL COMMENT 'Longtext name for controller',
  `page` int(11) DEFAULT NULL COMMENT 'Your Page Number for this Data Block',
  `register` int(11) DEFAULT NULL COMMENT 'Modbus Register No',
  `description` varchar(50) DEFAULT NULL COMMENT 'Long text desctription',
  `bits` int(11) DEFAULT '16' COMMENT '16 or 32',
  `signed` tinyint(1) DEFAULT '0' COMMENT 'Set =1 for Signed Integer',
  `lowbyteregister` int(11) NOT NULL DEFAULT '0' COMMENT 'Contains the Register for the High Bytes in a 32 bit data',
  `scalingfactor` double DEFAULT '1' COMMENT 'Factor to multiply answer to get real value',
  `minimum` double DEFAULT NULL COMMENT 'Minimum Value',
  `maximum` double DEFAULT NULL COMMENT 'Maximum Value',
  `units` varchar(50) DEFAULT NULL COMMENT 'String representation of units eg %, deg C etc',
  `writeable` tinyint(1) DEFAULT NULL COMMENT 'Set=1 for Modbus READ_COILS',
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM AUTO_INCREMENT=1153 DEFAULT CHARSET=latin1 COMMENT='This table stores the register information';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registerdetail`
--

LOCK TABLES `registerdetail` WRITE;
/*!40000 ALTER TABLE `registerdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `registerdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rs485settings`
--

DROP TABLE IF EXISTS `rs485settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rs485settings` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `serialport` varchar(50) NOT NULL,
  `baud` int(11) NOT NULL,
  `parity` varchar(5) NOT NULL,
  `databits` int(11) NOT NULL,
  `stopbits` int(11) NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rs485settings`
--

LOCK TABLES `rs485settings` WRITE;
/*!40000 ALTER TABLE `rs485settings` DISABLE KEYS */;
INSERT INTO `rs485settings` VALUES (1,'/dev/ttyUSB1',115200,'None',8,2);
/*!40000 ALTER TABLE `rs485settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `siteinfo`
--

DROP TABLE IF EXISTS `siteinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `siteinfo` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `sitename` varchar(50) DEFAULT NULL,
  `comments` longtext,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `siteinfo`
--

LOCK TABLES `siteinfo` WRITE;
/*!40000 ALTER TABLE `siteinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `siteinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slaves`
--

DROP TABLE IF EXISTS `slaves`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `slaves` (
  `rowid` int(11) NOT NULL AUTO_INCREMENT,
  `modbusslaveid` int(11) DEFAULT NULL,
  `longname` varchar(150) DEFAULT NULL,
  `controllertype` varchar(15) NOT NULL,
  PRIMARY KEY (`rowid`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slaves`
--

LOCK TABLES `slaves` WRITE;
/*!40000 ALTER TABLE `slaves` DISABLE KEYS */;
/*!40000 ALTER TABLE `slaves` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-02-26 16:19:42