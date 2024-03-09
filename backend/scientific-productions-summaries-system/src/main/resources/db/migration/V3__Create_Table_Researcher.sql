CREATE TABLE IF NOT EXISTS `researcher` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`)
);
