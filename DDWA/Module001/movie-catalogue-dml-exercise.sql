DROP DATABASE IF EXISTS MovieCatalogue;

CREATE DATABASE MovieCatalogue;

USE MovieCatalogue;

CREATE TABLE Genre (
    GenreId INT PRIMARY KEY AUTO_INCREMENT,
    GenreName VARCHAR(30) NOT NULL
);
-- What's with int(11)??? WHY?


-- CREATE TABLE IF NOT EXISTS `Genre` (
-- 	`GenreID` int(11) not null auto_increment,
-- 	`GenreName` varchar(30) not null,
--     PRIMARY KEY (`GenreID`)
-- );


CREATE TABLE Director (
    DirectorId INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(30) NOT NULL,
    LastName VARCHAR(30) NOT NULL,
    BirthDate DATE NULL
);

-- CREATE TABLE IF NOT EXISTS `Director` (
-- 	`DirectorID` int(11) not null auto_increment,
-- 	`FirstName` varchar(30) not null,
-- 	`LastName` varchar(30) not null,
-- 	`BirthDate` date null,
--     PRIMARY KEY (`DirectorID`)
-- );


CREATE TABLE Rating (
    RatingId INT PRIMARY KEY AUTO_INCREMENT,
    RatingName VARCHAR(5) NOT NULL
);

-- CREATE TABLE IF NOT EXISTS `Rating` (
-- 	`RatingID` int(11) not null auto_increment,
-- 	`RatingName` varchar(5) not null,
--     PRIMARY KEY (`RatingID`)
-- );

CREATE TABLE Actor (
    ActorId INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(30) NOT NULL,
    LastName VARCHAR(30) NOT NULL,
    BirthDate DATE NULL
);

-- CREATE TABLE IF NOT EXISTS `Actor` (
-- 	`ActorID` int(11) not null auto_increment,
-- 	`FirstName` varchar(30) not null,
-- 	`LastName` varchar(30) not null,
-- 	`BirthDate` date null,
--     PRIMARY KEY (`ActorID`)
-- );

CREATE TABLE Movie (
    MovieId INT PRIMARY KEY AUTO_INCREMENT,
    GenreId INT NOT NULL,
    FOREIGN KEY (GenreId)
        REFERENCES Genre (GenreId),
    DirectorId INT NULL,
    FOREIGN KEY (DirectorId)
        REFERENCES Director (DirectorId),
    RatingId INT NULL,
    FOREIGN KEY (RatingId)
        REFERENCES Rating (RatingId),
    Title VARCHAR(128) NOT NULL,
    ReleaseDate DATE NULL
);

-- CREATE TABLE IF NOT EXISTS `Movie` (
-- 	`MovieID` int(11) not null auto_increment,
-- 	`GenreID` int(11) not null,
-- 	`DirectorID` int(11) null,
-- 	`RatingID` int(11) null,
-- 	`Title` varchar(128) not null,
-- 	`ReleaseDate` date null,
--     PRIMARY KEY (`MovieID`)
-- );

-- ALTER TABLE `Movie`
--  ADD CONSTRAINT `fk_MovieGenre` FOREIGN KEY (`GenreID`) REFERENCES `Genre`
-- (`GenreID`) ON DELETE NO ACTION;
-- ALTER TABLE `Movie`
--  ADD CONSTRAINT `fk_MovieDirector` FOREIGN KEY (`DirectorID`) REFERENCES `Director`
-- (`DirectorID`) ON DELETE NO ACTION;
-- ALTER TABLE `Movie`
--  ADD CONSTRAINT `fk_MovieRating` FOREIGN KEY (`RatingID`) REFERENCES `Rating`
-- (`RatingID`) ON DELETE NO ACTION;


CREATE TABLE CastMember (
    CastMemberId INT PRIMARY KEY AUTO_INCREMENT,
    ActorId INT NOT NULL,
    FOREIGN KEY (ActorId)
        REFERENCES Actor (ActorId),
    MovieId INT NOT NULL,
    FOREIGN KEY (MovieId)
        REFERENCES Movie (MovieId),
	`Role` VARCHAR(50) NOT NULL
);

-- CREATE TABLE IF NOT EXISTS `CastMember` (
-- 	`CastMemberID` int(11) not null auto_increment,
-- 	`ActorID` int(11) not null,
-- 	`MovieID` int(11) not null,
-- 	`Role` varchar(50) not null,
--     PRIMARY KEY (`CastMemberID`)
-- );

-- ALTER TABLE `CastMember`
--  ADD CONSTRAINT `fkCastMemberActor` FOREIGN KEY (`ActorID`) REFERENCES `Actor`
-- (`ActorID`) ON DELETE NO ACTION;


-- ALTER TABLE `CastMember`
--  ADD CONSTRAINT `fkCastMemberMovie` FOREIGN KEY (`MovieID`) REFERENCES `Movie`
-- (`MovieID`) ON DELETE NO ACTION;
    
-- -------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------
-- -------------------------------------------------------------------------------

INSERT INTO Actor (ActorId, FirstName, LastName, BirthDate) VALUES
	(1, 'Bill', 'Murray', '1950-09-21'),
    (2, 'Dan', 'Aykroyd', '1952-07-01'),
    (3, 'John', 'Candy', '1950-10-31'),
    (4, 'Steve', 'Martin', NULL),
    (5, 'Sylvester', 'Stallone', NULL);
    
INSERT INTO Director (DirectorId, FirstName, LastName, BirthDate) VALUES
	(1, 'Ivan', 'Reitman', '1946-10-27'),
    (2, 'Ted', 'Kotcheff', NULL);

INSERT INTO Rating (RatingId, RatingName) VALUES
	(1, 'G'),
    (2, 'PG'),
    (3, 'PG-13'),
    (4, 'R');

INSERT INTO Genre (GenreId, GenreName) VALUES
	(1, 'Action'),
    (2, 'Comedy'),
    (3, 'Drama'),
    (4, 'Horror');

INSERT INTO Movie (MovieId, GenreId, DirectorId, RatingId, Title, ReleaseDate) VALUES
 (1, 1, 2, 4, 'Rambo: First Blood', '1982-10-22'),
 (2, 2, NULL, 4, 'Planes, Trains, & Automobiles', '1987-11-25'),
 (3, 2, 1, 2, 'Ghostbusters', NULL),
 (4, 2, NULL, 2, 'The Great Outdoors', '1988-06-17');
 
INSERT INTO CastMember (CastMemberId, ActorId, MovieId, `Role`) VALUES
	(1, 5, 1, 'John Rambo'),
    (2, 4, 2, 'Neil Page'),
    (3, 3, 2, 'Del Griffith'),
    (4, 1, 3, 'Dr. Peter Venkman'),
    (5, 2, 3, 'Dr. Raymond Stanz'),
    (6, 2, 4, 'Roman Craig'),
    (7, 3, 4, 'Chet Ripley');

UPDATE Movie SET 
	Title = 'Ghostbusters (1984)',
    ReleaseDate = '1984-06-08'
WHERE 
	MovieId = 3;

UPDATE Genre 
SET 
    GenreName = 'Action/Adventure'
WHERE
    GenreId = 1;
    
-- Deleting Movie: Rambo
DELETE FROM CastMember 
WHERE
    MovieId = 1;
    
DELETE FROM Movie 
WHERE
    MovieId = 1;

ALTER TABLE Actor
	ADD COLUMN DateOfDeath DATE NULL;

UPDATE Actor 
SET 
    DateOfDeath = '1994-03-04'
WHERE
    ActorId = 3;
