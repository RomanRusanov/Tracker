--Add entry in table
insert into state (title) values ('open'),('closed');
insert into category (title) values ('First'),('Second'),('Third');
insert into ruls (title) values ('full'),('half');
insert into role (title) values ('admin'),('user');
insert into role_ruls (role_id, ruls_id) values (1, 1),(2, 2);

insert into users (last_name, first_name, midle_name, role_id)
values ('Иванов', 'Иван', 'Иванович', 1),('Петров', 'Петр', 'Петрович', 2);

insert into item (item_id, date_create, title, category_id, description, state_id)
values (1, '2004-10-19 10:23:54', 'заявка1', 1, 'Текст заявки 1', 1),(2, '2005-12-05 18:53:04', 'заявка2', 2, 'Текст заявки 2', 1);

insert into comments (item_id, comments)
values (1, 'Коментарий 1 к заявке 1'),(2, 'Коментарий 1 к заявке 2');

insert into attached (item_id, path_to_file)
values (1, '\attached\attached1item1.zip'),(2, '\attached\attached1item2.zip');