DROP TABLE IF EXISTS order_tbl_product;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS order_tbl;
DROP TABLE IF EXISTS table_tbl;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS menu_category;
DROP TABLE IF EXISTS user_authority;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS authority;


CREATE TABLE table_tbl (
                           table_id VARCHAR(255) PRIMARY KEY,
                           x_offset DOUBLE,
                           y_offset DOUBLE,
                           table_size INT,
                           floor INT
);

CREATE TABLE menu_category (
                               name VARCHAR(255) PRIMARY KEY,
                               iconBase64 VARCHAR(255)
);

CREATE TABLE product (
                         name VARCHAR(255) PRIMARY KEY,
                         price DOUBLE,
                         category_name VARCHAR(255),
                         FOREIGN KEY (category_name) REFERENCES menu_category(name)
);



CREATE TABLE order_tbl (
                           order_id VARCHAR(255) PRIMARY KEY,
                           price DOUBLE,
                           table_id VARCHAR(255),
                           FOREIGN KEY (table_id) REFERENCES table_tbl(table_id)
);

CREATE TABLE order_tbl_product (
                                   order_id VARCHAR(255),
                                   product_name VARCHAR(255),
                                   quantity INT,
                                   PRIMARY KEY (order_id, product_name),
                                   FOREIGN KEY (order_id) REFERENCES order_tbl(order_id),
                                   FOREIGN KEY (product_name) REFERENCES product(name)
);



CREATE TABLE reservation (
                             reservation_id VARCHAR(255) PRIMARY KEY,
                             number_of_people INT,
                             name VARCHAR(255),
                             date_time TIMESTAMP,
                             table_id VARCHAR(255),
                             FOREIGN KEY (table_id) REFERENCES table_tbl(table_id)
);

CREATE TABLE payment (
                         payment_id VARCHAR(255) PRIMARY KEY,
                         time DATETIME,
                         amount DOUBLE,
                         tax DOUBLE,
                         net DOUBLE,
                         with_cash BOOLEAN,
                         order_id VARCHAR(255),
                         FOREIGN KEY (order_id) REFERENCES order_tbl(order_id)
);

CREATE TABLE `user`(
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       enabled BOOLEAN NOT NULL DEFAULT true,
                       account_non_expired BOOLEAN NOT NULL DEFAULT true,
                       account_non_locked BOOLEAN NOT NULL DEFAULT true,
                       credentials_non_expired BOOLEAN NOT NULL DEFAULT true
);

CREATE TABLE authority(
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          role VARCHAR(50) NOT NULL
);

CREATE TABLE user_authority(
                               user_id BIGINT,
                               authority_id BIGINT,
                               FOREIGN KEY (user_id) REFERENCES `user`(id),
                               FOREIGN KEY (authority_id) REFERENCES AUTHORITY(id),
                               PRIMARY KEY (user_id, authority_id)
);