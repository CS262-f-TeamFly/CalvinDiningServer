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
pollID int NOT NULL PRIMARY KEY,
personID int REFERENCES Person(ID),
title varchar(50),
questionType varchar(50),
question varchar(100),
option1 varchar(50),
answer1 boolean,
option2 varchar(50),
answer2 boolean,
option3 varchar(50),
answer3 boolean,
option4 varchar(50),
answer4 boolean
);



INSERT INTO Person VALUES (1, 'JohnCalvin', 'password', 33);
INSERT INTO Person VALUES (2, 'JohnDoe', '12345', 25);
INSERT INTO Person VALUES (3, 'RaymondStantz', 'helloworld', 45);
INSERT INTO Person VALUES (4, 'PeterVenkman', 'buster', 15);
INSERT INTO Person VALUES (5, 'EgonSpengler', 'password', 20);

INSERT INTO Poll VALUES (1, 1, 'CommonsSurvey', 'multipleChoice', 'What should Commons add to the menu?', 'Lobster', TRUE, 
'Swordfish', FALSE, 'Crab', FALSE, 'Clams', FALSE);
INSERT INTO Poll VALUES (2, 4, 'CommonsSurvey', 'trueFalse', 'Did you enjoy you meal today?', 'Yes', FALSE, 'No', TRUE);



SELECT *
FROM Person
WHERE meals > 30;

SELECT *
FROM Person
WHERE username LIKE 'John%';

SELECT question, option1, answer1, option2, answer2
FROM Poll
WHERE questionType = 'trueFalse'



