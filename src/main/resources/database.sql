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

-- Table with unnecessary categories
CREATE TABLE null_category
(
    id             INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255)
)
    ENGINE = InnoDB;

-- Table with unnecessary prefixes by model and brand
CREATE TABLE fix_brand_model
(
    id             INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fix_key VARCHAR(255),
    fix_value VARCHAR(255)
)
    ENGINE = InnoDB;

-- Table vas_users
CREATE TABLE vas_users
(
    id             INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255),
    user_surname VARCHAR(255),
    user_patronymic VARCHAR(255),
    user_login VARCHAR(255),
    user_password VARCHAR(255),
    user_mail VARCHAR(255)
)
ENGINE = InnoDB;

-- Add column in tables vas_nps and gee_nps
ALTER TABLE `vas_nps` ADD `id_client` VARCHAR(255) NULL;
ALTER TABLE `gee_nps` ADD `id_client` VARCHAR(255) NULL;

-- Table VasFullReportNPS
CREATE TABLE vas_full_report_nps
(
    id             INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    vehicle_identification_number VARCHAR(255),
    outgoing_call_date DATE,
    num_order VARCHAR(255) UNIQUE,
    bq010 VARCHAR(10),
    bq020 VARCHAR(10),
    bq030 VARCHAR(10),
    bq030_comment VARCHAR(500),
    bq040 VARCHAR(10),
    bq050 VARCHAR(10),
    bq050_comment VARCHAR(500),
    bq060 VARCHAR(10),
    bq070 VARCHAR(10),
    bq080 VARCHAR(10),
    bq080_comment VARCHAR(500),
    sq010 VARCHAR(10),
    sq020 VARCHAR(10),
    sq030 VARCHAR(10),
    sq040 VARCHAR(10),
    sq050 VARCHAR(10),
    sq060 VARCHAR(10),
    sq070 VARCHAR(10),
    sq080 VARCHAR(10),
    sq090 VARCHAR(10),
    sq110 VARCHAR(10),
    sq120 VARCHAR(10),
    sq130 VARCHAR(10),
    sq140 VARCHAR(10),
    dq010 VARCHAR(10),
    dq020 VARCHAR(10),
    dq030 VARCHAR(10),
    dq040 VARCHAR(10),
    id_client VARCHAR(255)
)
ENGINE = InnoDB;

-- Alter table vas_manager
ALTER TABLE `vas_manager` ADD `name_report` VARCHAR(255) NULL;
ALTER TABLE `vas_manager` ADD `position_report_satisfaction_kia` VARCHAR(255) NULL;
ALTER TABLE `vas_manager` ADD `position_report_satisfaction_kia_copy` VARCHAR(255) NULL;
-- Insert into table vas_manager
INSERT INTO `vas_manager` (organisation, manager_name, manager_email, position_report_satisfaction_kia, `name_report`)
VALUES ('ООО Автопалас-М', 'Артём Хомич', 'artem_homich@atlantm.com', 'checked','customerSatisfactionReport');
-- Update table vas_manager
UPDATE `vas_manager` SET `vas_manager`.`position_report_satisfaction_kia_copy` = 'checked' WHERE `vas_manager`.`manager_name` = 'Шабанов Кирилл Владимирович';
UPDATE `vas_manager` SET `vas_manager`.`position_report_satisfaction_kia_copy` = 'checked' WHERE `vas_manager`.`manager_name` = 'Дащёнок Павел Леонидович';
UPDATE `vas_manager` SET `vas_manager`.`position_report_satisfaction_kia_copy` = 'checked' WHERE `vas_manager`.`manager_name` = 'Бородулина Юлия Юрьевна';
