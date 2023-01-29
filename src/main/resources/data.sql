INSERT INTO roles (id,role) VALUES (1,'USER');
INSERT INTO roles (id,role) VALUES (2,'ADMIN');

INSERT INTO users(id,email,first_name,last_name,password,username,is_banned,is_suspended,banned_time,suspended_time,suspended_until,city,county,voivodeship) VALUES (1,'tester@gmail.com','John','Doe','$2a$10$dlHrRPK9/7ZLQSCmfH6nHu2j7U2vlRic09D18ygL3rLykFqV/Q4wi','Tester1',false,false,null,null,null,'Rzeszow','rzeszowski','podkarpackie');
INSERT INTO users(id,email,first_name,last_name,password,username,is_banned,is_suspended,banned_time,suspended_time,suspended_until,city,county,voivodeship) VALUES (2,'tester@gmail.com','Jimmy','Ode','$2a$10$0Q79eJ.Polx3vjCKbRoOd.5N6C6VVjO8R49SXAvvzRmdCav29Ns6G','Tester2',false,false,null,null,null,'Rzeszow','rzeszowski','podkarpackie');
INSERT INTO users(id,email,first_name,last_name,password,username,is_banned,is_suspended,banned_time,suspended_time,suspended_until,city,county,voivodeship) VALUES (3,'admin@gmail.com','Admin','Admin','$2a$10$vks6ONg1jBfqzFufzh/SZubrhJv5bwdNduASa5RlD27FzcvoJyLN.','Admin1',false,false,null,null,null,'Rzeszow','rzeszowski','podkarpackie');
ALTER TABLE users ALTER COLUMN id RESTART WITH 4;

INSERT INTO animals(id,age,category,description,name,user_Id,city,county) VALUES (1,2,'DOG','Good boy','Dog1',1,'Rzeszow','rzeszowski');
INSERT INTO animals(id,age,category,description,name,user_Id,city,county) VALUES (2,2,'DOG','Very good boy','Dog2',2,'Rzeszow','rzeszowski');
ALTER TABLE animals ALTER COLUMN id RESTART WITH 3;

INSERT INTO user_roles(user_id,role_id) VALUES (1,1);
INSERT INTO user_roles(user_id,role_id) VALUES (2,2);