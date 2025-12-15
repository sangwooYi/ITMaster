create table member (
                        user_id VARCHAR(100) PRIMARY KEY,
                        password VARCHAR(500) NOT NULL,
                        user_name VARCHAR(100) NOT NULL ,
                        mail_address VARCHAR(300),
                        phone_number VARCHAR(300),
                        address VARCHAR(1000),
                        role_name varchar(30) DEFAULT 'role_normal' CHECK ( role_name IN  ('role_normal', 'role_admin')),
                        is_active TINYINT DEFAULT 1 CHECK ( is_active IN (0, 1) ),
                        register_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE board (
                       board_num INT AUTO_INCREMENT PRIMARY KEY ,
                       user_id VARCHAR(100) ,
                       title VARCHAR(1000) NOT NULL,
                       contents TEXT NOT NULL,
                       view_count INT DEFAULT 0,
                       like_count INT DEFAULT 0,
                       original_name VARCHAR(500),
                       file_name VARCHAR(300),
                       created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT FOREIGN KEY (user_id) REFERENCES member (user_id) ON DELETE CASCADE
);
