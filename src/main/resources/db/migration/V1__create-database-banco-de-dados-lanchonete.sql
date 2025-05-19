-- --------------------------------------------------------
-- Servidor:                     127.0.0.1
-- Versão do servidor:           11.7.2-MariaDB - mariadb.org binary distribution
-- OS do Servidor:               Win64
-- HeidiSQL Versão:              12.10.0.7033
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Copiando estrutura do banco de dados para lanchonetedb
CREATE DATABASE IF NOT EXISTS `lanchonetedb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_uca1400_ai_ci */;
USE `lanchonetedb`;

-- Copiando estrutura para tabela lanchonetedb.categorias
CREATE TABLE IF NOT EXISTS `categorias` (
  `CODIGO` bigint(20) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Copiando dados para a tabela lanchonetedb.categorias: ~4 rows (aproximadamente)
INSERT INTO `categorias` (`CODIGO`, `NOME`) VALUES
	(1, 'LANCHE'),
	(2, 'ACOMPANHAMENTO'),
	(3, 'BEBIDA'),
	(4, 'SOBREMESA');

-- Copiando estrutura para tabela lanchonetedb.entregas
CREATE TABLE IF NOT EXISTS `entregas` (
  `CODIGO` bigint(20) NOT NULL AUTO_INCREMENT,
  `PEDIDOCODIGO` bigint(20) DEFAULT NULL,
  `DATAHORAENTREGA` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`CODIGO`),
  KEY `FK__pedidos__entregas` (`PEDIDOCODIGO`),
  CONSTRAINT `FK__pedidos__entregas` FOREIGN KEY (`PEDIDOCODIGO`) REFERENCES `pedidos` (`CODIGO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Copiando dados para a tabela lanchonetedb.entregas: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela lanchonetedb.filapedidospreparacao
CREATE TABLE IF NOT EXISTS `filapedidospreparacao` (
  `CODIGO` bigint(20) NOT NULL AUTO_INCREMENT,
  `PEDIDOCODIGO` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`),
  KEY `FK__pedido__filapedidospreparacao` (`PEDIDOCODIGO`),
  CONSTRAINT `FK__pedido__filapedidospreparacao` FOREIGN KEY (`PEDIDOCODIGO`) REFERENCES `pedidos` (`CODIGO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Copiando dados para a tabela lanchonetedb.filapedidospreparacao: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela lanchonetedb.pagamentos
CREATE TABLE IF NOT EXISTS `pagamentos` (
  `CODIGO` bigint(20) NOT NULL AUTO_INCREMENT,
  `PEDIDOCODIGO` bigint(20) NOT NULL,
  `VALORPAGO` decimal(20,6) NOT NULL DEFAULT 0.000000,
  `STATUS` varchar(50) NOT NULL DEFAULT '0',
  `DATAHORAPAGAMENTO` timestamp NOT NULL,
  PRIMARY KEY (`CODIGO`),
  KEY `FK_pagamentos_pedido` (`PEDIDOCODIGO`),
  CONSTRAINT `FK_pagamentos_pedido` FOREIGN KEY (`PEDIDOCODIGO`) REFERENCES `pedidos` (`CODIGO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Copiando dados para a tabela lanchonetedb.pagamentos: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela lanchonetedb.pedidoitem
CREATE TABLE IF NOT EXISTS `pedidoitem` (
  `CODIGO` bigint(20) NOT NULL AUTO_INCREMENT,
  `PEDIDOCODIGO` bigint(20) DEFAULT NULL,
  `PRODUTOCODIGO` bigint(20) DEFAULT NULL,
  `QUANTIDADE` int(11) DEFAULT NULL,
  `PRECOUNITARIO` decimal(20,6) DEFAULT NULL,
  `PRECOTOTAL` decimal(20,6) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`),
  KEY `FK__pedido` (`PEDIDOCODIGO`),
  KEY `FK__produtos` (`PRODUTOCODIGO`),
  CONSTRAINT `FK__pedido` FOREIGN KEY (`PEDIDOCODIGO`) REFERENCES `pedidos` (`CODIGO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK__produtos` FOREIGN KEY (`PRODUTOCODIGO`) REFERENCES `produtos` (`CODIGO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Copiando dados para a tabela lanchonetedb.pedidoitem: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela lanchonetedb.pedidos
CREATE TABLE IF NOT EXISTS `pedidos` (
  `CODIGO` bigint(20) NOT NULL AUTO_INCREMENT,
  `USUARIOCODIGO` bigint(20) DEFAULT NULL,
  `STATUS` varchar(50) DEFAULT NULL,
  `VALORTOTAL` decimal(20,6) DEFAULT NULL,
  `DATAHORASOLICITACAO` timestamp NULL DEFAULT NULL,
  `TEMPOTOTALPREPARO` time DEFAULT NULL,
  PRIMARY KEY (`CODIGO`),
  KEY `FK_pedido_usuario` (`USUARIOCODIGO`),
  CONSTRAINT `FK_pedido_usuario` FOREIGN KEY (`USUARIOCODIGO`) REFERENCES `usuarios` (`CODIGO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Copiando dados para a tabela lanchonetedb.pedidos: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela lanchonetedb.produtos
CREATE TABLE IF NOT EXISTS `produtos` (
  `CODIGO` bigint(20) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(50) NOT NULL DEFAULT '',
  `DESCRICAO` varchar(50) NOT NULL DEFAULT '',
  `CATEGORIACODIGO` bigint(20) NOT NULL DEFAULT 0,
  `PRECO` decimal(20,6) NOT NULL DEFAULT 0.000000,
  `TEMPOPREPARO` time NOT NULL DEFAULT '00:00:00',
  PRIMARY KEY (`CODIGO`),
  KEY `FK_produtos_categorias` (`CATEGORIACODIGO`),
  CONSTRAINT `FK_produtos_categorias` FOREIGN KEY (`CATEGORIACODIGO`) REFERENCES `categorias` (`CODIGO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Copiando dados para a tabela lanchonetedb.produtos: ~0 rows (aproximadamente)

-- Copiando estrutura para tabela lanchonetedb.usuarios
CREATE TABLE IF NOT EXISTS `usuarios` (
  `CODIGO` bigint(20) NOT NULL AUTO_INCREMENT,
  `NOME` varchar(50) DEFAULT NULL,
  `CPF` varchar(50) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CODIGO`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;

-- Copiando dados para a tabela lanchonetedb.usuarios: ~1 rows (aproximadamente)
INSERT INTO `usuarios` (`CODIGO`, `NOME`, `CPF`, `EMAIL`) VALUES
	(1, 'USUARIO PADRAO', '12345678901', 'padrao@email.com');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
