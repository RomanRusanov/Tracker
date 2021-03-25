--Create table Rols
create table role(
									 role_id serial primary key,
									 title varchar(100)
);
--Create table Ruls
create table ruls(
									 ruls_id serial primary key,
									 title varchar(100)
);
--Create table Users
create table users(
										user_id bigserial primary key,
										last_name varchar(50) not null,
										first_name varchar(50) not null,
										midle_name varchar(50) not null,
										role_id int not null,
										constraint role_role_id_fk
											foreign key (role_id)
												references role (role_id)
);
--Create table RoleRuls
create table role_ruls(
												role_id int not null,
												constraint role_role_id_fk
													foreign key (role_id)
														references role (role_id),
												ruls_id int not null,
												constraint ruls_ruls_id_fk
													foreign key (ruls_id)
														references ruls (ruls_id),
												primary key (role_id, ruls_id)
);
--Create table State
create table state(
										state_id serial primary key,
										title varchar(50)
);
--Create table Category
create table category(
											 category_id serial primary key,
											 title varchar(50)
);
--Create table Item
create table item(
									 item_id bigserial primary key,
									 title varchar(100),
									 description text,
									 state_id int not null,
									 date_create timestamp,
									 constraint state_state_id_fk
										 foreign key (state_id)
											 references state (state_id),
									 category_id int not null,
									 constraint category_category_id_fk
										 foreign key (category_id)
											 references category (category_id)
);
--create table Users_Item
create table Users_Item(
												 user_id bigint not null,
												 constraint users_user_id
													 foreign key (user_id)
														 references users(user_id),
												 item_id bigint not null,
												 constraint item_item_id
													 foreign key (item_id)
														 references item(item_id),
                         primary key(user_id, item_id)
												);
--Create table Attached
create table attached(
											 attached_id serial primary key,
											 item_id int not null,
											 path_to_file varchar(256) not null,
											 constraint item_item_id_fk
												 foreign key (item_id)
													 references item (item_id)
);
--Create table Comments
create table comments(
											 coment_id serial primary key,
											 item_id bigint not null,
											 comments text,
											 constraint item_item_id_fk
												 foreign key (item_id)
													 references item (item_id)
);
