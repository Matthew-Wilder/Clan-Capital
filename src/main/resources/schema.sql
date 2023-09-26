CREATE DATABASE IF NOT EXISTS clan_capital;

USE clan_capital;

-- All districts
CREATE TABLE IF NOT EXISTS `district_enum`
(
	`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE IF NOT EXISTS `building_category`
(
    `id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL UNIQUE
);

-- All buildings
CREATE TABLE IF NOT EXISTS `building_enum`
(
	`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(100) NOT NULL UNIQUE,
	`category_id` INT NOT NULL,
    FOREIGN KEY (`category_id`) REFERENCES `building_category`(`id`) ON DELETE NO ACTION
);

-- All building upgrades with level and cost
CREATE TABLE IF NOT EXISTS `building_upgrade`
(
	`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	`building_enum_id` INT NOT NULL,
	`level` INT NOT NULL,
	`cost` INT NOT NULL DEFAULT 0,
	UNIQUE (`building_enum_id`, `level`),
	FOREIGN KEY (`building_enum_id`) REFERENCES `building_enum`(`id`) ON DELETE NO ACTION
);

-- Each building instance gets a current state represented by a level
CREATE TABLE IF NOT EXISTS `building_instance`
(
	`id` INT PRIMARY KEY NOT NULL AUTO_INCREMENT, -- Which specific building instance
	`district_enum_id` INT NOT NULL, -- What district
    `building_enum_id` INT NOT NULL, -- What building
	`building_upgrade_id` INT NOT NULL, -- What level
	FOREIGN KEY (`district_enum_id`) REFERENCES `district_enum`(`id`) ON DELETE NO ACTION,
	FOREIGN KEY (`building_enum_id`) REFERENCES `building_enum`(`id`) ON DELETE NO ACTION,
    FOREIGN KEY (`building_upgrade_id`) REFERENCES `building_upgrade`(`id`) ON DELETE NO ACTION
);

-- How many of each building can be built in a district
CREATE TABLE IF NOT EXISTS `district_building_limit`
(
	`district_enum_id` INT NOT NULL,
	`building_enum_id` INT NOT NULL,
	`maximum_amount` INT NOT NULL DEFAULT 0,
	UNIQUE KEY (`district_enum_id`,`building_enum_id`),
	FOREIGN KEY (`district_enum_id`) REFERENCES `district_enum`(`id`) ON DELETE NO ACTION,
	FOREIGN KEY (`building_enum_id`) REFERENCES `building_enum`(`id`) ON DELETE NO ACTION
);
