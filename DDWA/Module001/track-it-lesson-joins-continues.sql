USE TrackIt;

SELECT
    Project.Name AS ProjectName,
    Worker.FirstName,
    Worker.LastName,
    ProjectWorker.ProjectId,
    ProjectWorker.WorkerId
FROM Project
RIGHT OUTER JOIN ProjectWorker ON Project.ProjectId = ProjectWorker.ProjectId
RIGHT OUTER JOIN Worker ON ProjectWorker.WorkerId = Worker.WorkerId
WHERE ProjectWorker.ProjectId IS NULL;
-- WHERE ProjectWorker.WorkerId IS NULL; // This works as well. Why?
-- This selects everything from worker and just pairs that with the contents of the other tables, ProjectWorker and Project. 
-- We can use either ProjectId IS NULL or WorkerId IS NULL since both will be null since the worker has not been assigned to a project!!!