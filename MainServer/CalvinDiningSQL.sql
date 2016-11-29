DROP TABLE IF EXISTS Response;
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
diningHall varchar(50),
questionType varchar(50),
question varchar(100),
option1 varchar(50),
option2 varchar(50),
option3 varchar(50),
option4 varchar(50)
    
);

CREATE TABLE Response
(
pollID int REFERENCES Poll(pollID),
personID int REFERENCES Person(ID),
answer1 boolean,
answer2 boolean,
answer3 boolean,
answer4 boolean
);



INSERT INTO Person VALUES (1, 'JohnCalvin', 'password', 33);
INSERT INTO Person VALUES (2, 'JohnDoe', '12345', 25);
INSERT INTO Person VALUES (3, 'RaymondStantz', 'helloworld', 45);
INSERT INTO Person VALUES (4, 'PeterVenkman', 'buster', 15);
INSERT INTO Person VALUES (5, 'EgonSpengler', 'password', 20);
INSERT INTO Person VALUES (6, 'BatMan', 'nanananan', 20);

INSERT INTO Poll VALUES (1,'Commons', 'multipleChoice', 'What should Commons add to the menu?', 'Lobster', 
'Swordfish', 'Crab', 'Clams');
INSERT INTO Poll VALUES (2,'Commons', 'multipleChoice', 'What should Commons never serve again?', 'Lobster', 
'Swordfish', 'Crab', 'Clams');
INSERT INTO Poll VALUES (3, 'Knollcrest', 'trueFalse', 'Did you enjoy you meal today?', 'Yes', 'No');
INSERT INTO Poll VALUES (4, 'Knollcrest', 'trueFalse', 'Did you not enjoy you meal today?', 'Yes', 'No');

INSERT INTO Response VALUES (1, 1, TRUE, FALSE, FALSE, FALSE);
INSERT INTO Response VALUES (1, 2, FALSE, TRUE, FALSE, FALSE);

INSERT INTO Response VALUES (2, 1, TRUE, FALSE, NULL, NULL);
INSERT INTO Response VALUES (2, 2, FALSE, TRUE, NULL, NULL);