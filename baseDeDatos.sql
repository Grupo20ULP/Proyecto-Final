-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-10-2025 a las 00:16:55
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `spa_entre_dedosgp20`
--
CREATE DATABASE IF NOT EXISTS `spa_entre_dedos_gp20` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `spa_entre_dedos_gp20`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `codCli` int(11) NOT NULL,
  `dni` varchar(15) NOT NULL,
  `nombre_completo` varchar(100) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `edad` int(11) DEFAULT NULL,
  `afecciones` text DEFAULT NULL,
  `estado` enum('activo','inactivo') DEFAULT 'activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consultorio`
--

CREATE TABLE `consultorio` (
  `nroConsultorio` int(11) NOT NULL,
  `usos` enum('facial','corporal','relajacion','estetico') NOT NULL,
  `equipamiento` text DEFAULT NULL,
  `apto` enum('si','no') DEFAULT 'si'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dia_de_spa`
--

CREATE TABLE `dia_de_spa` (
  `codPack` int(11) NOT NULL,
  `fecha_hora` datetime NOT NULL,
  `preferencias` text DEFAULT NULL,
  `cliente_id` int(11) NOT NULL,
  `monto_total` decimal(10,2) DEFAULT NULL,
  `estado` enum('pendiente','confirmado','realizado','cancelado') DEFAULT 'pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `instalacion`
--

CREATE TABLE `instalacion` (
  `codInstal` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `detalle_uso` text DEFAULT NULL,
  `precio30m` decimal(8,2) NOT NULL,
  `estado` enum('activa','inactiva') DEFAULT 'activa'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `masajista`
--

CREATE TABLE `masajista` (
  `matricula` int(11) NOT NULL,
  `nombre_apellido` varchar(100) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `especialidad` enum('facial','corporal','relajacion','estetico') NOT NULL,
  `estado` enum('activo','inactivo') DEFAULT 'activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sesion`
--

CREATE TABLE `sesion` (
  `codSesion` int(11) NOT NULL,
  `fecha_hora_inicio` datetime NOT NULL,
  `fecha_hora_fin` datetime NOT NULL,
  `tratamiento_id` int(11) NOT NULL,
  `consultorio_id` int(11) DEFAULT NULL,
  `masajista_id` int(11) NOT NULL,
  `dia_spa_id` int(11) NOT NULL,
  `instalacion_id` int(11) DEFAULT NULL,
  `estado` enum('pendiente','confirmada','realizada','cancelada') DEFAULT 'pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tratamiento`
-- list<productos> lo agregamos en java 
--

CREATE TABLE `tratamiento` (
  `codTratam` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `tipo` enum('facial','corporal','relajacion','estetico') NOT NULL,
  `detalle` text DEFAULT NULL,
  `duracion` int(11) NOT NULL,
  `costo` decimal(10,2) NOT NULL,
  `activo` enum('si','no') DEFAULT 'si'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`codCli`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `consultorio`
--
ALTER TABLE `consultorio`
  ADD PRIMARY KEY (`nroConsultorio`);

--
-- Indices de la tabla `dia_de_spa`
--
ALTER TABLE `dia_de_spa`
  ADD PRIMARY KEY (`codPack`),
  ADD KEY `cliente_id` (`cliente_id`);

--
-- Indices de la tabla `instalacion`
--
ALTER TABLE `instalacion`
  ADD PRIMARY KEY (`codInstal`);

--
-- Indices de la tabla `masajista`
--
ALTER TABLE `masajista`
  ADD PRIMARY KEY (`matricula`);

--
-- Indices de la tabla `sesion`
--
ALTER TABLE `sesion`
  ADD PRIMARY KEY (`codSesion`),
  ADD KEY `tratamiento_id` (`tratamiento_id`),
  ADD KEY `consultorio_id` (`consultorio_id`),
  ADD KEY `masajista_id` (`masajista_id`),
  ADD KEY `dia_spa_id` (`dia_spa_id`),
  ADD KEY `instalacion_id` (`instalacion_id`);

--
-- Indices de la tabla `tratamiento`
--
ALTER TABLE `tratamiento`
  ADD PRIMARY KEY (`codTratam`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `codCli` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `consultorio`
--
ALTER TABLE `consultorio`
  MODIFY `nroConsultorio` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `dia_de_spa`
--
ALTER TABLE `dia_de_spa`
  MODIFY `codPack` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `instalacion`
--
ALTER TABLE `instalacion`
  MODIFY `codInstal` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `masajista`
--
ALTER TABLE `masajista`
  MODIFY `matricula` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sesion`
--
ALTER TABLE `sesion`
  MODIFY `codSesion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `tratamiento`
--
ALTER TABLE `tratamiento`
  MODIFY `codTratam` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `dia_de_spa`
--
ALTER TABLE `dia_de_spa`
  ADD CONSTRAINT `dia_de_spa_ibfk_1` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`codCli`);

--
-- Filtros para la tabla `sesion`
--
ALTER TABLE `sesion`
  ADD CONSTRAINT `sesion_ibfk_1` FOREIGN KEY (`tratamiento_id`) REFERENCES `tratamiento` (`codTratam`),
  ADD CONSTRAINT `sesion_ibfk_2` FOREIGN KEY (`consultorio_id`) REFERENCES `consultorio` (`nroConsultorio`),
  ADD CONSTRAINT `sesion_ibfk_3` FOREIGN KEY (`masajista_id`) REFERENCES `masajista` (`matricula`),
  ADD CONSTRAINT `sesion_ibfk_4` FOREIGN KEY (`dia_spa_id`) REFERENCES `dia_de_spa` (`codPack`),
  ADD CONSTRAINT `sesion_ibfk_5` FOREIGN KEY (`instalacion_id`) REFERENCES `instalacion` (`codInstal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
