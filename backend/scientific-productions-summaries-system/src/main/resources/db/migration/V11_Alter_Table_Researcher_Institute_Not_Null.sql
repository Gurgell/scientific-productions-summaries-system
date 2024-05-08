ALTER TABLE researcher MODIFY COLUMN institute_id bigint NOT NULL
    ADD CONSTRAINT `fk_institute_id`
    FOREIGN KEY (`institute_id`)
    REFERENCES `institute`(`id`);