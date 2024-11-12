create database donna_pizza;
use donna_pizza;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `donna pizza`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
                            `id_cliente` int(11) NOT NULL,
                            `nombre` varchar(50) NOT NULL,
                            `apellido` varchar(50) NOT NULL,
                            `email` varchar(50) NOT NULL,
                            `telefono` varchar(13) DEFAULT NULL,
                            `direccion` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalles_pedido`
--

CREATE TABLE `detalles_pedido` (
                                   `id_detalle` int(11) NOT NULL,
                                   `id_pedido` int(11) DEFAULT NULL,
                                   `id_pizza` int(11) DEFAULT NULL,
                                   `id_pizza_familiar` int(11) DEFAULT NULL,
                                   `id_pasta` int(11) DEFAULT NULL,
                                   `id_entrada` int(11) DEFAULT NULL,
                                   `cantidad` int(11) NOT NULL,
                                   `precio_unitario` decimal(10,2) NOT NULL,
                                   `subtotal` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `metodos_pago`
--

CREATE TABLE `metodos_pago` (
                                `id_metodo_pago` int(11) NOT NULL,
                                `nombre` varchar(50) NOT NULL,
                                `descripcion` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagos`
--

CREATE TABLE `pagos` (
                         `id_pago` int(11) NOT NULL,
                         `id_pedido` int(11) DEFAULT NULL,
                         `id_metodo_pago` int(11) DEFAULT NULL,
                         `monto` decimal(10,2) NOT NULL,
                         `fecha` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
                           `id_pedido` int(11) NOT NULL,
                           `id_usuario` int(11) DEFAULT NULL,
                           `id_cliente` int(11) DEFAULT NULL,
                           `fecha` datetime DEFAULT current_timestamp(),
                           `total` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pizzas`
--

CREATE TABLE `pizzas` (
                          `id_pizza` int(11) NOT NULL,
                          `nombre` varchar(100) NOT NULL,
                          `descripcion` text DEFAULT NULL,
                          `precio` decimal(8,2) NOT NULL,
                          `disponible` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promociones`
--

CREATE TABLE `promociones` (
                               `id_promocion` int(11) NOT NULL,
                               `nombre` varchar(50) NOT NULL,
                               `descripcion` text DEFAULT NULL,
                               `descuento` decimal(5,2) NOT NULL,
                               `requisitos` text DEFAULT NULL,
                               `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promociones_usuarios`
--

CREATE TABLE `promociones_usuarios` (
                                        `id_promocion_usuario` int(11) NOT NULL,
                                        `id_usuario` int(11) DEFAULT NULL,
                                        `id_promocion` int(11) DEFAULT NULL,
                                        `fecha_aplicacion` datetime DEFAULT current_timestamp(),
                                        `estado` varchar(20) DEFAULT 'pendiente'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
                            `id_usuario` int(11) NOT NULL,
                            `nombre` varchar(50) NOT NULL,
                            `apellido` varchar(50) NOT NULL,
                            `email` varchar(50) NOT NULL,
                            `telefono` varchar(13) DEFAULT NULL,
                            `direccion` text DEFAULT NULL,
                            `rol` varchar(20) NOT NULL,
                            `contraseña` varchar(50) NOT NULL,
                            `fecha_registro` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `pizzas_familiares` (
                                     `id_pizza_familiar` int(11) NOT NULL AUTO_INCREMENT,
                                     `nombre` varchar(100) NOT NULL,
                                     `descripcion` text DEFAULT NULL,
                                     `precio` decimal(8,2) NOT NULL,
                                     `disponible` tinyint(1) DEFAULT 1,
                                     PRIMARY KEY (`id_pizza_familiar`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Crear tabla para entradas
CREATE TABLE `entradas` (
                            `id_entrada` int(11) NOT NULL AUTO_INCREMENT,
                            `nombre` varchar(100) NOT NULL,
                            `descripcion` text DEFAULT NULL,
                            `precio` decimal(8,2) NOT NULL,
                            `disponible` tinyint(1) DEFAULT 1,
                            PRIMARY KEY (`id_entrada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Crear tabla para pastas
CREATE TABLE `pastas` (
                          `id_pasta` int(11) NOT NULL AUTO_INCREMENT,
                          `nombre` varchar(100) NOT NULL,
                          `descripcion` text DEFAULT NULL,
                          `precio` decimal(8,2) NOT NULL,
                          `disponible` tinyint(1) DEFAULT 1,
                          PRIMARY KEY (`id_pasta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
    ADD PRIMARY KEY (`id_cliente`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `detalles_pedido`
--
ALTER TABLE `detalles_pedido`
    ADD PRIMARY KEY (`id_detalle`),
  ADD KEY `id_pedido` (`id_pedido`),
  ADD KEY `id_pasta` (`id_pasta`),
  ADD KEY `id_pizza_familiar` (`id_pizza_familiar`),
  ADD KEY `id_entrada` (`id_entrada`);

--
-- Indices de la tabla `metodos_pago`
--
ALTER TABLE `metodos_pago`
    ADD PRIMARY KEY (`id_metodo_pago`);

--
-- Indices de la tabla `pagos`
--
ALTER TABLE `pagos`
    ADD PRIMARY KEY (`id_pago`),
  ADD KEY `id_pedido` (`id_pedido`),
  ADD KEY `id_metodo_pago` (`id_metodo_pago`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
    ADD PRIMARY KEY (`id_pedido`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_cliente` (`id_cliente`);

--
-- Indices de la tabla `pizzas`
--
ALTER TABLE `pizzas`
    ADD PRIMARY KEY (`id_pizza`);

--
-- Indices de la tabla `promociones`
--
ALTER TABLE `promociones`
    ADD PRIMARY KEY (`id_promocion`);

--
-- Indices de la tabla `promociones_usuarios`
--
ALTER TABLE `promociones_usuarios`
    ADD PRIMARY KEY (`id_promocion_usuario`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_promocion` (`id_promocion`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
    ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
    MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalles_pedido`
--
ALTER TABLE `detalles_pedido`
    MODIFY `id_detalle` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `ingredientes`

-- AUTO_INCREMENT de la tabla `metodos_pago`
--
ALTER TABLE `metodos_pago`
    MODIFY `id_metodo_pago` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pagos`
--
ALTER TABLE `pagos`
    MODIFY `id_pago` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
    MODIFY `id_pedido` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pizzas`
--
ALTER TABLE `pizzas`
    MODIFY `id_pizza` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `promociones`
--
ALTER TABLE `promociones`
    MODIFY `id_promocion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `promociones_usuarios`
--
ALTER TABLE `promociones_usuarios`
    MODIFY `id_promocion_usuario` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
    MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalles_pedido`
--
ALTER TABLE `detalles_pedido`
    ADD CONSTRAINT `detalles_pedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`),
  ADD CONSTRAINT `detalles_pedido_ibfk_2` FOREIGN KEY (`id_pizza`) REFERENCES `pizzas` (`id_pizza`),
  ADD CONSTRAINT `detalles_pedido_ibfk_3` FOREIGN KEY (`id_pizza_familiar`) REFERENCES `pizzas_familiares` (`id_pizza_familiar`),
    ADD CONSTRAINT `detalles_pedido_ibfk_4` FOREIGN KEY (`id_pasta`) REFERENCES `pastas` (`id_pasta`),
    ADD CONSTRAINT `detalles_pedido_ibfk_5` FOREIGN KEY (`id_entrada`) REFERENCES `entradas` (`id_entrada`);

--
-- Filtros para la tabla `ingredientes_pizza`
--

-- Filtros para la tabla `pagos`
--
ALTER TABLE `pagos`
    ADD CONSTRAINT `pagos_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`),
  ADD CONSTRAINT `pagos_ibfk_2` FOREIGN KEY (`id_metodo_pago`) REFERENCES `metodos_pago` (`id_metodo_pago`);

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
    ADD CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `user` (`id_usuario`),
  ADD CONSTRAINT `pedidos_ibfk_2` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`);

--
-- Filtros para la tabla `promociones_usuarios`
--
ALTER TABLE `promociones_usuarios`
    ADD CONSTRAINT `promociones_usuarios_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `user` (`id_usuario`),
  ADD CONSTRAINT `promociones_usuarios_ibfk_2` FOREIGN KEY (`id_promocion`) REFERENCES `promociones` (`id_promocion`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


CREATE TABLE `ingredientes` (
                                `id_ingrediente` int(11) NOT NULL AUTO_INCREMENT,
                                `nombre` varchar(50) NOT NULL,
                                `cantidad_disponible` decimal(10,2) NOT NULL,
                                PRIMARY KEY (`id_ingrediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `ingredientes_pizza` (
                                      `id_pizza` int(11) NOT NULL,
                                      `id_ingrediente` int(11) NOT NULL,
                                      `cantidad_necesaria` decimal(10,2) NOT NULL,
                                      PRIMARY KEY (`id_pizza`, `id_ingrediente`),
                                      FOREIGN KEY (`id_pizza`) REFERENCES `pizzas` (`id_pizza`),
                                      FOREIGN KEY (`id_ingrediente`) REFERENCES `ingredientes` (`id_ingrediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `documentos` (
                              `id_documento` int(11) NOT NULL AUTO_INCREMENT,
                              `tipo_documento` varchar(10) NOT NULL,  -- Puede ser 'boleta' o 'factura'
                              PRIMARY KEY (`id_documento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE `pedidos`
    ADD `id_documento` int(11) DEFAULT NULL,
ADD CONSTRAINT `pedidos_ibfk_3` FOREIGN KEY (`id_documento`) REFERENCES `documentos` (`id_documento`);

ALTER TABLE `user`
    MODIFY COLUMN `telefono` varchar(15) NOT NULL,
    ADD CONSTRAINT chk_telefono_usuarios CHECK (telefono REGEXP '^\\+[0-9]{1,3} [0-9]{6,12}$');

ALTER TABLE `clientes`
    MODIFY COLUMN `telefono` varchar(15) NOT NULL,
    ADD CONSTRAINT chk_telefono_clientes CHECK (telefono REGEXP '^\\+[0-9]{1,3} [0-9]{6,12}$');


-- Crear tabla para pizzas familiares


-- Crear tabla de relación entre ingredientes y pizzas familiares
CREATE TABLE `ingredientes_pizza_familiar` (
                                               `id_pizza_familiar` int(11) NOT NULL,
                                               `id_ingrediente` int(11) NOT NULL,
                                               `cantidad_necesaria` decimal(10,2) NOT NULL,
                                               PRIMARY KEY (`id_pizza_familiar`, `id_ingrediente`),
                                               FOREIGN KEY (`id_pizza_familiar`) REFERENCES `pizzas_familiares` (`id_pizza_familiar`),
                                               FOREIGN KEY (`id_ingrediente`) REFERENCES `ingredientes` (`id_ingrediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Crear tabla de relación entre ingredientes y entradas
CREATE TABLE `ingredientes_entrada` (
                                        `id_entrada` int(11) NOT NULL,
                                        `id_ingrediente` int(11) NOT NULL,
                                        `cantidad_necesaria` decimal(10,2) NOT NULL,
                                        PRIMARY KEY (`id_entrada`, `id_ingrediente`),
                                        FOREIGN KEY (`id_entrada`) REFERENCES `entradas` (`id_entrada`),
                                        FOREIGN KEY (`id_ingrediente`) REFERENCES `ingredientes` (`id_ingrediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Crear tabla de relación entre ingredientes y pastas
CREATE TABLE `ingredientes_pasta` (
                                      `id_pasta` int(11) NOT NULL,
                                      `id_ingrediente` int(11) NOT NULL,
                                      `cantidad_necesaria` decimal(10,2) NOT NULL,
                                      PRIMARY KEY (`id_pasta`, `id_ingrediente`),
                                      FOREIGN KEY (`id_pasta`) REFERENCES `pastas` (`id_pasta`),
                                      FOREIGN KEY (`id_ingrediente`) REFERENCES `ingredientes` (`id_ingrediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



select * from pizzas;
select * from ingredientes;