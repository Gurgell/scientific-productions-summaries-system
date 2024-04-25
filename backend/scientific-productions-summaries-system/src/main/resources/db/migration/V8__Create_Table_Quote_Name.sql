CREATE TABLE IF NOT EXISTS `quote_name` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255) NOT NULL,
  `work_id` bigint NOT NULL,

  FOREIGN KEY (`work_id`) REFERENCES `work`(`id`)
);
