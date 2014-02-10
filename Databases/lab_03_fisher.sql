-- LAB 03
-- D. FISHER EVANS 
-- 2014-02-06
USE lunches; 
SET sql_mode = 'ONLY_FULL_GROUP_BY'; 

-- A1  
SELECT Count(employee_id) AS count, 
       dept_code 
FROM   l_employees 
GROUP  BY dept_code; 

-- A2  
SELECT Count(product_code) AS count, 
       supplier_id 
FROM   l_foods 
GROUP  BY supplier_id 
ORDER  BY count DESC; 

-- A3  
SELECT Count(lunch_id) AS lunch_count, 
       lunch_date 
FROM   l_lunches 
GROUP  BY lunch_date; 

-- A4  
SELECT Count(employee_id) AS employee_count, 
       manager_id 
FROM   l_employees 
WHERE  manager_id IN ( 201, 202, 203 ) 
GROUP  BY manager_id; 

-- A5  
SELECT Count(d.bowl) AS bowl_count, 
       d.location 
FROM   (SELECT DISTINCT bowl, 
                        location 
        FROM   superbowl_history) AS d 
GROUP  BY d.location; 

-- A6  
SELECT supplier_id, 
       item_number, 
       Sum(quantity) 
FROM   l_lunch_items 
GROUP  BY supplier_id, 
          item_number WITH rollup; 

-- B1  
SELECT s.supplier_id, 
       f.description 
FROM   l_foods AS f, 
       l_suppliers AS s 
WHERE  f.supplier_id = s.supplier_id; 

SELECT s.supplier_id, 
       f.description 
FROM   l_foods AS f 
       JOIN l_suppliers AS s 
         ON f.supplier_id = s.supplier_id; 

-- B2  
SELECT e.first_name, 
       e.last_name, 
       l.lunch_date 
FROM   l_lunches AS l 
       JOIN l_employees AS e 
         ON l.employee_id = e.employee_id 
WHERE  lunch_date = '2011-11-16'; 

SELECT e.first_name, 
       e.last_name, 
       l.lunch_date 
FROM   l_lunches l 
       natural JOIN l_employees e 
WHERE  lunch_date = '2011-11-16'; 

-- B3  
SELECT f.description, 
       Sum(li.quantity) AS total_quantity 
FROM   l_foods f 
       JOIN l_lunch_items li using(supplier_id) 
GROUP  BY li.product_code; 

-- B4  
SELECT e.first_name, 
       e.last_name, 
       f.description, 
       l.lunch_date 
FROM   l_employees e 
       JOIN l_lunches l using(employee_id) 
       JOIN l_lunch_items li using(lunch_id) 
       JOIN l_foods f using(product_code, supplier_id) 
WHERE  e.first_name = 'SUSAN' 
       AND e.last_name = 'BROWN' 
       AND l.lunch_date = '2011-12-05'; 

-- B5 
SELECT Concat(e2.first_name, ' ', e2.last_name) AS manager 
FROM   l_employees e1 
       JOIN l_employees e2 
         ON ( e1.manager_id = e2.employee_id ) 
WHERE  e1.first_name = 'PAULA' 
       AND e1.last_name = 'JACOBS'; 

-- B6 
SELECT t.year, 
       t.theory, 
       h.team, 
       h.bowl 
FROM   superbowl_history h 
       JOIN superbowl_theory t 
         ON ( Year(h.bowl_date) = t.year ) 
WHERE  h.bowl = 'XXIII'; 