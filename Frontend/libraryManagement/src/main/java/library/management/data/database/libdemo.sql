DROP DATABASE IF EXISTS libdemo;
CREATE DATABASE libdemo;
USE libdemo;

-- Tạo bảng manager với cột managerID là khóa chính và AUTO_INCREMENT
CREATE TABLE manager (
                         managerID INT AUTO_INCREMENT PRIMARY KEY,
                         managerName VARCHAR(100) UNIQUE,
                         identityCard VARCHAR(20) UNIQUE,
                         password CHAR(12) NOT NULL,
                         email VARCHAR(100) UNIQUE
);

ALTER TABLE manager AUTO_INCREMENT = 1;

INSERT INTO manager (managerName, password)
VALUES ('Tuan Anh', '123'),
       ('Duc Thien', '123'),
       ('Duc Anh', '123'),
       ('Nang Diu', '123');

SELECT * FROM manager;

-- Tạo bảng category với categoryID là khóa chính và AUTO_INCREMENT
CREATE TABLE category (
                          categoryID INT AUTO_INCREMENT PRIMARY KEY,
                          tag VARCHAR(50) UNIQUE NOT NULL
);

INSERT INTO category (tag)
VALUES ('Fantasy'),
       ('Science Fiction'),
       ('Mystery'),
       ('Romance'),
       ('Horror'),
       ('Thriller'),
       ('Historical Fiction'),
       ('Biography'),
       ('Self-help'),
       ('Poetry'),
       ('Adventure'),
       ('Drama'),
       ('Graphic Novel'),
       ('Children\'s Literature'),
       ('Young Adult'),
       ('Dystopian'),
       ('Fairy Tale'),
       ('Memoir'),
       ('Cooking'),
       ('Health & Wellness'),
       ('Psychology'),
       ('Religion'),
       ('Spirituality'),
       ('Travel'),
       ('True Crime'),
       ('Anthology'),
       ('Essay'),
       ('Humor'),
       ('Philosophy'),
       ('Politics'),
       ('Science'),
       ('Sociology'),
       ('Art'),
       ('Photography'),
       ('Music'),
       ('Business'),
       ('Economics'),
       ('Education'),
       ('Technology'),
       ('Law'),
       ('Environmental Studies');

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
    phoneNumber CHAR(10) UNIQUE,
    email VARCHAR(100) UNIQUE,
    country VARCHAR(50),
    state VARCHAR(50),
    status VARCHAR(10) DEFAULT 'pending',
    registeredDate DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (userName, identityCard, phoneNumber, email, country, state)
VALUES ('Nguyen Van A', '012345678901', '0987654321', 'a@example.com', 'Vietnam', 'Hanoi'),
       ('Tran Thi B', '012345678902', '0987654322', 'b@example.com', 'Vietnam', 'Ho Chi Minh City'),
       ('Le Van C', '012345678903', '0987654323', 'c@example.com', 'Vietnam', 'Da Nang'),
       ('Pham Thi D', '012345678904', '0987654324', 'd@example.com', 'Vietnam', 'Hai Phong'),
       ('Hoang Van E', '012345678905', '0987654325', 'e@example.com', 'Vietnam', 'Can Tho'),
       ('Do Thi F', '012345678906', '0987654326', 'f@example.com', 'Vietnam', 'Hue'),
       ('Nguyen Van G', '012345678907', '0987654327', 'g@example.com', 'Vietnam', 'Nha Trang'),
       ('Tran Thi H', '012345678908', '0987654328', 'h@example.com', 'Vietnam', 'Vung Tau'),
       ('Le Van I', '012345678909', '0987654329', 'i@example.com', 'Vietnam', 'Bac Ninh'),
       ('Pham Thi J', '012345678910', '0987654330', 'j@example.com', 'Vietnam', 'Quang Ninh');

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
    status VARCHAR(20) DEFAULT 'Pending',
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
