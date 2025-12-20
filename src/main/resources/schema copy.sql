-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS testdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE testdb;

-- users 테이블 생성
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    age INT,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_status (status),
    INDEX idx_age (age)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 샘플 데이터 삽입
INSERT INTO users (username, email, phone, age, status) VALUES
('john_doe', 'john@example.com', '010-1234-5678', 25, 'ACTIVE'),
('jane_smith', 'jane@example.com', '010-2345-6789', 30, 'ACTIVE'),
('bob_johnson', 'bob@example.com', '010-3456-7890', 35, 'INACTIVE'),
('alice_williams', 'alice@example.com', '010-4567-8901', 28, 'ACTIVE'),
('charlie_brown', 'charlie@example.com', '010-5678-9012', 42, 'ACTIVE'),
('diana_davis', 'diana@example.com', '010-6789-0123', 33, 'INACTIVE'),
('edward_miller', 'edward@example.com', '010-7890-1234', 27, 'ACTIVE'),
('fiona_wilson', 'fiona@example.com', '010-8901-2345', 31, 'ACTIVE'),
('george_moore', 'george@example.com', '010-9012-3456', 29, 'ACTIVE'),
('helen_taylor', 'helen@example.com', '010-0123-4567', 26, 'INACTIVE'),
('ian_anderson', 'ian@example.com', '010-1111-2222', 38, 'ACTIVE'),
('julia_thomas', 'julia@example.com', '010-2222-3333', 24, 'ACTIVE'),
('kevin_jackson', 'kevin@example.com', '010-3333-4444', 45, 'ACTIVE'),
('laura_white', 'laura@example.com', '010-4444-5555', 32, 'INACTIVE'),
('michael_harris', 'michael@example.com', '010-5555-6666', 36, 'ACTIVE'),
('nancy_martin', 'nancy@example.com', '010-6666-7777', 28, 'ACTIVE'),
('oliver_thompson', 'oliver@example.com', '010-7777-8888', 41, 'ACTIVE'),
('patricia_garcia', 'patricia@example.com', '010-8888-9999', 34, 'INACTIVE'),
('quincy_martinez', 'quincy@example.com', '010-9999-0000', 27, 'ACTIVE'),
('rachel_robinson', 'rachel@example.com', '010-0000-1111', 29, 'ACTIVE');