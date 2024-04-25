CREATE TABLE IF NOT EXISTS `work` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` varchar(255) NOT NULL,
  `year` int NOT NULL,
  `place` varchar(255),
  `work_type` varchar(30) NOT NULL,
  `researcher_id` bigint NOT NULL,

    FOREIGN KEY (`researcher_id`) REFERENCES `researcher`(`id`)
);
