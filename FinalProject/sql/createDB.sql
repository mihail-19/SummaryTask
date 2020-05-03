create database FT4test;
use FT4test;
create table users (id int auto_increment primary key, 
login varchar(20) UNIQUE,
roleID int,
firstName varchar(20),
 lastName varchar(20),
 dateOfBirth date);

insert into users (login, roleID, firstName, lastName, dateOfBirth) VALUES
('admin', 0, 'Mykhailo', 'Teslenko', DATE('1992-08-19'));

create table users_autorization (user_id int not null, password varchar(20) not null, 
foreign key (user_id) references users (id) on delete cascade);

insert into users_autorization (user_id, password) values (1, 1234);

SELECT login, roleID, firstName, lastName, dateOfBirth, password FROM users 
INNER JOIN users_autorization ON user_id=id WHERE login='admin';

CREATE TABLE patients (id int auto_increment primary key, 
firstName varchar(20),
 lastName varchar(20),
 dateOfBirth date,
 isActive boolean);


create table doctors_params (id int auto_increment primary key, 
category int,
numberOfPatients int,
doctor_id int,
foreign key (doctor_id) references users (id) on delete cascade );



create table patients_doctors (patient_id int, doctor_id int,
foreign key (patient_id) references patients (id) on delete cascade,
foreign key (doctor_id) references users (id) on delete cascade );


create table hospitalCards (id int auto_increment primary key,
diagnose varchar(50),
patient_id int,
foreign key (patient_id) references patients (id) on delete cascade);

create table prescription (id int auto_increment primary key,
prescType varchar (20),
description text,
completionStatus boolean,
hospitalCard_id int,
foreign key (hospitalCard_id) references hospitalCards (id) on delete cascade);

