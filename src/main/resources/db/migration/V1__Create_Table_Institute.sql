CREATE TABLE IF NOT EXISTS `institute` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `acronym` varchar(255) NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`name`)
);
