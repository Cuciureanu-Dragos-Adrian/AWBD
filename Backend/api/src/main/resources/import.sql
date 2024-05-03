insert into table_tbl (table_id, table_size, floor, x_offset, y_offset) values ('A1', 2, 0, 100, 100);
insert into table_tbl (table_id, table_size, floor, x_offset, y_offset) values ('B1', 3, 0, 600, 100);
insert into table_tbl (table_id, table_size, floor, x_offset, y_offset) values ('C1', 4, 0, 300, 300);

insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation1', 2, 'Test 1', '2024-05-30T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation2', 2, 'Test 2', '2024-06-30T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation3', 3, 'Test 3', '2024-05-30T20:30:00.000Z', 'C1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation2', 2, 'Test 2', '2024-06-01T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation3', 2, 'Test 3', '2024-06-02T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation4', 2, 'Test 4', '2024-06-03T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation5', 2, 'Test 5', '2024-06-04T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation6', 2, 'Test 6', '2024-06-05T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation7', 2, 'Test 7', '2024-06-06T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation8', 2, 'Test 8', '2024-06-07T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation9', 2, 'Test 9', '2024-06-08T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation10', 2, 'Test 10', '2024-06-09T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation11', 2, 'Test 11', '2024-06-10T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation12', 2, 'Test 12', '2024-06-11T20:30:00.000Z', 'A1');
insert into reservation (reservation_id, number_of_people, name, date_time, table_id) values ('reservation30', 3, 'Test 30', '2024-05-30T21:30:00.000Z', 'C1');

-- insert into product (name, price) values ('Coca Cola', 10)
-- insert into product (name, price, category_name) values ('Burger', 35, 'Main courses')
-- insert into product (name, price, category_name) values ('Vodka', 50, 'Spirits')