drop database if exists libdemo;

create database libdemo;
use libdemo;

-- Tạo bảng manager với cột STT là khóa chính và AUTO_INCREMENT
CREATE TABLE manager (
    STT INT AUTO_INCREMENT PRIMARY KEY,
    managerName VARCHAR(100),
    password CHAR(12),
    position VARCHAR(50),
    email VARCHAR(100),
    managerID VARCHAR(50) UNIQUE
);

ALTER TABLE manager AUTO_INCREMENT = 1;

-- Tạo Trigger để tự động cập nhật giá trị cho managerID dựa trên STT
DELIMITER //

CREATE TRIGGER before_insert_manager
BEFORE INSERT ON manager
FOR EACH ROW
BEGIN
    DECLARE next_id INT;

    -- Lấy giá trị AUTO_INCREMENT kế tiếp
    SET next_id = (SELECT AUTO_INCREMENT 
                   FROM information_schema.TABLES 
                   WHERE TABLE_SCHEMA = DATABASE() 
                   AND TABLE_NAME = 'manager');
    
    -- Gán giá trị managerID
    SET NEW.managerID = CONCAT('MNG', next_id);
END;
//
DELIMITER ;



INSERT INTO manager (managerName, password, position)
VALUES ('Tuan Anh', '123', 'Project leader');
INSERT INTO manager (managerName, password, position)
VALUES ('Duc Thien', '123', 'manager');
INSERT INTO manager (managerName, password, position)
VALUES ('Duc Anh', '123', 'manager');
INSERT INTO manager (managerName, password, position)
VALUES ('Nang Diu', '123', 'manager');

-- Tạo bảng genre với STT là khóa chính và AUTO_INCREMENT
CREATE TABLE genre (
    STT INT AUTO_INCREMENT PRIMARY KEY,
    tag VARCHAR(50),
    genreID VARCHAR(50) UNIQUE
);

-- Tạo Trigger để tự động cập nhật giá trị cho genreID dựa trên STT
DELIMITER //
CREATE TRIGGER before_insert_genre
BEFORE INSERT ON genre
FOR EACH ROW
BEGIN
    DECLARE next_id INT;

    -- Lấy giá trị AUTO_INCREMENT kế tiếp từ bảng information_schema
    SET next_id = (SELECT AUTO_INCREMENT 
                   FROM information_schema.TABLES 
                   WHERE TABLE_SCHEMA = DATABASE() 
                   AND TABLE_NAME = 'genre');
    
    -- Gán giá trị genreID với tiền tố 'GEN' và giá trị của next_id
    SET NEW.genreID = CONCAT('GEN', next_id);
END;
//
DELIMITER ;

INSERT INTO genre (tag) VALUES ('Fantasy');
INSERT INTO genre (tag) VALUES ('Science Fiction');
INSERT INTO genre (tag) VALUES ('Mystery');
INSERT INTO genre (tag) VALUES ('Romance');
INSERT INTO genre (tag) VALUES ('Horror');
INSERT INTO genre (tag) VALUES ('Thriller');
INSERT INTO genre (tag) VALUES ('Historical Fiction');
INSERT INTO genre (tag) VALUES ('Biography');
INSERT INTO genre (tag) VALUES ('Self-help');
INSERT INTO genre (tag) VALUES ('Poetry');

select * from genre;

CREATE TABLE language (
    STT SMALLINT AUTO_INCREMENT PRIMARY KEY,
    lgName VARCHAR(50),
    lgID VARCHAR(50) UNIQUE
);

DELIMITER //
CREATE TRIGGER before_insert_language
BEFORE INSERT ON language
FOR EACH ROW
BEGIN
    DECLARE next_id INT;

    -- Lấy giá trị AUTO_INCREMENT kế tiếp từ bảng information_schema
    SET next_id = (SELECT AUTO_INCREMENT 
                   FROM information_schema.TABLES 
                   WHERE TABLE_SCHEMA = DATABASE() 
                   AND TABLE_NAME = 'language');
    
    -- Gán giá trị lgID với tiền tố 'LANG' và giá trị của next_id
    SET NEW.lgID = CONCAT('LANG', next_id);
END;
//
DELIMITER ;



INSERT INTO language (lgName) VALUES ('English');
INSERT INTO language (lgName) VALUES ('Spanish');
INSERT INTO language (lgName) VALUES ('French');
INSERT INTO language (lgName) VALUES ('German');
INSERT INTO language (lgName) VALUES ('Chinese');
INSERT INTO language (lgName) VALUES ('Japanese');
INSERT INTO language (lgName) VALUES ('Russian');
INSERT INTO language (lgName) VALUES ('Portuguese');
INSERT INTO language (lgName) VALUES ('Italian');
INSERT INTO language (lgName) VALUES ('Korean');

select * from language;

CREATE TABLE user (
    STT INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(100),
    address VARCHAR(100),
    identityCard CHAR(12),
    mobile CHAR(10),
    email VARCHAR(100),
    membershipLevel VARCHAR(50),
    userId VARCHAR(50) UNIQUE
);

DELIMITER //
CREATE TRIGGER before_insert_user
BEFORE INSERT ON user
FOR EACH ROW
BEGIN
    DECLARE next_id INT;

    -- Lấy giá trị AUTO_INCREMENT kế tiếp từ bảng information_schema
    SET next_id = (SELECT AUTO_INCREMENT 
                   FROM information_schema.TABLES 
                   WHERE TABLE_SCHEMA = DATABASE() 
                   AND TABLE_NAME = 'user');
    
    -- Gán giá trị userId với tiền tố 'USER' và giá trị của next_id
    SET NEW.userId = CONCAT('USER', next_id);
END;
//
DELIMITER ;


INSERT INTO user (userName, address, identityCard, mobile, email, membershipLevel) 
VALUES ('Nguyen Van A', '123 Street', '012345678901', '0987654321', 'a@example.com', 'Gold');

INSERT INTO user (userName, address, identityCard, mobile, email, membershipLevel) 
VALUES ('Tran Thi B', '456 Avenue', '012345678902', '0987654322', 'b@example.com', 'Silver');

INSERT INTO user (userName, address, identityCard, mobile, email, membershipLevel) 
VALUES ('Le Van C', '789 Road', '012345678903', '0987654323', 'c@example.com', 'Bronze');

INSERT INTO user (userName, address, identityCard, mobile, email, membershipLevel) 
VALUES ('Pham Thi D', '101 Street', '012345678904', '0987654324', 'd@example.com', 'Gold');

INSERT INTO user (userName, address, identityCard, mobile, email, membershipLevel) 
VALUES ('Hoang Van E', '202 Avenue', '012345678905', '0987654325', 'e@example.com', 'Silver');

INSERT INTO user (userName, address, identityCard, mobile, email, membershipLevel) 
VALUES ('Do Thi F', '303 Road', '012345678906', '0987654326', 'f@example.com', 'Bronze');

INSERT INTO user (userName, address, identityCard, mobile, email, membershipLevel) 
VALUES ('Nguyen Van G', '404 Street', '012345678907', '0987654327', 'g@example.com', 'Gold');

INSERT INTO user (userName, address, identityCard, mobile, email, membershipLevel) 
VALUES ('Tran Thi H', '505 Avenue', '012345678908', '0987654328', 'h@example.com', 'Silver');

INSERT INTO user (userName, address, identityCard, mobile, email, membershipLevel) 
VALUES ('Le Van I', '606 Road', '012345678909', '0987654329', 'i@example.com', 'Bronze');

INSERT INTO user (userName, address, identityCard, mobile, email, membershipLevel) 
VALUES ('Pham Thi J', '707 Street', '012345678910', '0987654330', 'j@example.com', 'Gold');

select * from user;

CREATE TABLE document (
    STT INT AUTO_INCREMENT PRIMARY KEY,
    genrId VARCHAR(50),
    publisher VARCHAR(100),
    lgID VARCHAR(50),
    title VARCHAR(100),
    author VARCHAR(100),
    quantity INT,
    availableCopies INT,
    addDate DATETIME,
    price DECIMAL(10,2),
    documentId VARCHAR(50) UNIQUE,
	FOREIGN KEY (genrId) REFERENCES genre(genreID),
    FOREIGN KEY (lgId) REFERENCES language(lgID)
);

DELIMITER //
CREATE TRIGGER before_insert_document
BEFORE INSERT ON document
FOR EACH ROW
BEGIN
    DECLARE next_id INT;

    -- Lấy giá trị AUTO_INCREMENT kế tiếp từ bảng information_schema
    SET next_id = (SELECT AUTO_INCREMENT 
                   FROM information_schema.TABLES 
                   WHERE TABLE_SCHEMA = DATABASE() 
                   AND TABLE_NAME = 'document');
    
    -- Gán giá trị documentId với tiền tố 'DOC' và giá trị của next_id
    SET NEW.documentId = CONCAT('DOC', next_id);
END;
//
DELIMITER ;


INSERT INTO document (genrId, publisher, lgId, title, author, quantity, availableCopies, addDate, price) 
VALUES ('GEN1', 'Penguin Books', 'LANG1', 'document Title 1', 'Author 1', 50, 50, NOW(), 19.99);

INSERT INTO document (genrId, publisher, lgId, title, author, quantity, availableCopies, addDate, price) 
VALUES ('GEN2', 'HarperCollins', 'LANG2', 'document Title 2', 'Author 2', 45, 45, NOW(), 18.99);

INSERT INTO document (genrId, publisher, lgId, title, author, quantity, availableCopies, addDate, price) 
VALUES ('GEN3', 'Simon & Schuster', 'LANG3', 'document Title 3', 'Author 3', 40, 40, NOW(), 20.99);

INSERT INTO document (genrId, publisher, lgId, title, author, quantity, availableCopies, addDate, price) 
VALUES ('GEN1', 'Random House', 'LANG2', 'document Title 4', 'Author 4', 60, 60, NOW(), 21.99);

INSERT INTO document (genrId, publisher, lgId, title, author, quantity, availableCopies, addDate, price) 
VALUES ('GEN2', 'Penguin Books', 'LANG5', 'document Title 5', 'Author 5', 55, 55, NOW(), 22.99);

INSERT INTO document (genrId, publisher, lgId, title, author, quantity, availableCopies, addDate, price) 
VALUES ('GEN3', 'HarperCollins', 'LANG7', 'document Title 6', 'Author 6', 65, 65, NOW(), 17.99);

INSERT INTO document (genrId, publisher, lgId, title, author, quantity, availableCopies, addDate, price) 
VALUES ('GEN1', 'Simon & Schuster', 'LANG4', 'document Title 7', 'Author 7', 70, 70, NOW(), 16.99);

INSERT INTO document (genrId, publisher, lgId, title, author, quantity, availableCopies, addDate, price) 
VALUES ('GEN2', 'Random House', 'LANG1', 'document Title 8', 'Author 8', 80, 80, NOW(), 15.99);

INSERT INTO document (genrId, publisher, lgId, title, author, quantity, availableCopies, addDate, price) 
VALUES ('GEN3', 'Penguin Books', 'LANG5', 'document Title 9', 'Author 9', 90, 90, NOW(), 23.99);

INSERT INTO document (genrId, publisher, lgId, title, author, quantity, availableCopies, addDate, price) 
VALUES ('GEN1', 'HarperCollins', 'LANG10', 'document Title 10', 'Author 10', 100, 100, NOW(), 24.99);

select * from document;

CREATE TABLE loans (
    STT INT AUTO_INCREMENT PRIMARY KEY,
    userId VARCHAR(50),
    quantityOfBorrow SMALLINT,
    deposit DECIMAL(10, 2),
    dateOfBorrow DATETIME,
    requiredReturnDate DATETIME,
    loanID varchar(50) unique,
    -- Khóa ngoại tham chiếu đến userId trong bảng user
    FOREIGN KEY (userId) REFERENCES user(userId)
);

DELIMITER //

CREATE TRIGGER before_insert_loans
BEFORE INSERT ON loans
FOR EACH ROW
BEGIN
    DECLARE next_id INT;

    -- Lấy giá trị AUTO_INCREMENT kế tiếp từ bảng information_schema
    SET next_id = (SELECT AUTO_INCREMENT 
                   FROM information_schema.TABLES 
                   WHERE TABLE_SCHEMA = DATABASE() 
                   AND TABLE_NAME = 'loans');
    
    -- Gán giá trị loanID với tiền tố 'LOAN' và giá trị của next_id
    SET NEW.loanID = CONCAT('LOAN', next_id);
END;
//
DELIMITER ;

INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
VALUES ('USER1', 3, 100.00, '2024-11-02 10:00:00', '2024-12-02 10:00:00');

INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
VALUES ('USER2', 2, 50.00, '2024-11-03 11:00:00', '2024-12-03 11:00:00');

INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
VALUES ('USER3', 5, 150.00, '2024-11-04 12:00:00', '2024-12-04 12:00:00');

INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
VALUES ('USER4', 1, 30.00, '2024-11-05 13:00:00', '2024-12-05 13:00:00');

INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
VALUES ('USER5', 4, 120.00, '2024-11-06 14:00:00', '2024-12-06 14:00:00');

INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
VALUES ('USER6', 2, 60.00, '2024-11-07 15:00:00', '2024-12-07 15:00:00');

INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
VALUES ('USER7', 3, 90.00, '2024-11-08 16:00:00', '2024-12-08 16:00:00');

INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
VALUES ('USER8', 1, 40.00, '2024-11-09 17:00:00', '2024-12-09 17:00:00');

INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
VALUES ('USER9', 6, 200.00, '2024-11-10 18:00:00', '2024-12-10 18:00:00');

INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
VALUES ('USER10', 2, 70.00, '2024-11-11 19:00:00', '2024-12-11 19:00:00');

select * from loans;

CREATE TABLE loandetail (
    STT INT AUTO_INCREMENT PRIMARY KEY,
    loanId VARCHAR(50),
    documentId VARCHAR(50),
    quantity SMALLINT,
    loanDetailID VARCHAR(50) UNIQUE,
    -- Khóa ngoại tham chiếu đến loanId trong bảng loans
    FOREIGN KEY (loanId) REFERENCES loans(loanId),
    -- Khóa ngoại tham chiếu đến documentId trong bảng document
    FOREIGN KEY (documentId) REFERENCES document(documentId)
);

DELIMITER //

CREATE TRIGGER before_insert_loandetail
BEFORE INSERT ON loandetail
FOR EACH ROW
BEGIN
    DECLARE next_id INT;

    -- Lấy giá trị AUTO_INCREMENT kế tiếp từ bảng information_schema
    SET next_id = (SELECT AUTO_INCREMENT 
                   FROM information_schema.TABLES 
                   WHERE TABLE_SCHEMA = DATABASE() 
                   AND TABLE_NAME = 'loandetail');
    
    -- Gán giá trị loanDetailID với tiền tố 'DETAIL' và giá trị của next_id
    SET NEW.loanDetailID = CONCAT('DETAIL', next_id);
END;
//
DELIMITER ;


INSERT INTO loandetail (loanId, documentId, quantity) 
VALUES ('LOAN1', 'DOC1', 1);

INSERT INTO loandetail (loanId, documentId, quantity) 
VALUES ('LOAN2', 'DOC2', 2);

INSERT INTO loandetail (loanId, documentId, quantity) 
VALUES ('LOAN3', 'DOC3', 1);

INSERT INTO loandetail (loanId, documentId, quantity) 
VALUES ('LOAN4', 'DOC4', 1);

INSERT INTO loandetail (loanId, documentId, quantity) 
VALUES ('LOAN5', 'DOC5', 3);

INSERT INTO loandetail (loanId, documentId, quantity) 
VALUES ('LOAN3', 'DOC6', 1);

INSERT INTO loandetail (loanId, documentId, quantity) 
VALUES ('LOAN7', 'DOC7', 2);

INSERT INTO loandetail (loanId, documentId, quantity) 
VALUES ('LOAN8', 'DOC8', 1);

INSERT INTO loandetail (loanId, documentId, quantity) 
VALUES ('LOAN8', 'DOC9', 1);

INSERT INTO loandetail (loanId, documentId, quantity) 
VALUES ('LOAN9', 'DOC10', 2);

select * from loandetail;
