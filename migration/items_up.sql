create table items (
	id int not null auto_increment,
	name varchar(255),
	category varchar(255),
	status varchar(255),
	created_at timestamp default now(),
	updated_at timestamp,
	deleted_at timestamp,
	primary key (id)
);