CREATE DATABASE IF NOT EXISTS MovieCatalogue;


USE MovieCatalogue;


CREATE TABLE IF NOT EXISTS `Genre` (
	`GenreID` int(11) not null auto_increment,
	`GenreName` varchar(30) not null,
    PRIMARY KEY (`GenreID`)
);

CREATE TABLE IF NOT EXISTS `Rating` (
	`RatingID` int(11) not null auto_increment,
	`RatingName` varchar(5) not null,
    PRIMARY KEY (`RatingID`)
);

CREATE TABLE IF NOT EXISTS `Director` (
	`DirectorID` int(11) not null auto_increment,
	`FirstName` varchar(30) not null,
	`LastName` varchar(30) not null,
	`BirthDate` date null,
    PRIMARY KEY (`DirectorID`)
);

CREATE TABLE IF NOT EXISTS `Actor` (
	`ActorID` int(11) not null auto_increment,
	`FirstName` varchar(30) not null,
	`LastName` varchar(30) not null,
	`BirthDate` date null,
    PRIMARY KEY (`ActorID`)
);

CREATE TABLE IF NOT EXISTS `Movie` (
	`MovieID` int(11) not null auto_increment,
	`GenreID` int(11) not null,
	`DirectorID` int(11) null,
	`RatingID` int(11) null,
	`Title` varchar(128) not null,
	`ReleaseDate` date null,
    PRIMARY KEY (`MovieID`)
);


ALTER TABLE `Movie`
 ADD CONSTRAINT `fk_MovieGenre` FOREIGN KEY (`GenreID`) REFERENCES `Genre`
(`GenreID`) ON DELETE NO ACTION;
ALTER TABLE `Movie`
 ADD CONSTRAINT `fk_MovieDirector` FOREIGN KEY (`DirectorID`) REFERENCES `Director`
(`DirectorID`) ON DELETE NO ACTION;
ALTER TABLE `Movie`
 ADD CONSTRAINT `fk_MovieRating` FOREIGN KEY (`RatingID`) REFERENCES `Rating`
(`RatingID`) ON DELETE NO ACTION;

CREATE TABLE IF NOT EXISTS `CastMember` (
	`CastMemberID` int(11) not null auto_increment,
	`ActorID` int(11) not null,
	`MovieID` int(11) not null,
	`Role` varchar(50) not null,
    PRIMARY KEY (`CastMemberID`)
);

ALTER TABLE `CastMember`
 ADD CONSTRAINT `fkCastMemberActor` FOREIGN KEY (`ActorID`) REFERENCES `Actor`
(`ActorID`) ON DELETE NO ACTION;


ALTER TABLE `CastMember`
 ADD CONSTRAINT `fkCastMemberMovie` FOREIGN KEY (`MovieID`) REFERENCES `Movie`
(`MovieID`) ON DELETE NO ACTION;