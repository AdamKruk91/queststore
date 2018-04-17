DROP TABLE IF EXISTS `Wallet`;
CREATE TABLE IF NOT EXISTS `Wallet` (
	`id_wallet`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`total_coolcoins`	INTEGER NOT NULL,
	`balance`	INTEGER NOT NULL,
	`id_student`	INTEGER NOT NULL
);
INSERT INTO `Wallet` (id_wallet,total_coolcoins,balance,id_student) VALUES (1,1000,1000,1),
 (2,0,0,2),
 (3,0,0,3),
 (4,0,0,4),
 (5,0,0,5),
 (6,0,0,6),
 (7,0,0,0),
 (8,0,0,0);
DROP TABLE IF EXISTS `Student`;
CREATE TABLE IF NOT EXISTS `Student` (
	`id_student`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`first_name`	TEXT NOT NULL,
	`last_name`	TEXT NOT NULL,
	`id_login`	INTEGER NOT NULL,
	`id_status`	INTEGER NOT NULL,
	`id_group`	INTEGER NOT NULL
);
INSERT INTO `Student` (id_student,first_name,last_name,id_login,id_status,id_group) VALUES (1,'Wiola','Szczepanik',4,3,1),
 (2,'Maria','Barszczyk',5,3,1),
 (3,'Kasia','Drobna',6,3,1),
 (4,'Iza','Rapacz',8,3,1),
 (5,'Ola','Herba',9,2,1),
 (6,'Ryszarda','Kowal',10,2,2),
 (7,'Tomasz','Gruca',12,2,2),
 (8,'Ewa','Kosa',14,2,4);
DROP TABLE IF EXISTS `Item`;
CREATE TABLE IF NOT EXISTS `Item` (
	`id_item`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`item_name`	TEXT NOT NULL,
	`description`	TEXT NOT NULL,
	`price`	INTEGER NOT NULL,
	`id_type`	INTEGER NOT NULL
);
INSERT INTO `Item` (id_item,item_name,description,price,id_type) VALUES (1,'Spot trap','Spot a major mistake in the assignment',50,1),
 (2,'Taming a pet','Doing a demo about a pet project',200,1),
 (3,'Combat training','Private mentoring',100,2),
 (4,'Master the mornings ','Attend 1 months without being late ',200,1),
 (5,'Time Travel','extend SI week assignment deadline by one day',150,2);
DROP TABLE IF EXISTS `ItemType`;
CREATE TABLE IF NOT EXISTS `ItemType` (
	`id_type`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL UNIQUE
);
INSERT INTO `ItemType` (id_type,name) VALUES (1,'Quest'),
 (2,'Artifact');
DROP TABLE IF EXISTS `Transactions`;
CREATE TABLE IF NOT EXISTS `Transactions` (
	`id_transaction`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`id_student`	INTEGER NOT NULL,
	`id_item`	INTEGER NOT NULL,
	`used`	INTEGER NOT NULL
);
INSERT INTO `Transactions` (id_transaction,id_student,id_item,used) VALUES (1,1,5,1),
 (2,1,3,0),
 (3,1,1,0),
 (4,1,1,0),
 (5,1,3,1),
 (6,1,5,1),
 (7,1,3,1),
 (8,1,2,0),
 (9,1,3,1),
 (10,1,1,0),
 (11,1,5,1),
 (12,1,2,0),
 (13,2,5,0),
 (14,2,1,0),
 (15,2,3,0);
DROP TABLE IF EXISTS `Login`;
CREATE TABLE IF NOT EXISTS `Login` (
	`id_login`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`email`	TEXT NOT NULL,
	`password`	TEXT NOT NULL,
	`id_status`	INTEGER NOT NULL
);
INSERT INTO `Login` (id_login,email,password,id_status) VALUES (1,'olaf@gmail.com','olaf',1),
 (2,'zenek@gmail.com','zenek',2),
 (3,'piotr@gmail.com','piotr',2),
 (4,'wiola@gmail.com','wiola',3),
 (5,'maria@gmail.com','maria',3),
 (6,'kasia@gmail.com','kasia',3),
 (7,'remi@gmail.com','remi',2),
 (8,'iza@gmail.com','iza',3),
 (9,'ola@gmail.com','ola',3),
 (10,'rysia@gmail.com','rysia',3),
 (11,'marzena@gmail.com','marzena',2),
 (12,'tomek@gmail.com','tomek',3),
 (13,'ula@gmail.com','ula',2),
 (14,'ewa@gmail.com','ewa',3),
 (15,'wojtek@gmail.com','wojtek',2);
DROP TABLE IF EXISTS `Mentor`;
CREATE TABLE IF NOT EXISTS `Mentor` (
	`id_mentor`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`first_name`	TEXT NOT NULL,
	`last_name`	TEXT NOT NULL,
	`id_login`	INTEGER,
	`id_status`	INTEGER NOT NULL,
	`id_group`	INTEGER NOT NULL
);
INSERT INTO `Mentor` (id_mentor,first_name,last_name,id_login,id_status,id_group) VALUES (1,'Zenek','Mrozowski',2,2,1),
 (2,'Piotr','Podsiadło',3,2,1),
 (3,'Remigiusz','Robak',7,2,1),
 (4,'Marzena','Mrówka',11,2,2),
 (5,'Ula','Krzywda',13,2,2),
 (6,'Wojtek','Dębski',15,2,3);
DROP TABLE IF EXISTS `Admin`;
CREATE TABLE IF NOT EXISTS `Admin` (
	`id_admin`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`first_name`	TEXT NOT NULL,
	`last_name`	TEXT NOT NULL,
	`id_login`	INTEGER NOT NULL,
	`id_status`	INTEGER NOT NULL
);
INSERT INTO `Admin` (id_admin,first_name,last_name,id_login,id_status) VALUES (2,'Olaf','Lubaszenko',1,1);
DROP TABLE IF EXISTS `status`;
CREATE TABLE IF NOT EXISTS `status` (
	`id_status`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT UNIQUE
);
INSERT INTO `status` (id_status,name) VALUES (1,'Admin'),
 (2,'Mentor'),
 (3,'Student');
DROP TABLE IF EXISTS `Groups`;
CREATE TABLE IF NOT EXISTS `Groups` (
	`id_group`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL
);
INSERT INTO `Groups` (id_group,name) VALUES (1,'A'),
 (2,'B'),
 (3,'C'),
 (4,'D');
COMMIT;
