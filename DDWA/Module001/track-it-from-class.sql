DROP DATABASE IF EXISTS TrackIt;

CREATE DATABASE TrackIt;

USE TrackIt;

CREATE TABLE Task(
	TaskId INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(100) NOT NULL,
    Details TEXT NULL,
    DueDate DATE NOT NULL,
    EstimatedHours DECIMAL(5,2) NULL
);

CREATE TABLE Project (
    ProjectId CHAR(50) PRIMARY KEY,
    `Name` VARCHAR(100) NOT NULL,
    Summary VARCHAR(2000) NULL,
    DueDate DATE NOT NULL,
    IsActive BIT NOT NULL DEFAULT 1
);

ALTER TABLE Task
    ADD COLUMN (
        ProjectId CHAR(50) NOT NULL
    ),
    ADD CONSTRAINT fk_Task_Project
        FOREIGN KEY (ProjectId) 
        REFERENCES Project(ProjectId);
        
        
CREATE TABLE Worker (
    WorkerId INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    ProjectId CHAR(50) NOT NULL,
	CONSTRAINT fk_Worker_Project FOREIGN KEY (ProjectId)
        REFERENCES Project(ProjectId)	
);

ALTER TABLE Worker
    DROP FOREIGN KEY fk_Worker_Project,
    DROP COLUMN ProjectId;
    
ALTER TABLE Worker
    MODIFY COLUMN FirstName VARCHAR(50) NOT NULL,
    MODIFY COLUMN LastName VARCHAR(50) NOT NULL;

CREATE TABLE ProjectWorker (
    ProjectId CHAR(50) NOT NULL,
    WorkerId INT NOT NULL,
    PRIMARY KEY pk_ProjectWorker (ProjectId, WorkerId),
    FOREIGN KEY fk_ProjectWorker_Project (ProjectId)
        REFERENCES Project(ProjectId),
    FOREIGN KEY fk_ProjectWorker_Worker (WorkerId)
        REFERENCES Worker(WorkerId)
);