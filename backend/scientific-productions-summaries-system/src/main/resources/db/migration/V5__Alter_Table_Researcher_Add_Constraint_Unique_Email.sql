ALTER TABLE `researcher` DROP INDEX `unique_name`;
ALTER TABLE `researcher` ADD CONSTRAINT `unique_email` UNIQUE (`email`);