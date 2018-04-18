CREATE TABLE IF NOT EXISTS `Login` (
	`id_login`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`email`	TEXT NOT NULL,
	`password`	TEXT NOT NULL,
	`id_status`	INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS `Student` (
	`id_student`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`first_name`	TEXT NOT NULL,
	`last_name`	TEXT NOT NULL,
	`id_login`	INTEGER NOT NULL,
	`id_status`	INTEGER NOT NULL,
	`id_group`	INTEGER NOT NULL
);
INSERT INTO `Student` VALUES (1,'Wiola','Szczepanik',4,3,1);
INSERT INTO `Student` VALUES (2,'Maria','Barszczyk',5,3,1);
INSERT INTO `Student` VALUES (3,'Kasia','Drobna',6,3,1);
INSERT INTO `Student` VALUES (4,'Iza','Rapacz',8,3,1);
INSERT INTO `Student` VALUES (5,'Ola','Herba',9,2,1);
INSERT INTO `Student` VALUES (6,'Ryszarda','Kowal',10,2,2);
INSERT INTO `Student` VALUES (7,'Tomasz','Gruca',12,2,2);
INSERT INTO `Student` VALUES (8,'Ewa','Kosa',14,2,4);
CREATE TABLE IF NOT EXISTS `Mentor` (
	`id_mentor`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`first_name`	TEXT NOT NULL,
	`last_name`	TEXT NOT NULL,
	`id_login`	INTEGER,
	`id_status`	INTEGER NOT NULL,
	`id_group`	INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS `status` (
	`id_status`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT UNIQUE
);
INSERT INTO `status` VALUES (1,'Admin');
INSERT INTO `status` VALUES (2,'Mentor');
INSERT INTO `status` VALUES (3,'Student');
INSERT INTO `status` VALUES (0,'Default');
CREATE TABLE IF NOT EXISTS `Wallet` (
	`id_wallet`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`total_coolcoins`	INTEGER NOT NULL,
	`balance`	INTEGER NOT NULL,
	`id_student`	INTEGER NOT NULL,
	FOREIGN KEY(`id_student`) REFERENCES `Student`(`id_student`) ON DELETE CASCADE
);
INSERT INTO `Wallet` VALUES (1,1000,1000,1);
INSERT INTO `Wallet` VALUES (2,0,0,2);
INSERT INTO `Wallet` VALUES (3,0,0,3);
INSERT INTO `Wallet` VALUES (4,0,0,4);
INSERT INTO `Wallet` VALUES (5,0,0,5);
INSERT INTO `Wallet` VALUES (6,0,0,6);
INSERT INTO `Wallet` VALUES (7,0,0,7);
INSERT INTO `Wallet` VALUES (8,0,0,8);
CREATE TABLE IF NOT EXISTS `Transactions` (
	`id_transaction`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`id_student`	INTEGER NOT NULL,
	`id_item`	INTEGER NOT NULL,
	`used`	INTEGER NOT NULL
);
INSERT INTO `Transactions` VALUES (1,1,5,1);
INSERT INTO `Transactions` VALUES (2,1,3,1);
INSERT INTO `Transactions` VALUES (3,1,1,0);
INSERT INTO `Transactions` VALUES (4,1,1,0);
INSERT INTO `Transactions` VALUES (5,1,3,1);
INSERT INTO `Transactions` VALUES (6,1,5,1);
INSERT INTO `Transactions` VALUES (7,1,3,1);
INSERT INTO `Transactions` VALUES (8,1,2,0);
INSERT INTO `Transactions` VALUES (9,1,3,1);
INSERT INTO `Transactions` VALUES (10,1,1,0);
INSERT INTO `Transactions` VALUES (11,1,5,1);
INSERT INTO `Transactions` VALUES (12,1,2,0);
INSERT INTO `Transactions` VALUES (13,1,3,1);
INSERT INTO `Transactions` VALUES (14,1,1,0);
INSERT INTO `Transactions` VALUES (15,1,2,0);
INSERT INTO `Mentor` VALUES (1,'Zenek','Mrozowski',2,2,1);
INSERT INTO `Mentor` VALUES (2,'Piotr','Podsiadło',3,2,1);
INSERT INTO `Mentor` VALUES (3,'Remigiusz','Robak',7,2,1);
INSERT INTO `Mentor` VALUES (4,'Marzena','Mrówka',11,2,2);
INSERT INTO `Mentor` VALUES (5,'Ula','Krzywda',13,2,2);
INSERT INTO `Mentor` VALUES (6,'Wojtek','Dębski',15,2,3);
INSERT INTO `Login` VALUES (1,'olaf@gmail.com','olaf',1);
INSERT INTO `Login` VALUES (2,'zenek@gmail.com','zenek',2);
INSERT INTO `Login` VALUES (3,'piotr@gmail.com','piotr',2);
INSERT INTO `Login` VALUES (4,'wiola@gmail.com','wiola',3);
INSERT INTO `Login` VALUES (5,'maria@gmail.com','maria',3);
INSERT INTO `Login` VALUES (6,'kasia@gmail.com','kasia',3);
INSERT INTO `Login` VALUES (7,'remi@gmail.com','remi',2);
INSERT INTO `Login` VALUES (8,'iza@gmail.com','iza',3);
INSERT INTO `Login` VALUES (9,'ola@gmail.com','ola',3);
INSERT INTO `Login` VALUES (10,'rysia@gmail.com','rysia',3);
INSERT INTO `Login` VALUES (11,'marzena@gmail.com','marzena',2);
INSERT INTO `Login` VALUES (12,'tomek@gmail.com','tomek',3);
INSERT INTO `Login` VALUES (13,'ula@gmail.com','ula',2);
INSERT INTO `Login` VALUES (14,'ewa@gmail.com','ewa',3);
INSERT INTO `Login` VALUES (15,'wojtek@gmail.com','wojtek',2);
CREATE TABLE IF NOT EXISTS `ItemType` (
	`id_type`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL UNIQUE
);
INSERT INTO `ItemType` VALUES (1,'Quest');
INSERT INTO `ItemType` VALUES (2,'Artifact');
CREATE TABLE IF NOT EXISTS `Item` (
	`id_item`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`item_name`	TEXT NOT NULL,
	`description`	TEXT NOT NULL,
	`price`	INTEGER NOT NULL,
	`id_type`	INTEGER NOT NULL
);
INSERT INTO `Item` VALUES (1,'Spot trap','Spot a major mistake in the assignment',50,1);
INSERT INTO `Item` VALUES (2,'Taming a pet','Doing a demo about a pet project',200,1);
INSERT INTO `Item` VALUES (3,'Combat training','Private mentoring',100,2);
INSERT INTO `Item` VALUES (4,'Master the mornings ','Attend 1 months without being late ',200,1);
INSERT INTO `Item` VALUES (5,'Time Travel','extend SI week assignment deadline by one day',150,2);
CREATE TABLE IF NOT EXISTS `Groups` (
	`id_group`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT NOT NULL
);
INSERT INTO `Groups` VALUES (1,'A');
INSERT INTO `Groups` VALUES (2,'B');
INSERT INTO `Groups` VALUES (3,'C');
INSERT INTO `Groups` VALUES (4,'D');
CREATE TABLE IF NOT EXISTS `Admin` (
	`id_admin`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`first_name`	TEXT NOT NULL,
	`last_name`	TEXT NOT NULL,
	`id_login`	INTEGER NOT NULL,
	`id_status`	INTEGER NOT NULL
);
INSERT INTO `Admin` VALUES (2,'Olaf','Lubaszenko',1,1);
CREATE TABLE IF NOT EXISTS `Level` (
	`id_level`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
	`experience_amount`	INTEGER NOT NULL,
	`name`	TEXT NOT NULL
);
INSERT INTO `Level` VALUES (1,300,'Newbie');
INSERT INTO `Level` VALUES (2,1200,'Scratch Developer');
INSERT INTO `Level` VALUES (3,2500,'Regular Codecooler');
INSERT INTO `Level` VALUES (4,3500,'Mentor Slayer');
INSERT INTO `Level` VALUES (5,5000,'UML Champion');
COMMIT;
