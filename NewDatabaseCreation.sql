BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `wallet` (
	`user_id`	INTEGER,
	`total_coins_earned`	INTEGER,
	`amount`	INTEGER,
	FOREIGN KEY(`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
	PRIMARY KEY(`user_id`)
);
INSERT INTO `wallet` VALUES (2,300,300);
INSERT INTO `wallet` VALUES (4,250,250);
INSERT INTO `wallet` VALUES (5,350,350);
CREATE TABLE IF NOT EXISTS `user_quest` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`user_id`	INTEGER,
	`quest_id`	INTEGER,
	`status_id`	INTEGER,
	FOREIGN KEY(`quest_id`) REFERENCES `quest`(`id`) ON DELETE CASCADE,
	FOREIGN KEY(`status_id`) REFERENCES `quest_status`(`id`) ON DELETE CASCADE,
	FOREIGN KEY(`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS `user_group` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`user_id`	INTEGER,
	`group_id`	INTEGER,
	FOREIGN KEY(`group_id`) REFERENCES `group`(`id`) ON DELETE CASCADE,
	FOREIGN KEY(`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE
);
INSERT INTO `user_group` VALUES (1,2,1);
INSERT INTO `user_group` VALUES (2,4,2);
INSERT INTO `user_group` VALUES (3,5,1);
INSERT INTO `user_group` VALUES (4,1,1);
CREATE TABLE IF NOT EXISTS `user_category` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT
);
INSERT INTO `user_category` VALUES (1,'Codecooler');
INSERT INTO `user_category` VALUES (2,'Mentor');
INSERT INTO `user_category` VALUES (3,'CreepyGuy');
CREATE TABLE IF NOT EXISTS `user_artifact` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`user_id`	INTEGER,
	`artifact_id`	INTEGER,
	`status_id`	INTEGER,
	FOREIGN KEY(`artifact_id`) REFERENCES `artifact`(`id`) ON DELETE CASCADE,
	FOREIGN KEY(`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
	FOREIGN KEY(`status_id`) REFERENCES `artifact_status`(`id`)
);
CREATE TABLE IF NOT EXISTS `user` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`login`	TEXT UNIQUE,
	`password`	TEXT,
	`name`	TEXT,
	`surname`	TEXT,
	`email`	TEXT,
	`user_category_id`	INTEGER,
	FOREIGN KEY(`user_category_id`) REFERENCES `user_category`(`id`)
);
INSERT INTO `user` VALUES (1,'Mentor','mentor','Gwidon','Testuje','bidongwidon@email.com',2);
INSERT INTO `user` VALUES (2,'Cooler','qwerty','Hierofant','Serio-Hierofant','serio@email.com',1);
INSERT INTO `user` VALUES (3,'Creepy','1234','Lucjan','Belzebub','lucek666@pieklo.com',3);
INSERT INTO `user` VALUES (4,'adi6','haslo','Adrian','Scrum','adisc@email.com',1);
INSERT INTO `user` VALUES (5,'wtf6','pass','Pawel','Komit','pkomit@email.com',1);
CREATE TABLE IF NOT EXISTS `session` (
	`id`	TEXT NOT NULL UNIQUE,
	`user_id`	INTEGER NOT NULL,
	PRIMARY KEY(`id`)
);
CREATE TABLE IF NOT EXISTS `quest_status` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT
);
INSERT INTO `quest_status` VALUES (1,'In progress');
INSERT INTO `quest_status` VALUES (2,'Done');
CREATE TABLE IF NOT EXISTS `quest_category` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT
);
INSERT INTO `quest_category` VALUES (1,'Basic');
INSERT INTO `quest_category` VALUES (2,'Extra');
CREATE TABLE IF NOT EXISTS `quest` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`reward`	INTEGER,
	`description`	TEXT,
	`category_id`	INTEGER,
	FOREIGN KEY(`category_id`) REFERENCES `quest_category`(`id`)
);
INSERT INTO `quest` VALUES (1,'Exploring a dungeon',80,'Finish teamwork project',1);
INSERT INTO `quest` VALUES (2,'Slaying a dragon',600,'Pass checkpoint',1);
INSERT INTO `quest` VALUES (3,'Spot a trap ',30,'Spot a major mistake in the assignment ',2);
INSERT INTO `quest` VALUES (4,'Fast as an unicorn',300,'Get 4 assignments in a row in before deadline.',2);
INSERT INTO `quest` VALUES (5,'Sleep Master',1,'Take a nap during break-time.',2);
INSERT INTO `quest` VALUES (6,'Deathmatch',250,'Pass checkpoint',1);
CREATE TABLE IF NOT EXISTS `level` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`experience_amount`	INTEGER,
	`name`	TEXT
);
INSERT INTO `level` VALUES (1,300,'Newbie');
INSERT INTO `level` VALUES (2,1200,'Scratch Developer');
INSERT INTO `level` VALUES (3,2500,'Regular Codecooler');
INSERT INTO `level` VALUES (4,3500,'Mentor Slayer');
INSERT INTO `level` VALUES (5,5000,'UML Champion');
CREATE TABLE IF NOT EXISTS `group` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT
);
INSERT INTO `group` VALUES (1,'a');
INSERT INTO `group` VALUES (2,'b');
CREATE TABLE IF NOT EXISTS `artifact_status` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT
);
INSERT INTO `artifact_status` VALUES (1,'Used');
INSERT INTO `artifact_status` VALUES (2,'In wallet');
INSERT INTO `artifact_status` VALUES (3,'Use requested');
CREATE TABLE IF NOT EXISTS `artifact_category` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT
);
INSERT INTO `artifact_category` VALUES (1,'Normal');
INSERT INTO `artifact_category` VALUES (2,'Magic');
CREATE TABLE IF NOT EXISTS `artifact` (
	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	`name`	TEXT,
	`description`	TEXT,
	`price`	INTEGER,
	`category_id`	INTEGER,
	FOREIGN KEY(`category_id`) REFERENCES `artifact_category`(`id`)
);
INSERT INTO `artifact` VALUES (1,'Scroll of Combat','Enhance user combat skills.',200,1);
INSERT INTO `artifact` VALUES (2,'Invitation to Sanctuary','Spend the day ins Home Sanctuary to rest.',300,1);
INSERT INTO `artifact` VALUES (3,'Scoll of Time Travel','Extend deadline of quest by one day.',300,1);
INSERT INTO `artifact` VALUES (4,'Unholy Grail','Anyone who drinks it becomes Evil!',666,2);
INSERT INTO `artifact` VALUES (5,'Scroll of Knowledge','Enhances user intelligence.',150,1);
INSERT INTO `artifact` VALUES (6,'Teleportation','User can telepot to chosen destination.',1000,2);
INSERT INTO `artifact` VALUES (7,'Bed of Thousand Nulls','User can sleep in their own bed in Codecool.',1200,2);
COMMIT;
