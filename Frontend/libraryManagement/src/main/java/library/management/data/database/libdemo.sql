DROP
DATABASE IF EXISTS libdemo;
CREATE
DATABASE libdemo;
USE
libdemo;

-- Tạo bảng manager với cột managerID là khóa chính và AUTO_INCREMENT
CREATE TABLE manager
(
    managerID    INT AUTO_INCREMENT PRIMARY KEY,
    managerName  VARCHAR(100) UNIQUE,
    identityCard VARCHAR(20) UNIQUE,
    password     CHAR(12)            NOT NULL,
    email        VARCHAR(100) UNIQUE not null,
    phoneNumber  VARCHAR(15) UNIQUE
);

ALTER TABLE manager AUTO_INCREMENT = 1;

INSERT INTO manager (managerName, email, password)
VALUES ('Tuan Anh', 'a', '123'),
       ('Duc Thien', 'pdthien4325@gmail.com', '123'),
       ('Duc Anh', 'b', '123'),
       ('Nang Diu', 'c', '123');

SELECT *
FROM manager;

-- Tạo bảng category với categoryID là khóa chính và AUTO_INCREMENT
CREATE TABLE category
(
    categoryID INT AUTO_INCREMENT PRIMARY KEY,
    tag        VARCHAR(50) UNIQUE NOT NULL
);

INSERT INTO category (tag)
VALUES ('Fantasy');

SELECT *
FROM category;

-- Tạo bảng language với lgID là khóa chính và AUTO_INCREMENT
CREATE TABLE language
(
    lgID   SMALLINT AUTO_INCREMENT PRIMARY KEY,
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

SELECT *
FROM language;

-- Tạo bảng user với userName là khóa chính
CREATE TABLE user
(
    userName       VARCHAR(100) PRIMARY KEY,
    identityCard   CHAR(12) UNIQUE,
    phoneNumber    CHAR(10),
    email          VARCHAR(100) UNIQUE,
    password       varchar(100) not null,
    country        VARCHAR(50) default 'not set',
    state          VARCHAR(50) default 'not set',
    status         VARCHAR(10) DEFAULT 'pending',
    registeredDate DATETIME    DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO user (userName, identityCard, phoneNumber, email, password, country, state)
VALUES ('Tran Thi B', '012345678902', '0987654322', 'b@example.com', 'password456', 'Vietnam',
        'Ho Chi Minh City');

INSERT INTO user (userName, identityCard, phoneNumber, email, password)
VALUES ('Nguyen Van A', '123456789', '0987654321', 'a@example.com', 'password123');

SELECT *
FROM user;

-- Tạo bảng document với khóa chính và khóa ngoại
CREATE TABLE document
(
    documentId      INT AUTO_INCREMENT PRIMARY KEY,
    categoryID      INT,
    publisher       VARCHAR(100),
    lgID            SMALLINT,
    title           VARCHAR(100)       NOT NULL,
    author          VARCHAR(100)       NOT NULL,
    isbn            VARCHAR(30) UNIQUE NOT NULL,
    description     TEXT,
    url             TEXT,
    image           TEXT,
    quantity        INT                NOT NULL,
    availableCopies INT                NOT NULL,
    addDate         DATETIME    DEFAULT CURRENT_TIMESTAMP,
    price           DECIMAL(10, 2)     NOT NULL,
    availability    VARCHAR(15) DEFAULT 'available',
    FOREIGN KEY (categoryId) REFERENCES category (categoryID),
    FOREIGN KEY (lgId) REFERENCES language (lgID)
);

SELECT *
FROM document;

-- Tạo bảng loans với khóa chính và khóa ngoại
CREATE TABLE loans
(
    loanID             INT AUTO_INCREMENT PRIMARY KEY,
    userName           VARCHAR(100),
    documentId         INT,
    quantityOfBorrow   SMALLINT       NOT NULL,
    deposit            DECIMAL(10, 2) NOT NULL,
    dateOfBorrow       DATETIME    DEFAULT CURRENT_TIMESTAMP,
    requiredReturnDate DATETIME       NOT NULL,
    returnDate         DATETIME    DEFAULT NULL,
    status             VARCHAR(20) DEFAULT 'pending',
    FOREIGN KEY (userName) REFERENCES user (userName),
    FOREIGN KEY (documentId) REFERENCES document (documentId)
);

SELECT *
FROM loans;

-- Tạo bảng suggestion
CREATE TABLE suggestion
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    value     VARCHAR(255) UNIQUE NOT NULL,
    frequency INT                 NOT NULL
);

SELECT *
FROM suggestion;

INSERT INTO manager (managerName, email, password, identityCard, phoneNumber)
VALUES ('Minh Hoang', 'hoang@example.com', '123', '012345678900', '0911222333'),
       ('Anh Khoa', 'khoa@example.com', '123', '012345678901', '0911222444');

INSERT INTO category (tag)
VALUES ('Science Fiction'),
       ('Self-help'),
       ('Technology'),
       ('History'),
       ('Mystery');

INSERT INTO language (lgName)
VALUES ('Vietnamese'),
       ('Arabic'),
       ('Hindi');

INSERT INTO user (userName, identityCard, phoneNumber, email, password, country, state)
VALUES ('Le Minh', '012345678910', '0912345678', 'minh@example.com', 'pwd123', 'Vietnam',
        'Da Nang'),
       ('Pham Hoa', '012345678911', '0912345679', 'hoa@example.com', 'pwd123', 'Vietnam', 'Ha Noi'),
       ('Vo Cuong', '012345678912', '0912345680', 'cuong@example.com', 'pwd123', 'Vietnam', 'Hue');

INSERT INTO document (categoryID, publisher, lgID, title, author, isbn, description, quantity,
                      availableCopies, price)
VALUES (1, 'NXB Kim Dong', 11, 'Harry Potter', 'J.K. Rowling', 'ISBN-HP-001', 'Fantasy novel', 10,
        10, 120000),
       (3, 'Reilly Media', 13, 'Learning Java', 'Marc Loy', 'ISBN-JAVA-101',
        'Java programming guide', 7, 7, 450000),
       (2, 'Penguin Books', 1, 'Atomic Habits', 'James Clear', 'ISBN-AH-123', 'Self-help habits', 5,
        5, 200000),
       (5, 'NXB Tre', 11, 'Detective Conan', 'Gosho Aoyama', 'ISBN-CN-777', 'Mystery manga', 20, 20,
        80000),
       (4, 'HarperCollins', 1, 'World History', 'Edward Burns', 'ISBN-WH-555', 'History overview',
        4, 4, 350000);

INSERT INTO loans (userName, documentId, quantityOfBorrow, deposit, requiredReturnDate, status)
VALUES ('Le Minh', 1, 1, 50000, '2025-12-10', 'pending'),
       ('Pham Hoa', 2, 1, 100000, '2025-12-05', 'approved'),
       ('Nguyen Van A', 3, 1, 80000, '2025-11-25', 'returned'),
       ('Vo Cuong', 4, 2, 60000, '2025-12-15', 'pending'),
       ('Tran Thi B', 5, 1, 120000, '2025-11-20', 'late');
INSERT INTO suggestion (value, frequency)
VALUES ('Harry Potter', 12),
       ('Atomic Habits', 7),
       ('Java Programming', 9),
       ('Mystery Books', 3),
       ('World History', 4);

INSERT INTO user (userName, identityCard, phoneNumber, email, password, country, state)
VALUES ('Hoang Nam', '012345678913', '0912000001', 'nam@example.com', 'pwd123', 'Vietnam',
        'Ha Noi'),
       ('Ngoc Lan', '012345678914', '0912000002', 'lan@example.com', 'pwd123', 'Vietnam',
        'Ho Chi Minh'),
       ('Thanh Tung', '012345678915', '0912000003', 'tung@example.com', 'pwd123', 'Vietnam',
        'Da Nang'),
       ('Minh Chau', '012345678916', '0912000004', 'chau@example.com', 'pwd123', 'Vietnam',
        'Hai Phong'),
       ('My Duyen', '012345678917', '0912000005', 'duyen@example.com', 'pwd123', 'Vietnam', 'Hue'),
       ('Gia Huy', '012345678918', '0912000006', 'huy@example.com', 'pwd123', 'Vietnam', 'Can Tho'),
       ('Bao Tran', '012345678919', '0912000007', 'tran@example.com', 'pwd123', 'Vietnam',
        'Nha Trang'),
       ('Quoc Viet', '012345678920', '0912000008', 'viet@example.com', 'pwd123', 'Vietnam',
        'Quang Ninh'),
       ('Thuy Tien', '012345678921', '0912000009', 'tien@example.com', 'pwd123', 'Vietnam',
        'Vung Tau'),
       ('Khanh Linh', '012345678922', '0912000010', 'linh@example.com', 'pwd123', 'Vietnam',
        'Thai Nguyen');

INSERT INTO user (userName, identityCard, phoneNumber, email, password, country, state)
VALUES
    ('tuan anh', '012345678923', '0912000011', 'phuc@example.com', 'user123', 'Vietnam', 'Ha Noi'),
    ('Tran Minh Hoang', '012345678924', '0912000012', 'hoang@example.com', 'pwd123', 'Vietnam', 'Ha Noi'),
    ('Pham Tuan Kiet', '012345678925', '0912000013', 'kiet@example.com', 'pwd123', 'Vietnam', 'Da Nang'),
    ('Nguyen Hong Son', '012345678926', '0912000014', 'son@example.com', 'pwd123', 'Vietnam', 'Da Nang'),
    ('Vu Ngoc Hien', '012345678927', '0912000015', 'hien@example.com', 'pwd123', 'Vietnam', 'Ha Noi'),
    ('Pham Bao Long', '012345678928', '0912000016', 'long@example.com', 'pwd123', 'Vietnam', 'Hai Phong'),
    ('Doan Thi My', '012345678929', '0912000017', 'my@example.com', 'pwd123', 'Vietnam', 'Hue'),
    ('Ly Hoang Nhu', '012345678930', '0912000018', 'nhu@example.com', 'pwd123', 'Vietnam', 'Ho Chi Minh'),
    ('Ho Dang Khoa', '012345678931', '0912000019', 'khoa@example.com', 'pwd123', 'Vietnam', 'Ho Chi Minh'),
    ('Tran Bao Nghi', '012345678932', '0912000020', 'nghi@example.com', 'pwd123', 'Vietnam', 'Binh Duong'),

    ('Nguyen Thi Kim', '012345678933', '0912000021', 'kim@example.com', 'pwd123', 'Vietnam', 'Quang Ninh'),
    ('Ngo Minh Huy', '012345678934', '0912000022', 'mhuy@example.com', 'pwd123', 'Vietnam', 'Thanh Hoa'),
    ('Pham Hong Dao', '012345678935', '0912000023', 'dao@example.com', 'pwd123', 'Vietnam', 'Nghe An'),
    ('Bui Ngoc Anh', '012345678936', '0912000024', 'ngocanh@example.com', 'pwd123', 'Vietnam', 'Ha Tinh'),
    ('Pham Hai Yen', '012345678937', '0912000025', 'yen@example.com', 'pwd123', 'Vietnam', 'Thai Nguyen'),
    ('Pham Dan Thanh', '012345678938', '0912000026', 'danthanh@example.com', 'pwd123', 'Vietnam', 'Dak Lak'),
    ('Nguyen Gia Phat', '012345678939', '0912000027', 'phat@example.com', 'pwd123', 'Vietnam', 'Kien Giang'),
    ('Tran Bao Hieu', '012345678940', '0912000028', 'hieu@example.com', 'pwd123', 'Vietnam', 'Can Tho'),
    ('Le Minh Phuong', '012345678941', '0912000029', 'phuong@example.com', 'pwd123', 'Vietnam', 'Soc Trang'),
    ('Ho Ngoc Thao', '012345678942', '0912000030', 'thao@example.com', 'pwd123', 'Vietnam', 'Tay Ninh'),

    ('Nguyen Van Tai', '012345678943', '0912000031', 'tai@example.com', 'pwd123', 'Vietnam', 'Dong Nai'),
    ('Tran Van Minh', '012345678944', '0912000032', 'minhvan@example.com', 'pwd123', 'Vietnam', 'Bac Ninh'),
    ('Do Thanh Ha', '012345678945', '0912000033', 'thanha@example.com', 'pwd123', 'Vietnam', 'Hai Duong'),
    ('Vo Quang Trung', '012345678946', '0912000034', 'trung@example.com', 'pwd123', 'Vietnam', 'Lam Dong'),
    ('Ly Thanh Tuan', '012345678947', '0912000035', 'thanhtuan@example.com', 'pwd123', 'Vietnam', 'Phu Tho'),
    ('Pham Bao Chau', '012345678948', '0912000036', 'baochau@example.com', 'pwd123', 'Vietnam', 'Yen Bai'),
    ('Mai Hong Ngoc', '012345678949', '0912000037', 'hongngoc@example.com', 'pwd123', 'Vietnam', 'Lang Son'),
    ('Nguyen Hong Han', '012345678950', '0912000038', 'han@example.com', 'pwd123', 'Vietnam', 'Hue'),
    ('Dang Gia Minh', '012345678951', '0912000039', 'giaminh@example.com', 'pwd123', 'Vietnam', 'Quang Nam'),
    ('Vo Nhat Lam', '012345678952', '0912000040', 'nhatlam@example.com', 'pwd123', 'Vietnam', 'Da Nang');

INSERT INTO document (categoryID, publisher, lgID, title, author, isbn, description, url, image, quantity, availableCopies, price)
VALUES
    (1, 'Penguin Books', 1, 'The Hobbit', 'J.R.R. Tolkien', '9780618260300', 'A fantasy adventure', NULL, NULL, 10, 8, 15.99),
    (1, 'HarperCollins', 1, 'The Witcher: Blood of Elves', 'A. Sapkowski', '9780316029186', 'Witcher saga novel', NULL, NULL, 7, 3, 18.50),
    (1, 'Bloomsbury', 1, 'Harry Potter and the Sorcerer’s Stone', 'J.K. Rowling', '9780590353427', 'Wizarding world begins', NULL, NULL, 12, 10, 25.99),
    (1, 'Tor Books', 1, 'Mistborn', 'Brandon Sanderson', '9780765311788', 'Epic fantasy rebellion', NULL, NULL, 6, 2, 22.00),
    (1, 'Orbit', 1, 'The Way of Kings', 'Brandon Sanderson', '9780765326355', 'Stormlight Archive book 1', NULL, NULL, 8, 6, 28.50),
    (1, 'Tor Books', 1, 'The Name of the Wind', 'Patrick Rothfuss', '9780756404741', 'Kingkiller Chronicle', NULL, NULL, 5, 1, 20.40),
    (1, 'Del Rey', 1, 'Ready Player One', 'Ernest Cline', '9780307887443', 'OASIS adventure', NULL, NULL, 9, 4, 19.20),
    (1, 'Riverhead Books', 1, 'The Alchemist', 'Paulo Coelho', '9780062315007', 'Spiritual journey', NULL, NULL, 10, 7, 14.99),
    (1, 'Random House', 1, 'Game of Thrones', 'George R.R. Martin', '9780553573404', 'Song of Ice and Fire', NULL, NULL, 6, 3, 30.00),
    (1, 'Vintage', 1, 'Norwegian Wood', 'Haruki Murakami', '9780099448822', 'Japanese romance', NULL, NULL, 4, 2, 16.40),

    (1, 'Alpha Books', 1, 'The Martian', 'Andy Weir', '9780804139021', 'Survival on Mars', NULL, NULL, 3, 1, 17.99),
    (1, 'Picador', 1, 'Life of Pi', 'Yann Martel', '9780156027328', 'Boy stranded with tiger', NULL, NULL, 7, 5, 12.75),
    (1, 'Penguin', 1, 'Dune', 'Frank Herbert', '9780441172719', 'Sci-fi epic', NULL, NULL, 11, 6, 26.90),
    (1, 'Vintage', 1, '1984', 'George Orwell', '9780451524935', 'Dystopian future', NULL, NULL, 9, 3, 13.20),
    (1, 'Vintage', 1, 'Animal Farm', 'George Orwell', '9780451526342', 'Political allegory', NULL, NULL, 6, 1, 10.50),
    (1, 'Penguin', 1, 'Lord of the Flies', 'William Golding', '9780399501487', 'Civilization vs savagery', NULL, NULL, 6, 4, 11.99),
    (1, 'Dell', 1, 'It', 'Stephen King', '9781501142970', 'Horror thriller', NULL, NULL, 8, 6, 18.10),
    (1, 'Dell', 1, 'Carrie', 'Stephen King', '9780307743665', 'Telekinetic girl', NULL, NULL, 5, 2, 14.45),
    (1, 'Orbit', 1, 'Leviathan Wakes', 'James S.A. Corey', '9780316129084', 'The Expanse series', NULL, NULL, 7, 3, 23.30),
    (1, 'Tor Books', 1, 'Old Man’s War', 'John Scalzi', '9780765348272', 'Military sci-fi', NULL, NULL, 6, 2, 16.90);


INSERT INTO loans (userName, documentId, quantityOfBorrow, deposit, requiredReturnDate, returnDate, status)
VALUES
    ('Tran Minh Hoang', 1, 1, 50.00, '2025-01-20', NULL, 'pending'),
    ('Nguyen Van A', 3, 1, 40.00, '2025-01-15', '2025-01-10', 'returned'),
    ('Pham Tuan Kiet', 2, 1, 55.00, '2025-01-12', '2025-01-12', 'returned'),
    ('Vu Ngoc Hien', 10, 2, 80.00, '2025-01-18', NULL, 'pending'),
    ('Ly Hoang Nhu', 9, 1, 65.00, '2025-01-25', NULL, 'overdue'),
    ('Ho Dang Khoa', 11, 1, 45.00, '2025-01-10', '2025-01-09', 'returned'),
    ('Tran Bao Nghi', 6, 1, 50.00, '2025-01-17', NULL, 'pending'),
    ('Pham Hai Yen', 20, 2, 90.00, '2025-01-22', NULL, 'pending'),
    ('Dang Gia Minh', 15, 1, 35.00, '2025-01-13', '2025-01-12', 'returned'),

    ('Nguyen Hong Son', 4, 1, 60.00, '2025-01-18', NULL, 'pending'),
    ('Doan Thi My', 13, 1, 75.00, '2025-01-26', NULL, 'pending'),
    ('Nguyen Gia Phat', 14, 1, 40.00, '2025-01-11', '2025-01-11', 'returned'),
    ('Le Minh Phuong', 17, 1, 55.00, '2025-01-19', NULL, 'overdue'),
    ('Vo Nhat Lam', 19, 1, 60.00, '2025-01-15', NULL, 'pending'),
    ('Tran Van Minh', 7, 1, 50.00, '2025-01-17', NULL, 'pending'),
    ('Pham Bao Chau', 8, 1, 70.00, '2025-01-23', NULL, 'pending'),
    ('Mai Hong Ngoc', 16, 2, 85.00, '2025-01-14', '2025-01-13', 'returned'),
    ('Nguyen Hong Han', 12, 1, 35.00, '2025-01-21', NULL, 'pending'),
    ('Bui Ngoc Anh', 18, 1, 45.00, '2025-01-27', NULL, 'pending');



INSERT INTO suggestion (value, frequency)
VALUES
    ('Harry Potter', 12), ('Hobbit', 8), ('Game of Thrones', 5), ('Dune', 7),
    ('Fantasy', 14), ('Sci-fi', 11), ('Horror', 6), ('Library', 9),
    ('Borrow Book', 4), ('Return Book', 3), ('Overdue', 10), ('Author', 2),
    ('ISBN', 5), ('User Login', 7), ('Loan Status', 3);
