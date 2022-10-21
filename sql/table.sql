DROP DATABASE IF EXISTS wstore;
CREATE DATABASE IF NOT EXISTS wstore;
use wstore;

drop table if exists products;
create table if not exists products(
	id int primary key auto_increment,
    name varchar(20),
	colour varchar(20),
	stock int check(stock >= 0),
	price double check(price >= 0),
    cost double check(cost >= 0),
	sales double check(sales => 0 and sales <= 1),
	image blob
);

drop table if exists clothes;
create table if not exists clothes(
	id int primary key,
	size int check(size>=34 and size<=54 and size%2=0),
	sales double check(sales >= 0 and sales <= 1),
	foreign key(id)
	    references products(id)
	    on delete cascade
	    on update no action
);

drop table if exists shoes;
create table if not exists shoes(
	id int primary key,
	size int check(size>=36 and size<=50),
	sales double check(sales >= 0 and sales <= 1),
	foreign key(id)
	    references products(id)
	    on delete cascade
        on update no action
);

drop table if exists accessories;
create table if not exists accessories(
	id int primary key,
	sales double check(sales >= 0 and sales <= 1),
    foreign key(id)
        references products(id)
        on delete cascade
        on update no action
);

drop table if exists orders;
create table if not exists orders(
	id int primary key auto_increment,
    orderType enum('sell','purchase'),
    dateOrder date default(CURRENT_DATE)
	);

drop table if exists quantityOrder;
create table if not exists quantityOrder(
	productId int,
    orderId int,
    quantity int check(quantity > 0),
	primary key(productId,orderId),
    foreign key(productId)
		references Products (id)
        on delete cascade
        on update no action,
	foreign key(orderId)
		references Orders (id)
        on delete cascade
        on update no action
);











