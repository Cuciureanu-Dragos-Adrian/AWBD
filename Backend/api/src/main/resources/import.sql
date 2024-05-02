insert into table_tbl (table_id, table_size, floor, x_offset, y_offset) values ('A1', 2, 0, 100, 100);
insert into table_tbl (table_id, table_size, floor, x_offset, y_offset) values ('B1', 3, 0, 600, 100);
insert into table_tbl (table_id, table_size, floor, x_offset, y_offset) values ('C1', 4, 0, 300, 300);

insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation1', 2, 'Test 1', '2024-05-30T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation2', 2, 'Test 2', '2024-06-30T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation3', 3, 'Test 3', '2024-05-30T20:30:00.000Z', 'C1');

-- insert into product (name, price) values ('Coca Cola', 10)
-- insert into product (name, price, category_name) values ('Burger', 35, 'Main courses')
-- insert into product (name, price, category_name) values ('Vodka', 50, 'Spirits')