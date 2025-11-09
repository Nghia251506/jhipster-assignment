-- MySQL dump 10.13  Distrib 8.0.44, for macos15 (x86_64)
--
-- Host: localhost    Database: news_crawler
-- ------------------------------------------------------
-- Server version	8.4.7

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
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `tenant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_app_user__user_id` (`user_id`),
  KEY `fk_app_user__tenant_id` (`tenant_id`),
  CONSTRAINT `fk_app_user__tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`id`),
  CONSTRAINT `fk_app_user__user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1502 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (1500,'Nguyễn Trọng Nghĩa',1,1500),(1501,'Nguyễn Trọng Nghĩa 2',2,1500);
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` longtext COLLATE utf8mb4_unicode_ci,
  `is_active` tinyint NOT NULL,
  `tenant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category__tenant_id` (`tenant_id`),
  CONSTRAINT `fk_category__tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1501 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1500,'tech','Công Nghệ','acb',1,1500);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crawl_log`
--

DROP TABLE IF EXISTS `crawl_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crawl_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `error_message` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `crawled_at` datetime(6) NOT NULL,
  `tenant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_crawl_log__tenant_id` (`tenant_id`),
  CONSTRAINT `fk_crawl_log__tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crawl_log`
--

LOCK TABLES `crawl_log` WRITE;
/*!40000 ALTER TABLE `crawl_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `crawl_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `AUTHOR` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `FILENAME` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int NOT NULL,
  `EXECTYPE` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MD5SUM` varchar(35) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `COMMENTS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TAG` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `LIQUIBASE` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CONTEXTS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `LABELS` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2025-11-09 18:41:54',1,'EXECUTED','9:8ae73e58f4f46b4fcac75f9b9e68f7d6','createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...','',NULL,'4.29.2',NULL,NULL,'2688514516'),('20251109114425-1','jhipster','config/liquibase/changelog/20251109114425_added_entity_Tenant.xml','2025-11-09 18:45:51',2,'EXECUTED','9:6490f8ab3476e72a09e30efbe0cb53a5','createTable tableName=tenant','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114426-1','jhipster','config/liquibase/changelog/20251109114426_added_entity_AppUser.xml','2025-11-09 18:45:51',3,'EXECUTED','9:981818672c01465ee83fd726a3ed37b3','createTable tableName=app_user','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114427-1','jhipster','config/liquibase/changelog/20251109114427_added_entity_Category.xml','2025-11-09 18:45:51',4,'EXECUTED','9:9919107772cb2e5389d0fcd039851612','createTable tableName=category','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114428-1','jhipster','config/liquibase/changelog/20251109114428_added_entity_Source.xml','2025-11-09 18:45:51',5,'EXECUTED','9:b479d483c5ea386fa971c7e43e791186','createTable tableName=source','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114429-1','jhipster','config/liquibase/changelog/20251109114429_added_entity_Post.xml','2025-11-09 18:45:51',6,'EXECUTED','9:43bc4e15cddd860cdafec2b4e895da43','createTable tableName=post; dropDefaultValue columnName=published_at, tableName=post','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114429-1-relations','jhipster','config/liquibase/changelog/20251109114429_added_entity_Post.xml','2025-11-09 18:45:51',7,'EXECUTED','9:81eb1ff393c1e967e91b8d592d10c959','createTable tableName=rel_post__tags; addPrimaryKey tableName=rel_post__tags','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114430-1','jhipster','config/liquibase/changelog/20251109114430_added_entity_Tag.xml','2025-11-09 18:45:51',8,'EXECUTED','9:dd72cbd1bc88cd4fb47f78d4005a4d35','createTable tableName=tag','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114431-1','jhipster','config/liquibase/changelog/20251109114431_added_entity_SiteSetting.xml','2025-11-09 18:45:51',9,'EXECUTED','9:1200b799c8549a332972914f6383b837','createTable tableName=site_setting','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114432-1','jhipster','config/liquibase/changelog/20251109114432_added_entity_CrawlLog.xml','2025-11-09 18:45:51',10,'EXECUTED','9:99ec337f2ad3dbd90370c3dc098dac39','createTable tableName=crawl_log; dropDefaultValue columnName=crawled_at, tableName=crawl_log','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114426-2','jhipster','config/liquibase/changelog/20251109114426_added_entity_constraints_AppUser.xml','2025-11-09 18:45:51',11,'EXECUTED','9:d37322b1d52d66ff6569447e285b58ee','addForeignKeyConstraint baseTableName=app_user, constraintName=fk_app_user__user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=app_user, constraintName=fk_app_user__tenant_id, referencedTableName=tenant','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114427-2','jhipster','config/liquibase/changelog/20251109114427_added_entity_constraints_Category.xml','2025-11-09 18:45:51',12,'EXECUTED','9:e4c5f83da63674832987fe080975c222','addForeignKeyConstraint baseTableName=category, constraintName=fk_category__tenant_id, referencedTableName=tenant','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114428-2','jhipster','config/liquibase/changelog/20251109114428_added_entity_constraints_Source.xml','2025-11-09 18:45:51',13,'EXECUTED','9:37b14a64664b33e2215c9b97850e7180','addForeignKeyConstraint baseTableName=source, constraintName=fk_source__tenant_id, referencedTableName=tenant; addForeignKeyConstraint baseTableName=source, constraintName=fk_source__category_id, referencedTableName=category','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114429-2','jhipster','config/liquibase/changelog/20251109114429_added_entity_constraints_Post.xml','2025-11-09 18:45:51',14,'EXECUTED','9:1cd0627f92090ab822eba8fa238a2782','addForeignKeyConstraint baseTableName=post, constraintName=fk_post__tenant_id, referencedTableName=tenant; addForeignKeyConstraint baseTableName=post, constraintName=fk_post__source_id, referencedTableName=source; addForeignKeyConstraint baseTable...','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114430-2','jhipster','config/liquibase/changelog/20251109114430_added_entity_constraints_Tag.xml','2025-11-09 18:45:51',15,'EXECUTED','9:7b93597133d6f24a58af42dba1b9ac14','addForeignKeyConstraint baseTableName=tag, constraintName=fk_tag__tenant_id, referencedTableName=tenant','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114431-2','jhipster','config/liquibase/changelog/20251109114431_added_entity_constraints_SiteSetting.xml','2025-11-09 18:45:51',16,'EXECUTED','9:85bc8ef63e5e6cd0845c24d19d7ed0bd','addForeignKeyConstraint baseTableName=site_setting, constraintName=fk_site_setting__tenant_id, referencedTableName=tenant','',NULL,'4.29.2',NULL,NULL,'2688751303'),('20251109114432-2','jhipster','config/liquibase/changelog/20251109114432_added_entity_constraints_CrawlLog.xml','2025-11-09 18:45:52',17,'EXECUTED','9:4bc0db99182d6aad6c9173ccebf2a9b8','addForeignKeyConstraint baseTableName=crawl_log, constraintName=fk_crawl_log__tenant_id, referencedTableName=tenant','',NULL,'4.29.2',NULL,NULL,'2688751303');
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` tinyint NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,0,NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_authority`
--

DROP TABLE IF EXISTS `jhi_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_authority` (
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_authority`
--

LOCK TABLES `jhi_authority` WRITE;
/*!40000 ALTER TABLE `jhi_authority` DISABLE KEYS */;
INSERT INTO `jhi_authority` VALUES ('ROLE_ADMIN'),('ROLE_USER');
/*!40000 ALTER TABLE `jhi_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user`
--

DROP TABLE IF EXISTS `jhi_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password_hash` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image_url` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `activated` tinyint NOT NULL,
  `lang_key` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `activation_key` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `reset_key` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_by` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_user_login` (`login`),
  UNIQUE KEY `ux_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1050 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user`
--

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` VALUES (1,'admin','$2a$10$xCGxuc0rtYUdeVe5CnRKreu13RFBHS9.dFdiVzVeNfxzak9oQ.ofe','Administrator','Administrator','admin@localhost','',1,'vi',NULL,NULL,'system',NULL,NULL,'admin','2025-11-09 05:42:10'),(2,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','',1,'vi',NULL,NULL,'system',NULL,NULL,'system',NULL);
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jhi_user_authority`
--

DROP TABLE IF EXISTS `jhi_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `jhi_user_authority` (
  `user_id` bigint NOT NULL,
  `authority_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jhi_user_authority`
--

LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` VALUES (1,'ROLE_ADMIN'),(1,'ROLE_USER'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `origin_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `slug` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `summary` longtext COLLATE utf8mb4_unicode_ci,
  `content` longtext COLLATE utf8mb4_unicode_ci,
  `content_raw` longtext COLLATE utf8mb4_unicode_ci,
  `thumbnail` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `published_at` datetime(6),
  `view_count` bigint NOT NULL,
  `tenant_id` bigint DEFAULT NULL,
  `source_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_post__tenant_id` (`tenant_id`),
  KEY `fk_post__source_id` (`source_id`),
  KEY `fk_post__category_id` (`category_id`),
  CONSTRAINT `fk_post__category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_post__source_id` FOREIGN KEY (`source_id`) REFERENCES `source` (`id`),
  CONSTRAINT `fk_post__tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1502 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` VALUES (1500,'https://example.com/bai-viet-1','Bài viết demo đầu tiên','bai-viet-demo-dau-tien','Tóm tắt ngắn gọn...','Nội dung bài viết demo cho client.','','','PUBLISHED','2025-11-09 01:00:00.000000',0,1500,NULL,1500),(1501,'https://example.com/bai-viet-1','Bài Viết Demo','bai-viet-demo','abc','Content cho client thay','',NULL,'PUBLISHED','2025-11-08 17:00:00.000000',10,1500,NULL,1500);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rel_post__tags`
--

DROP TABLE IF EXISTS `rel_post__tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rel_post__tags` (
  `tags_id` bigint NOT NULL,
  `post_id` bigint NOT NULL,
  PRIMARY KEY (`post_id`,`tags_id`),
  KEY `fk_rel_post__tags__tags_id` (`tags_id`),
  CONSTRAINT `fk_rel_post__tags__post_id` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `fk_rel_post__tags__tags_id` FOREIGN KEY (`tags_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rel_post__tags`
--

LOCK TABLES `rel_post__tags` WRITE;
/*!40000 ALTER TABLE `rel_post__tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `rel_post__tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `site_setting`
--

DROP TABLE IF EXISTS `site_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `site_setting` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `site_title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `site_description` longtext COLLATE utf8mb4_unicode_ci,
  `site_keywords` longtext COLLATE utf8mb4_unicode_ci,
  `ga_tracking_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `banner_top` longtext COLLATE utf8mb4_unicode_ci,
  `banner_sidebar` longtext COLLATE utf8mb4_unicode_ci,
  `tenant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_site_setting__tenant_id` (`tenant_id`),
  CONSTRAINT `fk_site_setting__tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `site_setting`
--

LOCK TABLES `site_setting` WRITE;
/*!40000 ALTER TABLE `site_setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `site_setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `source`
--

DROP TABLE IF EXISTS `source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `source` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `base_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `list_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `list_item_selector` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `link_attr` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `title_selector` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content_selector` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `thumbnail_selector` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `author_selector` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint NOT NULL,
  `note` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_source__tenant_id` (`tenant_id`),
  KEY `fk_source__category_id` (`category_id`),
  CONSTRAINT `fk_source__category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_source__tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `source`
--

LOCK TABLES `source` WRITE;
/*!40000 ALTER TABLE `source` DISABLE KEYS */;
/*!40000 ALTER TABLE `source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `slug` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tenant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_tag__tenant_id` (`tenant_id`),
  CONSTRAINT `fk_tag__tenant_id` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenant`
--

DROP TABLE IF EXISTS `tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tenant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `contact_email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contact_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `max_users` int NOT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_tenant__code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1501 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenant`
--

LOCK TABLES `tenant` WRITE;
/*!40000 ALTER TABLE `tenant` DISABLE KEYS */;
INSERT INTO `tenant` VALUES (1500,'TNS','Trọng Nghĩa Software','ntn8530@gmail.com','0862273012',5,'ACTIVE');
/*!40000 ALTER TABLE `tenant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'news_crawler'
--

--
-- Dumping routines for database 'news_crawler'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-09 20:57:18
