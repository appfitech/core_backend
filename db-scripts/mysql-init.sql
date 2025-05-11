-- Set character set and collation
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
SET character_set_connection=utf8mb4;

-- Use the database
USE fitech;
-- Insertar datos en la tabla user_type
INSERT INTO user_type (name) VALUES ('Usuario');
INSERT INTO user_type (name) VALUES ('Entrenador');
INSERT INTO user_type (name) VALUES ('Soporte');

-- Insertar datos en la tabla metric_types
INSERT INTO metric_types (name) VALUES ('Peso');
INSERT INTO metric_types (name) VALUES ('Masa muscular');
INSERT INTO metric_types (name) VALUES ('Grasa corporal');
INSERT INTO metric_types (name) VALUES ('IMC');
INSERT INTO metric_types (name) VALUES ('Edad metabólica');
INSERT INTO metric_types (name) VALUES ('Grasa visceral');
INSERT INTO metric_types (name) VALUES ('% de agua');
INSERT INTO metric_types (name) VALUES ('Masa ósea');
INSERT INTO metric_types (name) VALUES ('Frecuencia cardiaca en reposo');

-- Insertar datos en la tabla fitness_goal_status
INSERT INTO fitness_goal_status (name) VALUES ('En progreso');
INSERT INTO fitness_goal_status (name) VALUES ('Completado');
INSERT INTO fitness_goal_status (name) VALUES ('Cancelado');

-- Insertar datos en la tabla fitness_goal_type
INSERT INTO fitness_goal_type (name) VALUES ('Pérdida de peso');
INSERT INTO fitness_goal_type (name) VALUES ('Aumento de masa muscular');
INSERT INTO fitness_goal_type (name) VALUES ('Definición');

-- Insertar una persona
INSERT INTO person (first_name, last_name, document_number, phone_number, email)
VALUES ('Admin', 'Admin', '12345678', '+1234567890', 'admin@fitech.com');

-- Insertar un usuario asociado a la persona
-- Nota: La contraseña es 'testpass' hasheada con BCrypt
INSERT INTO user (username, password, type, person_id, is_email_verified)
VALUES (
    'admin',
    '$2a$10$Mr9oKYjp8kqkIdSsngj1IOhvFfVf6WChbaF4WnYuWkoBrpny9eAyO',
    1, -- Tipo: Cliente
    LAST_INSERT_ID(), -- ID de la persona insertada
    TRUE -- Email verificado
);

-- Insertar unidades de medida comunes
INSERT INTO unit_of_measure (name, symbol, description) VALUES 
('Kilogramos', 'kg', 'Unidad de masa'),
('Gramos', 'g', 'Unidad de masa'),
('Porcentaje', '%', 'Porcentaje'),
('Kilogramos por metro cuadrado', 'kg/m²', 'Unidad de IMC'),
('Años', 'años', 'Unidad de tiempo'),
('Latidos por minuto', 'lpm', 'Frecuencia cardíaca'),
('Centímetros', 'cm', 'Unidad de longitud'),
('Mililitros', 'ml', 'Unidad de volumen'),
('Calorías', 'cal', 'Unidad de energía'),
('Kilocalorías', 'kcal', 'Unidad de energía (1000 calorías)');

-- Relacionar métricas con sus unidades de medida correspondientes
-- Peso
INSERT INTO metric_type_uom (metric_type_id, unit_of_measure_id, is_default) 
SELECT m.id, u.id, TRUE 
FROM metric_types m, unit_of_measure u 
WHERE m.name = 'Peso' AND u.symbol = 'kg';

-- Masa muscular
INSERT INTO metric_type_uom (metric_type_id, unit_of_measure_id, is_default) 
SELECT m.id, u.id, TRUE 
FROM metric_types m, unit_of_measure u 
WHERE m.name = 'Masa muscular' AND u.symbol = 'kg';

-- Grasa corporal
INSERT INTO metric_type_uom (metric_type_id, unit_of_measure_id, is_default) 
SELECT m.id, u.id, TRUE 
FROM metric_types m, unit_of_measure u 
WHERE m.name = 'Grasa corporal' AND u.symbol = '%';

-- IMC
INSERT INTO metric_type_uom (metric_type_id, unit_of_measure_id, is_default) 
SELECT m.id, u.id, TRUE 
FROM metric_types m, unit_of_measure u 
WHERE m.name = 'IMC' AND u.symbol = 'kg/m²';

-- Edad metabólica
INSERT INTO metric_type_uom (metric_type_id, unit_of_measure_id, is_default) 
SELECT m.id, u.id, TRUE 
FROM metric_types m, unit_of_measure u 
WHERE m.name = 'Edad metabólica' AND u.symbol = 'años';

-- Grasa visceral
INSERT INTO metric_type_uom (metric_type_id, unit_of_measure_id, is_default) 
SELECT m.id, u.id, TRUE 
FROM metric_types m, unit_of_measure u 
WHERE m.name = 'Grasa visceral' AND u.symbol = '%';

-- % de agua
INSERT INTO metric_type_uom (metric_type_id, unit_of_measure_id, is_default) 
SELECT m.id, u.id, TRUE 
FROM metric_types m, unit_of_measure u 
WHERE m.name = '% de agua' AND u.symbol = '%';

-- Masa ósea
INSERT INTO metric_type_uom (metric_type_id, unit_of_measure_id, is_default) 
SELECT m.id, u.id, TRUE 
FROM metric_types m, unit_of_measure u 
WHERE m.name = 'Masa ósea' AND u.symbol = 'kg';

-- Frecuencia cardíaca en reposo
INSERT INTO metric_type_uom (metric_type_id, unit_of_measure_id, is_default) 
SELECT m.id, u.id, TRUE 
FROM metric_types m, unit_of_measure u 
WHERE m.name = 'Frecuencia cardíaca en reposo' AND u.symbol = 'lpm';

-- Primero eliminamos los usuarios
DELETE FROM user WHERE username != 'admin';

-- Luego eliminamos las personas
DELETE FROM person WHERE first_name != 'Admin';