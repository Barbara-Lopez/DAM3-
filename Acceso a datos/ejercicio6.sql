use prueba;
CREATE TABLE departamentos (
	dept_no int(11) NOT NULL,
  dnombre varchar(45) DEFAULT NULL,
  loc varchar(45) DEFAULT NULL,
  PRIMARY KEY (`dept_no`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

create TABLE `empleados` (
  `emp_no` int(11) NOT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `oficio` varchar(45) DEFAULT NULL,
  `dir` int(11) DEFAULT NULL,
  `fecha_alt` date DEFAULT NULL,
  `salario` float DEFAULT NULL,
  `comision` float DEFAULT NULL,
  `dept_no` int(11) DEFAULT NULL,
  PRIMARY KEY (`emp_no`),
  KEY `fk_Empleados_Departamentos_idx` (`dept_no`),
  CONSTRAINT `fk_Empleados_Departamentos` FOREIGN KEY (`dept_no`) REFERENCES `departamentos` (`dept_no`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;






INSERT INTO `prueba`.`departamentos` (`dept_no`, `dnombre`, `loc`) VALUES ('10', 'Marketing', 'Guadalajara');

INSERT INTO `prueba`.`empleados` (`emp_no`, `apellido`, `oficio`,dir,fecha_alt,salario,comision,dept_no) VALUES (4455,'PEPE','VENDEDOR',7499,'2019-10-31',1500.0,10.0,10);
INSERT INTO `prueba`.`empleados` (`emp_no`, `apellido`, `oficio`,dir,fecha_alt,salario,comision,dept_no) VALUES (4456,'ALBERTO','SOLDADOR',7501,'2019-10-31',1500.0,0.0,10);
INSERT INTO `prueba`.`empleados` (`emp_no`, `apellido`, `oficio`,dir,fecha_alt,salario,comision,dept_no) VALUES (4457,'AITOR','FRESADOR',7502,'2019-10-31',1400.0,0.0,10);
INSERT INTO `prueba`.`empleados` (`emp_no`, `apellido`, `oficio`,dir,fecha_alt,salario,comision,dept_no) VALUES (4458,'IGOR','OPERARIO',7502,'2019-10-31',1450.0,0.0,10);

select apellido,oficio,salario from empleados where dept_no=10;
 update empleados set salario=1500.0 where apellido='PEPE' and apellido='ALBERTO';
select apellido,salario from empleados where salario=(select max(salario) from empleados);
select apellido,salario from empleados where emp_no=4457;
update empleados set salario =2000 where comision=10;
