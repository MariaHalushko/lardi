# "Phone book" app for Lardi
Use:
<ul>
  <li>Spring Boot</li>
  <li>Hibernate as JPA provider</li>
  <li>Spring Security oauth2</li>
  <li>Maven</li>
</ul>
Frontend:
<ul>
  <li>AngularJS</li>
  <li>Bootstrap 3</li>
</ul>
<b>For starting app:</b> java -jar -Dapp.conf={path}\application.properties  {app}.jar

<b>Example of application.properties:</b>

JPA:

spring.profiles.active: <b>jpa</b><br>
db.driver: <b>com.mysql.jdbc.Driver</b><br>
db.url: <b>{url}</b><br>
db.username: <b>{username}</b><br>
db.password: <b>{password}</b><br>
hibernate.dialect: <b>org.hibernate.dialect.MySQL5Dialect</b><br>
hibernate.hbm2ddl.auto: <b>validate</b><br>
entitymanager.packagesToScan: <b>syvenko.model</b><br>

JSON:

spring.profiles.active: <b>json</b><br>
jsondb.path: <b>/db/</b>

<b>NOTE: jsondb.path must be a folder!</b>

<b>SQL:</b>

```sql
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `phonebook` DEFAULT CHARACTER SET utf8 ;
USE `phonebook` ;

CREATE TABLE IF NOT EXISTS `phonebook`.`account` (
  `id` BIGINT(20) NOT NULL,
  `firstName` VARCHAR(255) NULL DEFAULT NULL,
  `lastName` VARCHAR(255) NULL DEFAULT NULL,
  `login` VARCHAR(255) NOT NULL,
  `middleName` VARCHAR(255) NULL DEFAULT NULL,
  `password` VARCHAR(255) NOT NULL,
  `fullname` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UK_kymjhvjpd14ci5gxwnbvnw8cu` (`login` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `phonebook`.`contact` (
  `id` BIGINT(20) NOT NULL,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  `email` VARCHAR(255) NULL DEFAULT NULL,
  `firstName` VARCHAR(255) NOT NULL,
  `homePhoneNumber` VARCHAR(255) NULL DEFAULT NULL,
  `lastName` VARCHAR(255) NOT NULL,
  `middleName` VARCHAR(255) NOT NULL,
  `mobilePhoneNumber` VARCHAR(255) NOT NULL,
  `account_id` BIGINT(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK1g35kvh77ugw3jkgd9vb90mg4` (`account_id` ASC),
  CONSTRAINT `FK1g35kvh77ugw3jkgd9vb90mg4`
    FOREIGN KEY (`account_id`)
    REFERENCES `phonebook`.`account` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `phonebook`.`hibernate_sequence` (
  `next_val` BIGINT(20) NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
```

