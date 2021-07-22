create table rental (
	id int not null auto_increment,
	item_id int,
	tenant varchar(255),
	start_time timestamp not null,
	end_time timestamp not null,
	created_at timestamp default now(),
	updated_at timestamp,
	deleted_at timestamp,
	primary key (id),
	foreign key (item_id) references items(id)
);