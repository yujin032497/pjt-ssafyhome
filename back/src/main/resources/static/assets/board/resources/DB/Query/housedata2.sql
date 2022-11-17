-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
-- Schema housedata2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `housedata2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `housedata2` ;

-- -----------------------------------------------------
-- Table `housedata2`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `housedata2`.`user` (
  `id` VARCHAR(30) NOT NULL,
  `password` VARCHAR(30) NOT NULL,
  `name` VARCHAR(30) NULL,
  `address` VARCHAR(100) NULL,
  `phone` VARCHAR(13) NULL,
  `is_admin` VARCHAR(1) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

--------------------------------------------------------
-- Table `housedata2`.`favorite`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `housedata2`.`favorite` (
  `dongCode_dongCode` VARCHAR(10) NOT NULL,
  `user_id` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`dongCode_dongCode`, `user_id`),
  INDEX `user_favorite_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `user_favorite_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `housedata2`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `dongcode_favorite_fk`
    FOREIGN KEY (`dongCode_dongCode`)
    REFERENCES `housedata2`.`dongcode` (`dongCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `housedata2`.`board`
-- -----------------------------------------------------

DROP TABLE IF EXISTS `housedata2`.`board` ;

CREATE TABLE IF NOT EXISTS `housedata2`.`board` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`),
  INDEX `board_to_members_user_id_fk` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_to_members_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `housedata2`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `housedata2`.`memo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `housedata2`.`memo` ;

CREATE TABLE IF NOT EXISTS `housedata2`.`memo` (
  `memo_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `comment` VARCHAR(500) NULL DEFAULT NULL,
  `memo_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `article_no` INT NULL DEFAULT NULL,
  PRIMARY KEY (`memo_no`),
  INDEX `memo_to_board_article_no_fk` (`article_no` ASC) VISIBLE,
  INDEX `memo_to_member_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `memo_to_board_article_no_fk`
    FOREIGN KEY (`article_no`)
    REFERENCES `housedata2`.`board` (`article_no`),
  CONSTRAINT `memo_to_member_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `housedata2`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;