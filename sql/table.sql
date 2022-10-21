DROP DATABASE IF EXISTS wstore;
CREATE DATABASE IF NOT EXISTS wstore;
use wstore;

drop table if exists products;
create table if not exists products(
	id int primary key auto_increment,
    name varchar(20),
	size varchar(20),
	type varchar(20),
	colour varchar(20),
	stock int check(stock > 0),
	price float check(price > 0),
    cost float check(cost > 0),
	promo float check(cost >= 0),
	image blob
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











