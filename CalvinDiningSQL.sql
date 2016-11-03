DROP TABLE IF EXISTS User;

CREATE TABLE User (
	ID integer PRIMARY KEY, 
	username varchar(50)
	password varchar(50)
	meals integer
	);

INSERT INTO User VALUES (1, JohnCalvin, password, 33);
INSERT INTO User VALUES (2, JohnDoe, 12345, 25);
INSERT INTO User VALUES (3, JaneDoe, HelloWorld, 45);

SELECT *
FROM User
WHERE meals > 30;

SELECT *
FROM USER
WHERE username LIKE John%;


