create database fullstack_auth;
create table person (
                        id serial primary key not null,
                        login varchar(2000),
                        password varchar(2000)
);

insert into person (login, password) values ('person1', '123');
insert into person (login, password) values ('person2', '123');
insert into person (login, password) values ('person3', '123');