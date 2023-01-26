INSERT INTO roles (id,role) VALUES (1,'USER');
INSERT INTO roles (id,role) VALUES (2,'ADMIN');

INSERT INTO users(id,email,first_name,last_name,password,username) VALUES (1,'tester@gmail.com','John','Doe','$2a$10$dlHrRPK9/7ZLQSCmfH6nHu2j7U2vlRic09D18ygL3rLykFqV/Q4wi','Tester1');
INSERT INTO users(id,email,first_name,last_name,password,username) VALUES (2,'admin@gmail.com','Admin','Admin','$2a$10$vks6ONg1jBfqzFufzh/SZubrhJv5bwdNduASa5RlD27FzcvoJyLN.','Admin1');
ALTER TABLE users ALTER COLUMN id RESTART WITH 3;

INSERT INTO user_roles(user_id,role_id) VALUES (1,1);
INSERT INTO user_roles(user_id,role_id) VALUES (2,2);