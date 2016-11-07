DROP TABLE IF EXISTS Person;

CREATE TABLE Person

(
ID int,
username varchar(50),
password varchar(50),
meals int

);

INSERT INTO Person VALUES (1, 'JohnCalvin', 'password', 33);
INSERT INTO Person VALUES (2, 'JohnDoe', 12345, 25);
INSERT INTO Person VALUES (3, 'RaymondStantz', 'helloworld', 45);
INSERT INTO Person VALUES (4, 'PeterVenkman', 'buster', 15);
INSERT INTO Person VALUES (5, 'EgonSpengler', 'password', 20);


SELECT *
FROM User
WHERE meals > 30;

SELECT *
FROM USER
WHERE username LIKE 'John%';
