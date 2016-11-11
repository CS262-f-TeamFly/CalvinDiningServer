DROP TABLE IF EXISTS Reviews;
DROP TABLE IF EXISTS Poll;
DROP TABLE IF EXISTS Person;

CREATE TABLE Person
(
ID int NOT NULL PRIMARY KEY,
username varchar(50),
password varchar(50),
meals integer

);

CREATE TABLE Poll
(
title varchar(50),
question varchar(1000),
response varchar(1000)
);

CREATE TABLE Reviews
(
PersonID integer REFERENCES Person(ID),
food varchar(50),
review varchar(100),
positive boolean
);



INSERT INTO Person VALUES (1, 'JohnCalvin', 'password', 33);
INSERT INTO Person VALUES (2, 'JohnDoe', 12345, 25);
INSERT INTO Person VALUES (3, 'RaymondStantz', 'helloworld', 45);
INSERT INTO Person VALUES (4, 'PeterVenkman', 'buster', 15);
INSERT INTO Person VALUES (5, 'EgonSpengler', 'password', 20);

INSERT INTO Poll VALUES ('CommonsSurvey', 'What should we do differently?', 'Answer 1...');
INSERT INTO Poll VALUES ('CommonsSurvey', 'What should we do differently?', 'Answer 2...');

INSERT INTO Reviews VALUES (1, 'Grilled Cheese', 'Very good', TRUE);
INSERT INTO Reviews VALUES (2, 'Pizza', 'Pretty Bad', FALSE);
INSERT INTO Reviews VALUES (3, 'Taco', 'Excellent', TRUE);



SELECT *
FROM Person
WHERE meals > 30;

SELECT *
FROM Person
WHERE username LIKE 'John%';

SELECT *
FROM Poll
WHERE response LIKE '%1';

SELECT *
FROM Reviews
WHERE positive = TRUE;



