DROP DATABASE IF EXISTS wstore;
CREATE DATABASE IF NOT EXISTS wstore;
use wstore;

drop table if exists products;
create table if not exists products(
	id int primary key auto_increment,
    name varchar(20),
    type enum("cloth", "shoe", "accessory"),
	colour varchar(20) default null,
	stock int check(stock >= 0),
	price double check(price >= 0),
    cost double default 0 check(cost >= 0),
    size int default 0,
    saleFactor double default 1 check(saleFactor >= 0 and saleFactor <=1),
    isOnSale bool default false
);

drop table if exists orders;
create table if not exists orders(
	id int primary key auto_increment,
    orderType enum('sell','purchase'),
    dateOrder date default now()
	);

drop table if exists quantityOrder;
create table if not exists quantityOrder(
	productId int,
    orderId int,
    quantity int check(quantity > 0),
	primary key(productId, orderId),
    foreign key(productId)
		references products (id)
        on delete cascade
        on update no action,
	foreign key(orderId)
		references orders (id)
        on delete cascade
        on update no action
);