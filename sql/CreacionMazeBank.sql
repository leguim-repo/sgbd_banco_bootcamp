# creacion de la tabla cuentas
CREATE TABLE `cuentas` (
  `id_cuenta` int unsigned NOT NULL AUTO_INCREMENT,
  `balance` decimal(4,0) NOT NULL,
  `fecha` datetime NOT NULL,
  `usuarios_id_usuario` int unsigned NOT NULL,
  PRIMARY KEY (`id_cuenta`),
  KEY `fk_cuentas_usuarios_idx` (`usuarios_id_usuario`),
  CONSTRAINT `fk_cuentas_usuarios` FOREIGN KEY (`usuarios_id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

# usuarios
CREATE TABLE `usuarios` (
  `id_usuario` int unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `dni` varchar(45) NOT NULL,
  `usuario` varchar(45) NOT NULL,
  `pin` int NOT NULL,
  `activo` tinyint DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

# historicos 
CREATE TABLE `historicos` (
  `id_historico` int NOT NULL,
  `movimiento` varchar(45) NOT NULL,
  `balance` decimal(4,0) NOT NULL,
  `fecha` datetime NOT NULL,
  `cuentas_id_cuenta` int unsigned NOT NULL,
  PRIMARY KEY (`id_historico`),
  KEY `fk_historicos_cuentas1_idx` (`cuentas_id_cuenta`),
  CONSTRAINT `fk_historicos_cuentas1` FOREIGN KEY (`cuentas_id_cuenta`) REFERENCES `cuentas` (`id_cuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

