USE BullsAndCowsTEST; 

INSERT INTO	Game(Answer) values( '3456');
INSERT INTO Round(GameId, Guess, TimeOfGuess, ResultOfGuess) 
VALUES(1,'1234',current_timestamp(),'e:0:p:2');

INSERT INTO Game (Answer) VALUES ('1234');

SELECT * FROM Round;
Select * from Game;
INSERT INTO Game (GameId, Answer) values(1, '3459');

DELETE FROM Round WHERE GameId = 9;

INSERT INTO Round(GameId, Guess, TimeOfGuess, ResultOfGuess) 
VALUES(1, '1234',current_timestamp(),'e:0:p:2');

UPDATE Game SET Answer = '4567' , Status = 1 WHERE GameId = 1;


