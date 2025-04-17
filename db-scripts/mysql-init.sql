-- Configurar el conjunto de caracteres para la sesión
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;
SET character_set_connection = utf8mb4;

-- Crear la base de datos con soporte UTF-8
CREATE DATABASE IF NOT EXISTS fitech
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE fitech;

-- Crear tabla de tipos de usuario
CREATE TABLE IF NOT EXISTS user_type (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Crear tabla de tipos de métricas
CREATE TABLE IF NOT EXISTS metric_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE -- Ej: 'Peso, Masa mulcular, Grasa corporal, IMC, Edad metabólica, Grasa visceral, % de agua, Masa ósea, Frecuencia cardiaca en reposo'
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Crear tabla de estados de objetivos de fitness
CREATE TABLE IF NOT EXISTS fitness_goal_status (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE -- Ej: 'En progreso', 'Completado', 'Cancelado'
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Crear tabla de tipos de objetivos de fitness
CREATE TABLE IF NOT EXISTS fitness_goal_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE -- Ej: 'Pérdida de peso', 'Aumento de masa muscular', 'Definición'
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Crear tabla de personas
CREATE TABLE IF NOT EXISTS person (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(125) NOT NULL,
  last_name VARCHAR(125) NOT NULL,
  document_number VARCHAR(80) NOT NULL UNIQUE,
  phone_number VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Crear tabla de usuarios
CREATE TABLE IF NOT EXISTS user (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_email_verified BOOLEAN DEFAULT FALSE,
  email_verification_token VARCHAR(255),
  email_token_expires_at DATETIME,
  person_id INT,
  type INT DEFAULT 0, -- 0: Cliente, 1: Entrenador, 2: Soporte
  FOREIGN KEY (person_id) REFERENCES person(id),
  FOREIGN KEY (type) REFERENCES user_type(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Crear tabla de objetivos de fitness
CREATE TABLE IF NOT EXISTS fitness_goal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(125) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    note TEXT, -- Notas adicionales sobre el objetivo
    goal_type_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (goal_type_id) REFERENCES fitness_goal_type(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS fitness_goal_detail (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fitness_goal_id INT NOT NULL, -- Referencia al objetivo de fitness
    metric_type_id INT NOT NULL,  -- Referencia al tipo de métrica (peso, masa muscular, etc.)
    target_value DECIMAL(10, 2) NOT NULL,  -- Valor objetivo para esta métrica
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (fitness_goal_id) REFERENCES fitness_goal(id),
    FOREIGN KEY (metric_type_id) REFERENCES metric_types(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS user_fitness_goals (
    id INT PRIMARY KEY,
    target_date DATE NOT NULL,
    note TEXT,
    trainer_id INT,
    fitness_goal_id INT, -- Referencia al Id del objetivo
    goal_status_id INT, -- Estado del objetivo
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (fitness_goal_id) REFERENCES fitness_goal(id),
    FOREIGN KEY (trainer_id) REFERENCES user(id),
    FOREIGN KEY (goal_status_id) REFERENCES fitness_goal_status(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS user_files (
    id INT PRIMARY KEY,
    user_id INT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(50) NOT NULL,
    filePath VARCHAR(255) NOT NULL,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS  achievements (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    trainer_id BIGINT NOT NULL,
    achievement_type VARCHAR(255) NOT NULL, -- Ej: 'certification', 'award'
    title VARCHAR(255) NOT NULL,
    description TEXT,
    achieved_at DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_achievements_trainer
    FOREIGN KEY (trainer_id) REFERENCES user(id)
    ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS achievement_files (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     achievement_id BIGINT NOT NULL,
     file_user_id INT NOT NULL,
     uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_achievement_files_achievement
    FOREIGN KEY (achievement_id) REFERENCES achievements(id)
    ON DELETE CASCADE

    CONSTRAINT fk_achievement_files_user_files
    FOREIGN KEY (file_user_id) REFERENCES user_files(id)
    ON DELETE CASCADE
);

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
