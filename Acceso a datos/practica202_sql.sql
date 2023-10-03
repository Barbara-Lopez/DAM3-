create database practica202;
use practica202;
CREATE TABLE doctors (
	id MEDIUMINT NOT NULL AUTO_INCREMENT,
	name varchar(45) DEFAULT NULL,
	lastname varchar(45) DEFAULT NULL,
    dni varchar(45) DEFAULT NULL,
    salary double DEFAULT NULL,
    speciality varchar(45) DEFAULT NULL,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

delete from practica202.doctors where id=2;
INSERT INTO doctors ( `name`, `lastname`,dni,salary,speciality) VALUES ("pepe","lopez","19213239R",1500.0,"dermatolocia");