-- LAB 01
-- D. FISHER EVANS
-- 2014-01-20

-- SELECT THE DATABASE
USE lunches;

-- QUESTION 1
SELECT * 
FROM   l_employees; 

-- QUESTION 2
SELECT employee_id, 
       last_name, 
       hire_date AS 'start date', 
       phone_number, 
       dept_code 
FROM   l_employees 
WHERE  dept_code = 'SAL' 
ORDER  BY last_name DESC; 

-- QUESTION 3
SELECT employee_id, 
       last_name, 
       hire_date, 
       phone_number, 
       dept_code 
FROM   l_employees 
WHERE  hire_date BETWEEN '2006-12-31' AND '2009-01-01' 
ORDER  BY hire_date DESC; 

-- QUESTION 4
SELECT employee_id, 
       last_name, 
       hire_date, 
       phone_number, 
       dept_code, 
       manager_id 
FROM   l_employees 
WHERE  manager_id IS NULL 
ORDER  BY employee_id DESC; 

-- QUESTION 5
SELECT DISTINCT lunch_date 
FROM   l_lunches 
ORDER  BY lunch_date DESC; 

-- QUESTION 6
SELECT * 
FROM   l_lunches 
ORDER  BY date_entered ASC; 

-- QUESTION 7
SELECT DISTINCT supplier_id, 
                product_code 
FROM   l_lunch_items 
ORDER  BY supplier_id, 
          product_code; 

-- QUESTION 8
SELECT * 
FROM   l_employees 
WHERE  first_name = 'SUSAN' 
       AND last_name = 'BROWN'; 

-- QUESTION 9
SELECT price 
FROM   l_foods 
WHERE  product_code = 'GS'; 

-- QUESTION 10
SELECT Concat('$', price) AS price 
FROM   l_foods 
WHERE  product_code = 'GS'; 

-- QUESTION 11
SELECT description, 
       Concat('$', price) AS price 
FROM   l_foods 
WHERE  price < 2 
ORDER  BY description; 

-- QUESTION 12
SELECT employee_id, 
       first_name, 
       last_name, 
       hire_date 
FROM   l_employees 
WHERE  hire_date < '2002-01-01' 
ORDER  BY 1; 

-- QUESTION 13
SELECT * 
FROM   l_employees 
WHERE  last_name IN ( 'WOODS', 'BROWN', 'OWENS' ); 

-- QUESTION 14
SELECT employee_id, 
       first_name, 
       last_name 
FROM   l_employees 
WHERE  last_name BETWEEN 'GZ' AND 'QA'; 

-- QUESTION 15
SELECT * 
FROM   l_employees 
WHERE  last_name LIKE '%S%'; 

-- QUESTION 16 A
SELECT phone_number, 
       last_name 
FROM   l_employees 
WHERE  phone_number LIKE '%4'; 

-- QUESTION 16 B
SELECT phone_number, 
       last_name 
FROM   l_employees 
WHERE  phone_number REGEXP '4$'; 

-- QUESTION 17
SELECT employee_id, 
       last_name, 
       phone_number 
FROM   l_employees 
WHERE  phone_number IS NULL; 

-- QUESTION 18
SELECT * 
FROM   l_suppliers 
WHERE  supplier_name = 'ALICE & RAY''S RESTAURANT'; 