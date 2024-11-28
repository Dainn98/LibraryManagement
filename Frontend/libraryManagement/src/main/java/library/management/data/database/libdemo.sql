DROP DATABASE IF EXISTS libdemo;
CREATE DATABASE libdemo;
USE libdemo;

-- Tạo bảng manager với cột managerID là khóa chính và AUTO_INCREMENT
CREATE TABLE manager (
                         managerID INT AUTO_INCREMENT PRIMARY KEY,
                         managerName VARCHAR(100) UNIQUE,
                         identityCard VARCHAR(20) UNIQUE,
                         password CHAR(12) NOT NULL,
                         email VARCHAR(100) UNIQUE not null,
                         phoneNumber VARCHAR(15) UNIQUE
);

ALTER TABLE manager AUTO_INCREMENT = 1;

INSERT INTO manager (managerName, email, password)
VALUES ('Tuan Anh', 'a', '123'),
       ('Duc Thien', 'pdthien4325@gmail.com', '123'),
       ('Duc Anh', 'b', '123'),
       ('Nang Diu', 'c', '123');

SELECT * FROM manager;

-- Tạo bảng category với categoryID là khóa chính và AUTO_INCREMENT
CREATE TABLE category (
                          categoryID INT AUTO_INCREMENT PRIMARY KEY,
                          tag VARCHAR(50) UNIQUE NOT NULL
);

INSERT INTO category (tag)
VALUES ('Fantasy');

SELECT * FROM category;

-- Tạo bảng language với lgID là khóa chính và AUTO_INCREMENT
CREATE TABLE language (
                          lgID SMALLINT AUTO_INCREMENT PRIMARY KEY,
                          lgName VARCHAR(50) UNIQUE NOT NULL
);

INSERT INTO language (lgName)
VALUES ('English'),
       ('Spanish'),
       ('French'),
       ('German'),
       ('Chinese'),
       ('Japanese'),
       ('Russian'),
       ('Portuguese'),
       ('Italian'),
       ('Korean');

SELECT * FROM language;

-- Tạo bảng user với userName là khóa chính
CREATE TABLE user (
                      userName VARCHAR(100) PRIMARY KEY,
                      identityCard CHAR(12) UNIQUE,
                      phoneNumber CHAR(10),
                      email VARCHAR(100) UNIQUE,
                      password varchar(100) not null,
                      country VARCHAR(50) default 'not set',
                      state VARCHAR(50) default 'not set',
                      status VARCHAR(10) DEFAULT 'pending',
                      registeredDate DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (userName, identityCard, phoneNumber, email, password, country, state)
VALUES ('Tran Thi B', '012345678902', '0987654322', 'b@example.com', 'password456', 'Vietnam', 'Ho Chi Minh City');

INSERT INTO user (userName, identityCard, phoneNumber, email, password)
VALUES ('Nguyen Van A', '123456789', '0987654321', 'a@example.com', 'password123');

SELECT * FROM user;

-- Tạo bảng document với khóa chính và khóa ngoại
CREATE TABLE document (
                          documentId INT AUTO_INCREMENT PRIMARY KEY,
                          categoryID INT,
                          publisher VARCHAR(100),
                          lgID SMALLINT,
                          title VARCHAR(100) NOT NULL,
                          author VARCHAR(100) NOT NULL,
                          isbn VARCHAR(30) UNIQUE NOT NULL,
                          description TEXT,
                          url TEXT,
                          image TEXT,
                          quantity INT NOT NULL,
                          availableCopies INT NOT NULL,
                          addDate DATETIME DEFAULT CURRENT_TIMESTAMP,
                          price DECIMAL(10,2) NOT NULL,
                          availability VARCHAR(15) DEFAULT 'available',
                          FOREIGN KEY (categoryId) REFERENCES category(categoryID),
                          FOREIGN KEY (lgId) REFERENCES language(lgID)
);

SELECT * FROM document;

-- Tạo bảng loans với khóa chính và khóa ngoại
CREATE TABLE loans (
                       loanID INT AUTO_INCREMENT PRIMARY KEY,
                       userName VARCHAR(100),
                       documentId INT,
                       quantityOfBorrow SMALLINT NOT NULL,
                       deposit DECIMAL(10, 2) NOT NULL,
                       dateOfBorrow DATETIME DEFAULT CURRENT_TIMESTAMP,
                       requiredReturnDate DATETIME NOT NULL,
                       returnDate DATETIME DEFAULT NULL,
                       status VARCHAR(20) DEFAULT 'pending',
                       FOREIGN KEY (userName) REFERENCES user(userName),
                       FOREIGN KEY (documentId) REFERENCES document(documentId)
);

SELECT * FROM loans;

-- Tạo bảng suggestion
CREATE TABLE suggestion (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            value VARCHAR(255) UNIQUE NOT NULL,
                            frequency INT NOT NULL
);

SELECT * FROM suggestion;
