-- CIS-3010 Quiz 1 
-- D. Fisher Evans 
-- 05-FEB-14 

-- Question 1 
SELECT DISTINCT city 
FROM   sp_suppliers 
ORDER  BY city ASC; 

-- Question 2 
SELECT sid, 
       sname, 
       city 
FROM   sp_suppliers 
WHERE  city IS NULL; 

-- Question 3 
SELECT sname 
FROM   sp_suppliers 
WHERE  sname LIKE '%s'; 

-- Question 4 
SELECT * 
FROM   sp_parts 
WHERE  pid IN ( 'P1', 'P3', 'P5' ); 

-- Question 5 
INSERT INTO sp_parts 
            (pid, 
             pname, 
             color, 
             weight, 
             city) 
VALUES      ('P6', 
             'BOLT', 
             'SILVER', 
             NULL, 
             NULL); 

-- Question 6 
SELECT Ceil(Avg(weight)) 
FROM   sp_parts; 

-- Question 7 
SELECT Concat(pname, ';', color) AS name_color, 
       Ifnull(weight, 0)         AS weight 
FROM   sp_parts; 

-- Question 8 
SELECT Count(sid) AS number, 
       city 
FROM   sp_suppliers 
GROUP  BY city; 

-- Question 9 
SELECT pid, 
       Sum(qty) AS total_quantity 
FROM   sp_shipments 
GROUP  BY pid; 

-- Question 10 
DELETE FROM sp_suppliers 
WHERE  sid = 'S6'; 

-- Question 11 
UPDATE sp_parts 
SET    weight = 6 
WHERE  pid = 'P6'; 

-- Question 12 
SELECT * 
FROM   sp_parts 
WHERE  weight BETWEEN 12 AND 14; 