-- Table: vas_user
create table vas_users (
    id int auto_increment primary key ,
    user_name varchar(50) not null,
    user_surname varchar(50) not null,
    user_patronymic varchar(50) not null,
    user_login varchar(50) not null,
    user_password varchar(255) not null,
    user_mail varchar(50) not null
)
    ENGINE = InnoDB;

-- Table: vas_roles
CREATE TABLE vas_roles (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_level_access INT NOT NULL
)
    ENGINE = InnoDB;

-- Table for mapping vas_user and vas_roles: vas_user_roles
CREATE TABLE vas_users_roles (
    vas_users_id INT NOT NULL,
    vas_roles_id INT NOT NULL,

    FOREIGN KEY (vas_users_id) REFERENCES vas_users (id),
    FOREIGN KEY (vas_roles_id) REFERENCES vas_roles (id),

    UNIQUE (vas_users_id, vas_roles_id)
)
    ENGINE = InnoDB;

-- Insert data
INSERT INTO vas_users (user_name, user_surname, user_patronymic, user_login, user_password, user_mail)
VALUES ('Кирилл', 'Шабанов', 'Владимирович', 'Shabanov', 'Vas20042018!!))', 'k.shabanov@vitautocity.by');
INSERT INTO vas_roles VALUES (1, 1);
INSERT INTO vas_roles VALUES (2, 2);

INSERT INTO vas_users_roles VALUES (1, 1);