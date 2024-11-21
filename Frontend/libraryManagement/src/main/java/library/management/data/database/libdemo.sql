drop database if exists libdemo;

create database libdemo;
use libdemo;

-- Tạo bảng manager với cột STT là khóa chính và AUTO_INCREMENT
CREATE TABLE manager (
                         managerID INT AUTO_INCREMENT PRIMARY KEY,
                         managerName VARCHAR(100) unique,
                         identityCard varchar(20) unique,
                         password CHAR(12),
                         email VARCHAR(100)
);

ALTER TABLE manager AUTO_INCREMENT = 1;

INSERT INTO manager (managerName, password)
VALUES ('Tuan Anh', '123');
INSERT INTO manager (managerName, password)
VALUES ('Duc Thien', '123');
INSERT INTO manager (managerName, password)
VALUES ('Duc Anh', '123');
INSERT INTO manager (managerName, password)
VALUES ('Nang Diu', '123');

select * from manager;

-- Tạo bảng category với STT là khóa chính và AUTO_INCREMENT
CREATE TABLE category (
                          categoryID INT AUTO_INCREMENT PRIMARY KEY,
                          tag VARCHAR(50) unique
);

INSERT INTO category (tag) VALUES ('Fantasy');
INSERT INTO category (tag) VALUES ('Science Fiction');
INSERT INTO category (tag) VALUES ('Mystery');
INSERT INTO category (tag) VALUES ('Romance');
INSERT INTO category (tag) VALUES ('Horror');
INSERT INTO category (tag) VALUES ('Thriller');
INSERT INTO category (tag) VALUES ('Historical Fiction');
INSERT INTO category (tag) VALUES ('Biography');
INSERT INTO category (tag) VALUES ('Self-help');
INSERT INTO category (tag) VALUES ('Poetry');
INSERT INTO category (tag) VALUES ('Adventure');
INSERT INTO category (tag) VALUES ('Drama');
INSERT INTO category (tag) VALUES ('Graphic Novel');
INSERT INTO category (tag) VALUES ('Children\'s Literature');
INSERT INTO category (tag) VALUES ('Young Adult');
INSERT INTO category (tag) VALUES ('Dystopian');
INSERT INTO category (tag) VALUES ('Fairy Tale');
INSERT INTO category (tag) VALUES ('Memoir');
INSERT INTO category (tag) VALUES ('Cooking');
INSERT INTO category (tag) VALUES ('Health & Wellness');
INSERT INTO category (tag) VALUES ('Psychology');
INSERT INTO category (tag) VALUES ('Religion');
INSERT INTO category (tag) VALUES ('Spirituality');
INSERT INTO category (tag) VALUES ('Travel');
INSERT INTO category (tag) VALUES ('True Crime');
INSERT INTO category (tag) VALUES ('Anthology');
INSERT INTO category (tag) VALUES ('Essay');
INSERT INTO category (tag) VALUES ('Humor');
INSERT INTO category (tag) VALUES ('Philosophy');
INSERT INTO category (tag) VALUES ('Politics');
INSERT INTO category (tag) VALUES ('Science');
INSERT INTO category (tag) VALUES ('Sociology');
INSERT INTO category (tag) VALUES ('Art');
INSERT INTO category (tag) VALUES ('Photography');
INSERT INTO category (tag) VALUES ('Music');
INSERT INTO category (tag) VALUES ('Business');
INSERT INTO category (tag) VALUES ('Economics');
INSERT INTO category (tag) VALUES ('Education');
INSERT INTO category (tag) VALUES ('Technology');
INSERT INTO category (tag) VALUES ('Law');
INSERT INTO category (tag) VALUES ('Environmental Studies');


select * from category;

CREATE TABLE language (
    lgID SMALLINT AUTO_INCREMENT PRIMARY KEY,
    lgName VARCHAR(50)
);

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
    userId INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(100),
    identityCard CHAR(12),
    phoneNumber CHAR(10),
    email VARCHAR(100),
    country VARCHAR(50),
    state VARCHAR(50),
    status VARCHAR(10) DEFAULT 'pending',
    registeredDate DATETIME DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO user (userName, identityCard, phoneNumber, email, country, state)
VALUES ('Nguyen Van A', '012345678901', '0987654321', 'a@example.com', 'Vietnam', 'Hanoi');

INSERT INTO user (userName, identityCard, phoneNumber, email, country, state)
VALUES ('Tran Thi B', '012345678902', '0987654322', 'b@example.com', 'Vietnam', 'Ho Chi Minh City');

INSERT INTO user (userName, identityCard, phoneNumber, email, country, state)
VALUES ('Le Van C', '012345678903', '0987654323', 'c@example.com', 'Vietnam', 'Da Nang');

INSERT INTO user (userName, identityCard, phoneNumber, email, country, state)
VALUES ('Pham Thi D', '012345678904', '0987654324', 'd@example.com', 'Vietnam', 'Hai Phong');

INSERT INTO user (userName, identityCard, phoneNumber, email, country, state)
VALUES ('Hoang Van E', '012345678905', '0987654325', 'e@example.com', 'Vietnam', 'Can Tho');

INSERT INTO user (userName, identityCard, phoneNumber, email, country, state)
VALUES ('Do Thi F', '012345678906', '0987654326', 'f@example.com', 'Vietnam', 'Hue');

INSERT INTO user (userName, identityCard, phoneNumber, email, country, state)
VALUES ('Nguyen Van G', '012345678907', '0987654327', 'g@example.com', 'Vietnam', 'Nha Trang');

INSERT INTO user (userName, identityCard, phoneNumber, email, country, state)
VALUES ('Tran Thi H', '012345678908', '0987654328', 'h@example.com', 'Vietnam', 'Vung Tau');

INSERT INTO user (userName, identityCard, phoneNumber, email, country, state)
VALUES ('Le Van I', '012345678909', '0987654329', 'i@example.com', 'Vietnam', 'Bac Ninh');

INSERT INTO user (userName, identityCard, phoneNumber, email, country, state)
VALUES ('Pham Thi J', '012345678910', '0987654330', 'j@example.com', 'Vietnam', 'Quang Ninh');

select * from user;

CREATE TABLE document (
    documentId INT AUTO_INCREMENT PRIMARY KEY,
    categoryID INT,
    publisher VARCHAR(100),
    lgID SMALLINT,
    title VARCHAR(100),
    author VARCHAR(100),
    isbn VARCHAR(13) UNIQUE NOT NULL,
    description TEXT,
    url TEXT,
    image TEXT,
    quantity INT,
    availableCopies INT,
    addDate DATETIME,
    price DECIMAL(10,2),
    availability BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (categoryId) REFERENCES category(categoryID),
    FOREIGN KEY (lgId) REFERENCES language(lgID)
);


INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('1', 'Oxford University Press', '1', 'Advanced Mathematics', 'John Doe', '9783161484100', 'A comprehensive guide to advanced mathematics.', 'https://example.com/book1', 'https://example.com/image1.jpg', 25, 25, NOW(), 30.50);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('2', 'Penguin Random House', '2', 'The Great Adventure', 'Emily Blunt', '9780143127796', 'An epic adventure across unknown lands.', 'https://example.com/book2', 'https://example.com/image2.jpg', 15, 15, NOW(), 20.99);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('3', 'Macmillan', '3', 'Mystery of the Lost Island', 'Arthur Conan', '9780316769488', 'A thrilling mystery set on a deserted island.', 'https://example.com/book3', 'https://example.com/image3.jpg', 35, 35, NOW(), 18.75);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('4', 'HarperCollins', '4', 'Romantic Encounters', 'Alice Parker', '9780062315007', 'A tale of love and romance.', 'https://example.com/book4', 'https://example.com/image4.jpg', 40, 40, NOW(), 22.50);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('5', 'Simon & Schuster', '5', 'Horror Nights', 'Stephen King', '9781501142970', 'Spine-chilling horror stories.', 'https://example.com/book5', 'https://example.com/image5.jpg', 50, 50, NOW(), 27.99);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('6', 'Bloomsbury', '6', 'Historical Tales', 'William Stone', '9781408855713', 'Stories from the past brought to life.', 'https://example.com/book6', 'https://example.com/image6.jpg', 20, 20, NOW(), 15.60);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('7', 'Scholastic', '7', 'Science and You', 'Dr. Susan Reed', '9780545010221', 'An introduction to the wonders of science.', 'https://example.com/book7', 'https://example.com/image7.jpg', 30, 30, NOW(), 12.80);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('8', 'Wiley', '8', 'Biography of Albert Einstein', 'Richard Roe', '9781118454465', 'The life story of the great physicist.', 'https://example.com/book8', 'https://example.com/image8.jpg', 10, 10, NOW(), 35.00);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('9', 'Cambridge University Press', '9', 'Self-Improvement 101', 'Emma Stone', '9780521671018', 'A guide to self-improvement techniques.', 'https://example.com/book9', 'https://example.com/image9.jpg', 60, 60, NOW(), 18.90);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('10', 'Hachette Livre', '10', 'Poems of the Century', 'Robert Frost', '9780316017337', 'A collection of timeless poems.', 'https://example.com/book10', 'https://example.com/image10.jpg', 25, 25, NOW(), 24.75);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('1', 'Pearson', '1', 'Basic English Grammar', 'Martha Collins', '9780134661138', 'An essential guide to English grammar.', 'https://example.com/book11', 'https://example.com/image11.jpg', 70, 70, NOW(), 12.40);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('2', 'HarperCollins', '2', 'Journey to the West', 'Li Bai', '9780062061249', 'A journey through mythical lands.', 'https://example.com/book12', 'https://example.com/image12.jpg', 55, 55, NOW(), 21.95);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('3', 'Penguin Random House', '3', 'Detective Stories', 'Arthur Doyle', '9780241978897', 'Classic detective tales.', 'https://example.com/book13', 'https://example.com/image13.jpg', 35, 35, NOW(), 19.50);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('4', 'Simon & Schuster', '4', 'Romantic Poetry', 'Elizabeth Browning', '9781982107331', 'A collection of romantic poems.', 'https://example.com/book14', 'https://example.com/image14.jpg', 45, 45, NOW(), 14.99);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('5', 'Houghton Mifflin', '5', 'Haunted Houses', 'John Carpenter', '9780395915246', 'Stories of haunted houses.', 'https://example.com/book15', 'https://example.com/image15.jpg', 50, 50, NOW(), 26.80);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('7', 'Scholastic', '7', 'Physics for Beginners', 'Isaac Newton', '9780439878128', 'An introductory book on physics.', 'https://example.com/book16', 'https://example.com/image16.jpg', 40, 40, NOW(), 29.99);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('8', 'Penguin Random House', '8', 'Autobiography of a Yogi', 'Paramahansa Yogananda', '9780876120835', 'The life story of a spiritual teacher.', 'https://example.com/book17', 'https://example.com/image17.jpg', 15, 15, NOW(), 38.50);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('9', 'Cambridge University Press', '9', 'Effective Communication', 'John Maxwell', '9781400204607', 'Tips and strategies for effective communication.', 'https://example.com/book18', 'https://example.com/image18.jpg', 60, 60, NOW(), 20.00);

INSERT INTO document (categoryId, publisher, lgId, title, author, isbn, description, url, image, quantity, availableCopies, addDate, price)
VALUES ('10', 'Oxford University Press', '10', 'Collected Poems', 'Sylvia Plath', '9780062445247', 'A collection of poems by Sylvia Plath.', 'https://example.com/book19', 'https://example.com/image19.jpg', 35, 35, NOW(), 23.95);

select * from document;

CREATE TABLE loans (
    loanID INT AUTO_INCREMENT PRIMARY KEY,
    userId int,
    quantityOfBorrow SMALLINT,
    deposit DECIMAL(10, 2),
    dateOfBorrow DATETIME,
    requiredReturnDate DATETIME,
    -- Khóa ngoại tham chiếu đến userId trong bảng user
    FOREIGN KEY (userId) REFERENCES user(userId) ON DELETE SET NULL
);

-- INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
-- VALUES ('1', 3, 100.00, '2024-11-02 10:00:00', '2024-12-02 10:00:00');

-- INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
-- VALUES ('2', 2, 50.00, '2024-11-03 11:00:00', '2024-12-03 11:00:00');

-- INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
-- VALUES ('3', 5, 150.00, '2024-11-04 12:00:00', '2024-12-04 12:00:00');

-- INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
-- VALUES ('4', 1, 30.00, '2024-11-05 13:00:00', '2024-12-05 13:00:00');

-- INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
-- VALUES ('5', 4, 120.00, '2024-11-06 14:00:00', '2024-12-06 14:00:00');

-- INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
-- VALUES ('6', 2, 60.00, '2024-11-07 15:00:00', '2024-12-07 15:00:00');

-- INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
-- VALUES ('7', 3, 90.00, '2024-11-08 16:00:00', '2024-12-08 16:00:00');

-- INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
-- VALUES ('8', 1, 40.00, '2024-11-09 17:00:00', '2024-12-09 17:00:00');

-- INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
-- VALUES ('9', 6, 200.00, '2024-11-10 18:00:00', '2024-12-10 18:00:00');

-- INSERT INTO loans (userId, quantityOfBorrow, deposit, dateOfBorrow, requiredReturnDate)
-- VALUES ('10', 2, 70.00, '2024-11-11 19:00:00', '2024-12-11 19:00:00');

select * from loans;

CREATE TABLE loandetail (
    loanDetailID INT AUTO_INCREMENT PRIMARY KEY,
    loanId int,
    documentId int,
    quantity SMALLINT,
    -- Khóa ngoại tham chiếu đến loanId trong bảng loans
    FOREIGN KEY (loanId) REFERENCES loans(loanId),
    -- Khóa ngoại tham chiếu đến documentId trong bảng document
    FOREIGN KEY (documentId) REFERENCES document(documentId) on delete set null
);


-- INSERT INTO loandetail (loanId, documentId, quantity)
-- VALUES ('1', '1', 1);

-- INSERT INTO loandetail (loanId, documentId, quantity)
-- VALUES ('2', '2', 2);

-- INSERT INTO loandetail (loanId, documentId, quantity)
-- VALUES ('3', '3', 1);

-- INSERT INTO loandetail (loanId, documentId, quantity)
-- VALUES ('4', '4', 1);

-- INSERT INTO loandetail (loanId, documentId, quantity)
-- VALUES ('5', '5', 3);

-- INSERT INTO loandetail (loanId, documentId, quantity)
-- VALUES ('3', '6', 1);

-- INSERT INTO loandetail (loanId, documentId, quantity)
-- VALUES ('7', '7', 2);

-- INSERT INTO loandetail (loanId, documentId, quantity)
-- VALUES ('8', '8', 1);

-- INSERT INTO loandetail (loanId, documentId, quantity)
-- VALUES ('8', '9', 1);

-- INSERT INTO loandetail (loanId, documentId, quantity)
-- VALUES ('9', '10', 2);

-- select * from loandetail;
