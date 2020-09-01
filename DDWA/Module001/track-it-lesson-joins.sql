USE TrackIt;

SELECT 
    *
FROM
    TaskStatus
WHERE
    IsResolved = 1;

SELECT 
    *
FROM
    Task
WHERE
    TaskStatusId BETWEEN 5 AND 8;
-- THIS QUERY GAVE 276 RESULTS EVEN THOUGH THE LESSON SAID 280. ?!?!?

SELECT 
    Task.TaskId, Task.Title, TaskStatus.Name
FROM
    TaskStatus
        INNER JOIN
    Task ON TaskStatus.TaskStatusId = Task.TaskStatusId
WHERE
    TaskStatus.IsResolved = 1;
    
SELECT 
    Project.Name, Worker.FirstName, Worker.LastName
FROM
    Project
        INNER JOIN
    ProjectWorker ON Project.ProjectId = ProjectWorker.ProjectId
        INNER JOIN
    Worker ON ProjectWorker.WorkerId = Worker.WorkerId
WHERE
    Project.ProjectId = 'game-goodboy';