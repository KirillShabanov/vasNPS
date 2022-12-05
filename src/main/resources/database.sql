-- Table: vas_nps
CREATE TABLE vas_nps (
                         id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         client_surname VARCHAR(255),
                         client_name VARCHAR(255),
                         phone_1 VARCHAR(255),
                         phone_2 VARCHAR(255),
                         vehicle_identification_number VARCHAR(17),
                         reg_num VARCHAR(10),
                         brand VARCHAR(20),
                         model VARCHAR(150),
                         year_release VARCHAR(10),
                         date_order DATETIME,
                         num_order VARCHAR(10) UNIQUE,
                         mileage longtext,
                         organisation VARCHAR(50),
                         department VARCHAR(50),
                         category varchar(50),
                         master_name VARCHAR(100),
                         nps integer,
                         mail_date DATE,
                         call_status varchar(15),
                         outgoing_call_date DATE,
                         admin_name VARCHAR(100),
                         admin_comment VARCHAR(500),
                         calendar_client VARCHAR(50)
)
    ENGINE = InnoDB;

-- Table: gee_nps
CREATE TABLE gee_nps (
                         id       INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
                         client_surname VARCHAR(255),
                         client_name VARCHAR(255),
                         phone_1 VARCHAR(255),
                         phone_2 VARCHAR(255),
                         vehicle_identification_number VARCHAR(17),
                         reg_num VARCHAR(10),
                         brand VARCHAR(20),
                         model VARCHAR(150),
                         year_release VARCHAR(10),
                         date_order DATETIME,
                         num_order VARCHAR(10) UNIQUE,
                         mileage longtext,
                         organisation VARCHAR(50),
                         department VARCHAR(50),
                         category varchar(50),
                         master_name VARCHAR(100),
                         nps integer,
                         mail_date DATE,
                         call_status varchar(15),
                         outgoing_call_date DATE,
                         admin_name VARCHAR(100),
                         admin_comment VARCHAR(500),
                         calendar_client VARCHAR(50)
)
    ENGINE = InnoDB;

-- Delete Row from vas_nps WHERE organisation = Джи-Морорс
DELETE FROM `vas_nps` WHERE `vas_nps`.`organisation` = 'Джи-Моторс';

-- Таблица с ненужными категориями
CREATE TABLE null_category
(
    id             INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255)
)
    ENGINE = InnoDB;

-- Таблица с ненужными префиксами по модели и бренду
CREATE TABLE fix_brand_model
(
    id             INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fix_key VARCHAR(255),
    fix_value VARCHAR(255)
)
    ENGINE = InnoDB;
