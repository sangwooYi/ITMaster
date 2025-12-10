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

