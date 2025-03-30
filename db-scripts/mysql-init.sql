CREATE DATABASE fitech;

USE fitech;


CREATE TABLE person (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(125) NOT NULL,
  last_name VARCHAR(125) NOT NULL,
  document_number VARCHAR(80) NOT NULL UNIQUE,
  phone_number VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL
);

create Table user_type (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE user (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  person_id INT,
  type INT DEFAULT 0, -- 0: Cliente, 1: Entrenador, 2: Soporte
  FOREIGN KEY (person_id) REFERENCES person(id),
  FOREIGN KEY (type) REFERENCES user_type(id)
);

CREATE TABLE user_fitness_metrics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    weight DECIMAL(5,2) NOT NULL,  -- Peso en kg
    height DECIMAL(5,2) NOT NULL,  -- Altura en cm
    body_fat_percentage DECIMAL(5,2), -- % de grasa corporal
    muscle_mass DECIMAL(5,2), -- Masa muscular en kg
    bmi DECIMAL(5,2) GENERATED ALWAYS AS (weight / (height / 100 * height / 100)) STORED, -- IMC calculado automáticamente
    metabolic_age INT, -- Edad metabólica
    visceral_fat_level DECIMAL(5,2), -- Nivel de grasa visceral
    water_percentage DECIMAL(5,2), -- % de agua en el cuerpo
    bone_mass DECIMAL(5,2), -- Masa ósea en kg
    heart_rate_resting INT, -- Frecuencia cardiaca en reposo
    evaluation_date DATE NOT NULL, -- Fecha de la evaluación
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE fitness_goals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    goal_type ENUM('weight_loss', 'muscle_gain', 'maintenance', 'other') NOT NULL,
    target_weight DECIMAL(5,2), -- Peso objetivo en kg
    target_body_fat DECIMAL(5,2), -- % de grasa corporal objetivo
    target_muscle_mass DECIMAL(5,2), -- Masa muscular objetivo en kg
    start_date DATE NOT NULL,
    end_date DATE,
    status ENUM('active', 'completed', 'cancelled') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE user_progress_photos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    photo_url VARCHAR(500) NOT NULL, -- URL de la foto almacenada en S3 u otro servicio
    taken_at DATE NOT NULL, -- Fecha en que se tomó la foto
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);


CREATE TABLE fitness_evaluations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    evaluator_id INT, -- ID del entrenador o nutricionista que realizó la evaluación
    notes TEXT, -- Comentarios sobre la evaluación
    evaluation_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);