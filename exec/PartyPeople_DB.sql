-- -----------------------------------------------------
-- Schema partypeople
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `partypeople` DEFAULT CHARACTER SET utf8mb4 ;
USE `partypeople` ;

-- -----------------------------------------------------
-- Table `partypeople`.`room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partypeople`.`room` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  `thumbnail_url` VARCHAR(100) NULL DEFAULT NULL,
  `capacity` INT(11) NOT NULL,
  `password` VARCHAR(10) NULL DEFAULT NULL,
  `start_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP(),
  `end_time` DATETIME NULL DEFAULT NULL,
  `is_locked` TINYINT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `partypeople`.`room_in_memory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partypeople`.`room_in_memory` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NOT NULL,
  `thumbnail_url` VARCHAR(100) NULL DEFAULT NULL,
  `capacity` INT(11) NOT NULL,
  `password` VARCHAR(10) NULL DEFAULT NULL,
  `start_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP(),
  `end_time` DATETIME NULL DEFAULT NULL,
  `is_locked` TINYINT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = MEMORY
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `partypeople`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partypeople`.`tag` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tag_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `partypeople`.`room_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partypeople`.`room_tag` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `room_id` INT(10) UNSIGNED NOT NULL,
  `tag_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `room_id`, `tag_id`),
  INDEX `fk_roomtag_room_id` (`room_id` ASC),
  INDEX `fk_roomtag_tag_id` (`tag_id` ASC),
  CONSTRAINT `fk_roomtag_room_id`
    FOREIGN KEY (`room_id`)
    REFERENCES `partypeople`.`room` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_roomtag_tag_id`
    FOREIGN KEY (`tag_id`)
    REFERENCES `partypeople`.`tag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `partypeople`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partypeople`.`user` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account_id` VARCHAR(45) NOT NULL,
  `nickname` VARCHAR(45) NOT NULL,
  `password` CHAR(60) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `tel` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `account_id_UNIQUE` (`account_id` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `partypeople`.`session`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partypeople`.`session` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` INT(10) UNSIGNED NOT NULL,
  `room_id` INT(10) UNSIGNED NOT NULL,
  `start_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  `end_time` DATETIME NULL DEFAULT NULL,
  `is_host` TINYINT(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`, `user_id`, `room_id`),
  INDEX `session_fk_room_id_room_id` (`room_id` ASC),
  INDEX `session_fk_user_id_user_id` (`user_id` ASC),
  CONSTRAINT `session_fk_room_id_room_id`
    FOREIGN KEY (`room_id`)
    REFERENCES `partypeople`.`room` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `session_fk_user_id_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `partypeople`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `partypeople`.`tag_in_memory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partypeople`.`tag_in_memory` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tag_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = MEMORY
DEFAULT CHARACTER SET = utf8mb4;


USE mysql;
CREATE USER 'a106'@'%' IDENTIFIED BY 'a106ssafy0183';
GRANT SELECT, INSERT, UPDATE ON partypeople.* to 'a106'@'%' IDENTIFIED BY 'a106ssafy0183';
FLUSH PRIVILEGES;