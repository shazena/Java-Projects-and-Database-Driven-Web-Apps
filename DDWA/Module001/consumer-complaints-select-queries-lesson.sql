USE ConsumerComplaints;

SELECT 
    *
FROM
    Complaint;


SELECT 
    Product, Issue, Company, ResponseToConsumer
FROM
    Complaint
WHERE
    ConsumerDisputed = 1
        AND ConsumerConsent = 1
        AND Product NOT IN ('Mortgage' , 'Debt collection');


SELECT 
    Product, ComplaintId, COUNT(Product)
FROM
    Complaint
WHERE
    ComplaintId BETWEEN 100000 AND 200000
GROUP BY Product;


SELECT 
    ComplaintId, DateReceived, Issue
FROM
    Complaint
WHERE
    DateReceived > '2015-01-01'
ORDER BY DateReceived DESC;                                     





