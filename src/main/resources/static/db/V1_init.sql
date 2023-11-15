DROP TABLE IF EXISTS account;


CREATE TABLE account (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         user_account VARCHAR(15) UNIQUE NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         name VARCHAR(30) NOT NULL,
                         email VARCHAR(255)
);