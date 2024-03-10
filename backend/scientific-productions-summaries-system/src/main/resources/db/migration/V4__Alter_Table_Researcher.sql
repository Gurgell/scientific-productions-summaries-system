ALTER TABLE  `researcher`
    ADD COLUMN `institute_id` bigint,
    ADD CONSTRAINT `fk_institute_id`
    FOREIGN KEY (`institute_id`)
    REFERENCES `institute`(`id`);