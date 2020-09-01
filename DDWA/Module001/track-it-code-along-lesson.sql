-- DROP DATABASE IF EXISTS TrackItLesson;

-- CREATE DATABASE TrackItLesson;

-- USE TrackItLesson;

-- CREATE TABLE Task (
--     TaskId INT PRIMARY KEY AUTO_INCREMENT,
--     Title VARCHAR(100) NOT NULL,
--     Details TEXT NULL,
--     DueDate DATE NOT NULL,
--     EstimatedHours DECIMAL(5 , 2 ) NULL
-- );

-- CREATE TABLE Project (
--     ProjectId CHAR(50) PRIMARY KEY,
--     `Name` VARCHAR(100) NOT NULL,
--     Summary VARCHAR(2000) NULL,
--     DueDate DATE NOT NULL,
--     IsActive BOOL NOT NULL DEFAULT 1
-- );

-- ALTER TABLE Task 
-- 	ADD COLUMN (
-- 		ProjectId CHAR(50) NOT NULL
-- 	),
--     ADD CONSTRAINT fk_Task_Project
-- 		FOREIGN KEY (ProjectId)
-- 		REFERENCES Project(ProjectId);

-- CREATE TABLE Worker (
--     WorkerId INT PRIMARY KEY AUTO_INCREMENT,
--     FirstName VARCHAR(50),
--     LastName VARCHAR(50),
--     ProjectId CHAR(50) NOT NULL,
--     CONSTRAINT fk_Worker_Project FOREIGN KEY (ProjectId)
--         REFERENCES Project (ProjectId)
-- );

-- ALTER TABLE Worker
-- 	DROP FOREIGN KEY fk_Worker_Project,
-- 	DROP COLUMN ProjectId;

-- ALTER TABLE Worker
-- 	MODIFY COLUMN FirstName VARCHAR(50) NOT NULL,
--     MODIFY COLUMN LastName VARCHAR(50) NOT NULL;

-- CREATE TABLE ProjectWorker (
--     ProjectId CHAR(50) NOT NULL,
--     WorkerId INT NOT NULL,
--     PRIMARY KEY pk_ProjectWorker (ProjectId , WorkerId),
--     FOREIGN KEY (ProjectId)
--         REFERENCES Project (ProjectId),
--     FOREIGN KEY (WorkerId)
--         REFERENCES Worker (WorkerId)
-- );

-------------------------------------------------------------------
DROP DATABASE IF EXISTS TrackItLesson;

CREATE DATABASE TrackItLesson;

USE TrackItLesson;

CREATE TABLE Project (
    ProjectId CHAR(50) PRIMARY KEY,
    `Name` VARCHAR(100) NOT NULL,
    Summary VARCHAR(2000) NULL,
    DueDate DATE NOT NULL,
    IsActive BOOL NOT NULL DEFAULT 1
);

CREATE TABLE Worker (
	WorkerId INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL
);

CREATE TABLE ProjectWorker (
    ProjectId CHAR(50) NOT NULL,
    WorkerId INT NOT NULL,
    PRIMARY KEY pk_ProjectWorker (ProjectId , WorkerId),
    FOREIGN KEY (ProjectId)
        REFERENCES Project (ProjectId),
    FOREIGN KEY (WorkerId)
        REFERENCES Worker (WorkerId)
);

CREATE TABLE Task (
    TaskId INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(100) NOT NULL,
    Details TEXT NULL,
    EstimatedHours DECIMAL(5 , 2 ) NULL,
    ProjectId CHAR(50) NOT NULL,
    WorkerId INT NOT NULL,
    FOREIGN KEY (ProjectId , WorkerId)
        REFERENCES ProjectWorker (ProjectId , WorkerId)
);


-- ------------------------------------------------------------
-- Below is the DML Lesson Code along
-- ------------------------------------------------------------
INSERT INTO Worker (WorkerId, FirstName, LastName)
	VALUES (1, 'Rosemonde', 'Featherbie');
    
INSERT INTO Worker (FirstName, LastName)
	VALUES ('Kingsly', 'Besantie');

INSERT INTO Worker (FirstName, LastName) VALUES 
    ('Goldi', 'Pilipets'),
	('Dorey', 'Rulf'),
    ('Panchito', 'Ashtonhurst');

INSERT INTO Worker (WorkerId, FirstName, LastName)
	VALUES (50, 'Valentino', 'Newvill');

INSERT INTO Worker (FirstName, LastName)
	VALUES ('Shazena', 'Khan');

SELECT * FROM Worker;

INSERT INTO Project (ProjectId, `Name`, DueDate)
	VALUES ('db-milestone', 'Database Material', '2018-12-31');

INSERT INTO ProjectWorker (ProjectId, WorkerId) 
	VALUES ('db-milestone', 2);

INSERT INTO Project (ProjectId, `Name`, DueDate)
	VALUES ('kitchen', 'Kitchen Remodel', '2025-07-15');

INSERT INTO ProjectWorker (ProjectId, WorkerId) VALUES
	('db-milestone', 1),
    ('kitchen', 2),
    ('db-milestone', 3),
    ('db-milestone', 4);

-- The following is used to allow a user to write an 
-- update function without specifying a where condition.
-- It must be re-enabled after you're done.
-- SET SQL_SAFE_UPDATES = 0;
-- SET SQL_SAFE_UPDATES = 1;

UPDATE Project 
SET 
    Summary = 'All lessons and exercises for the relational database milestone.',
    DueDate = '2018-10-15'
WHERE
    ProjectId = 'db-milestone';

UPDATE Worker 
SET 
    LastName = 'Oaks'
WHERE
    WorkerId = 2;

-- Disable safe updates.
SET SQL_SAFE_UPDATES = 0;

UPDATE Project 
SET 
    IsActive = 0
WHERE
    DueDate BETWEEN '2017-01-01' AND '2017-12-31'
        AND IsActive = 1;
        
-- Enable safe updates.
SET SQL_SAFE_UPDATES = 1;


-- Do you need safe updates disabled? YES
SET SQL_SAFE_UPDATES = 0;

-- Update all of Kingsly's Task estimates to include 25% more time.
UPDATE Task 
SET 
    EstimatedHours = EstimatedHours * 1.25
WHERE
    WorkerId = 2;

SET SQL_SAFE_UPDATES = 1;

DELETE FROM Worker 
WHERE
    WorkerId = 50;

SET SQL_SAFE_UPDATES = 0;

DELETE FROM Task 
WHERE
    WorkerId = 2;

DELETE FROM ProjectWorker 
WHERE
    WorkerId = 2;

DELETE FROM Worker 
WHERE FirstName = 'Kingsly';

SET  SQL_SAFE_UPDATES = 1;

