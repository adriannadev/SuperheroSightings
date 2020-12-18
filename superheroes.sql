-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema superheroesdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `superheroesdb` ;

-- -----------------------------------------------------
-- Schema superheroesdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `superheroesdb` DEFAULT CHARACTER SET utf8 ;
USE `superheroesdb` ;

-- -----------------------------------------------------
-- Table `superheroesdb`.`superhero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroesdb`.`superhero` (
  `idsuperhero` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `superpower` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idsuperhero`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroesdb`.`location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroesdb`.`location` (
  `idlocation` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `address` VARCHAR(100) NOT NULL,
  `latitude` DECIMAL(8,6) NOT NULL,
  `longitude` DECIMAL(9,6) NOT NULL,
  PRIMARY KEY (`idlocation`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroesdb`.`organisation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroesdb`.`organisation` (
  `idorganisation` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(255) NULL,
  `address` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(11) NOT NULL,
  PRIMARY KEY (`idorganisation`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroesdb`.`organisationSuperhero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroesdb`.`organisationSuperhero` (
  `idorganisation` INT NOT NULL,
  `idsuperhero` INT NOT NULL,
  PRIMARY KEY (`idorganisation`, `idsuperhero`),
  INDEX `fk_organisation_has_superhero_superhero1_idx` (`idsuperhero` ASC) VISIBLE,
  INDEX `fk_organisation_has_superhero_organisation_idx` (`idorganisation` ASC) VISIBLE,
  CONSTRAINT `fk_organisation_has_superhero_organisation`
    FOREIGN KEY (`idorganisation`)
    REFERENCES `superheroesdb`.`organisation` (`idorganisation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisation_has_superhero_superhero1`
    FOREIGN KEY (`idsuperhero`)
    REFERENCES `superheroesdb`.`superhero` (`idsuperhero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `superheroesdb`.`sighting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `superheroesdb`.`sighting` (
  `idsighting` INT NOT NULL AUTO_INCREMENT,
  `idsuperhero` INT NOT NULL,
  `idlocation` INT NOT NULL,
  `date` DATE NOT NULL,
  INDEX `fk_superhero_has_location_location1_idx` (`idlocation` ASC) VISIBLE,
  INDEX `fk_superhero_has_location_superhero1_idx` (`idsuperhero` ASC) VISIBLE,
  PRIMARY KEY (`idsighting`),
  CONSTRAINT `fk_superhero_has_location_superhero1`
    FOREIGN KEY (`idsuperhero`)
    REFERENCES `superheroesdb`.`superhero` (`idsuperhero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_superhero_has_location_location1`
    FOREIGN KEY (`idlocation`)
    REFERENCES `superheroesdb`.`location` (`idlocation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
