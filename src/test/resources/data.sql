CREATE SCHEMA IF NOT EXISTS `sql7641808`;
USE `sql7641808`;

-- -----------------------------------------------------
-- Table `sql7641808`.`banco`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql7641808`.`banco` (
  `id_banco` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_banco`));


-- -----------------------------------------------------
-- Table `sql7641808`.`cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql7641808`.`cliente` (
  `id_cliente` INT NOT NULL AUTO_INCREMENT,
  `nombre_legal` VARCHAR(45) NOT NULL,
  `direccion_legal` VARCHAR(45) NOT NULL,
  `dinero` DOUBLE NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `id_banco` INT NOT NULL,
  PRIMARY KEY (`id_cliente`),
  -- INDEX `banco_idx` (`id_banco` ASC) VISIBLE,
  CONSTRAINT `pertenece_a_banco`
    FOREIGN KEY (`id_banco`)
    REFERENCES `sql7641808`.`banco` (`id_banco`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `sql7641808`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql7641808`.`usuario` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `contrasena` VARCHAR(45) NOT NULL,
  `rol` ENUM('ADMIN', 'BANCA') NOT NULL,
  `banco` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  CONSTRAINT `banco_fk`
    FOREIGN KEY (`banco`)
    REFERENCES `sql7641808`.`banco` (`id_banco`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `sql7641808`.`deal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql7641808`.`deal` (
  `id_deal` INT NOT NULL AUTO_INCREMENT,
  `estado` ENUM('PENDING', 'APPROVED', 'CLOSED') NOT NULL,
  `cantidad_prestamo` DOUBLE NOT NULL,
  `cantidad_abonada` DOUBLE NOT NULL,
  `cantidad_a_pagar` DOUBLE NOT NULL,
  `moneda` ENUM('EUR', 'GBP', 'JPY', 'USD') NOT NULL,
  `tipo` ENUM('SOLE_LENDER', 'SYNDICATED') NOT NULL,
  `descuento` TINYINT NOT NULL,
  `cliente` INT NOT NULL,
  `creado_por` INT NOT NULL,
  `aprobado_por` INT NULL DEFAULT NULL,
  `cerrado_por` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id_deal`),
  CONSTRAINT `aprobado_por`
    FOREIGN KEY (`aprobado_por`)
    REFERENCES `sql7641808`.`usuario` (`id_usuario`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `cerrado_por`
    FOREIGN KEY (`cerrado_por`)
    REFERENCES `sql7641808`.`usuario` (`id_usuario`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `cliente`
    FOREIGN KEY (`cliente`)
    REFERENCES `sql7641808`.`cliente` (`id_cliente`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `creado_por`
    FOREIGN KEY (`creado_por`)
    REFERENCES `sql7641808`.`usuario` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `sql7641808`.`facility`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql7641808`.`facility` (
  `id_facility` INT NOT NULL AUTO_INCREMENT,
  `tipo` ENUM('TERM', 'REVOLVER') NOT NULL,
  `estado` ENUM('PENDING', 'APPROVED', 'CLOSED') NOT NULL,
  `cantidad` DOUBLE NOT NULL,
  `fecha_creacion` DATE NOT NULL,
  `fecha_efectiva` DATE NOT NULL,
  `fecha_finalizacion` DATE NOT NULL,
  `deal` INT NOT NULL,
  PRIMARY KEY (`id_facility`),
  CONSTRAINT `from_deal`
    FOREIGN KEY (`deal`)
    REFERENCES `sql7641808`.`deal` (`id_deal`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `sql7641808`.`libro_inversion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql7641808`.`libro_inversion` (
  `id_libro_inversion` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `dinero` DOUBLE NOT NULL,
  `pertenece_a` INT NOT NULL,
  PRIMARY KEY (`id_libro_inversion`),
  CONSTRAINT `pertenece_a`
    FOREIGN KEY (`pertenece_a`)
    REFERENCES `sql7641808`.`banco` (`id_banco`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `sql7641808`.`notificacion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql7641808`.`notificacion` (
  `id_notificacion` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(45) NOT NULL,
  `mensaje` VARCHAR(150) NOT NULL,
  `enviado_a` INT NOT NULL,
  `enviado_por` INT NOT NULL,
  `enlace` VARCHAR(45) NOT NULL,
  `fecha_notificacion` DATE NOT NULL,
  PRIMARY KEY (`id_notificacion`),
  CONSTRAINT `enviado_a`
    FOREIGN KEY (`enviado_a`)
    REFERENCES `sql7641808`.`usuario` (`id_usuario`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `enviado_por`
    FOREIGN KEY (`enviado_por`)
    REFERENCES `sql7641808`.`usuario` (`id_usuario`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `sql7641808`.`tipo_interes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql7641808`.`tipo_interes` (
  `id_tipo_interes` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `valor` DOUBLE NOT NULL,
  PRIMARY KEY (`id_tipo_interes`));


-- -----------------------------------------------------
-- Table `sql7641808`.`outstanding`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql7641808`.`outstanding` (
  `id_outstanding` INT NOT NULL AUTO_INCREMENT,
  `cantidad_restante` DOUBLE NOT NULL,
  `fecha_efectiva` DATE NOT NULL,
  `fecha_creacion` DATE NOT NULL,
  `fecha_finalizacion` DATE NOT NULL,
  `pago_principal` DOUBLE NOT NULL,
  `pago_intereses` DOUBLE NOT NULL,
  `tipo_interes` INT NOT NULL,
  `tipo_cobros` ENUM('MANUAL', 'AUTOMATICO') NOT NULL,
  `periodicidad` ENUM('SEMANAL', 'MENSUAL', 'TRIMESTRAL') NOT NULL,
  `cantidad_cobro_periodico` DOUBLE NOT NULL,
  `facility` INT NOT NULL,
  PRIMARY KEY (`id_outstanding`),
  CONSTRAINT `interes`
    FOREIGN KEY (`tipo_interes`)
    REFERENCES `sql7641808`.`tipo_interes` (`id_tipo_interes`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `from_facility`
    FOREIGN KEY (`facility`)
    REFERENCES `sql7641808`.`facility` (`id_facility`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `sql7641808`.`participante`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql7641808`.`participante` (
  `id_participante` INT NOT NULL AUTO_INCREMENT,
  `id_banco` INT NOT NULL,
  `id_deal` INT NOT NULL,
  `porcentaje_participacion` DOUBLE NOT NULL,
  `agente` TINYINT NOT NULL,
  PRIMARY KEY (`id_participante`),
  CONSTRAINT `banco`
    FOREIGN KEY (`id_banco`)
    REFERENCES `sql7641808`.`banco` (`id_banco`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `deal`
    FOREIGN KEY (`id_deal`)
    REFERENCES `sql7641808`.`deal` (`id_deal`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);


-- -----------------------------------------------------
-- Table `sql7641808`.`transaccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sql7641808`.`transaccion` (
  `id_transaccion` INT NOT NULL AUTO_INCREMENT,
  `cantidad` DOUBLE NOT NULL,
  `id_cliente` INT NOT NULL,
  `id_banco` INT NOT NULL,
  `tipo_transaccion` ENUM('PAGO', 'COBRO') NOT NULL,
  `outstanding` INT NOT NULL,
  PRIMARY KEY (`id_transaccion`),
  CONSTRAINT `banco_id`
    FOREIGN KEY (`id_banco`)
    REFERENCES `sql7641808`.`banco` (`id_banco`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `cliente_id`
    FOREIGN KEY (`id_cliente`)
    REFERENCES `sql7641808`.`cliente` (`id_cliente`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `outstanding`
    FOREIGN KEY (`outstanding`)
    REFERENCES `sql7641808`.`outstanding` (`id_outstanding`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION);