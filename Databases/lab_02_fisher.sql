-- LAB 02 
-- D. FISHER EVANS 
-- 2014-01-28 
-- SELECT THE DATABASE 
USE lunches; 

-- QUESTION A1 
SELECT Max(credit_limit), 
       Max(hire_date) 
FROM   l_employees; 

-- QUESTION A2 
SELECT Count(supplier_id), 
       Count(DISTINCT supplier_id) 
FROM   l_foods; 

-- QUESTION A3 
SELECT Avg(price) 
FROM   l_foods; 

-- QUESTION A4 
SELECT ( 10 / 3 ), 
       Ceil(10 / 3), 
       Floor(10 / 3); 

-- QUESTION A5 
SELECT Sum(COALESCE(price, 0) 
           + COALESCE(price_increase, 0)) AS row_first, 
       Sum(COALESCE(price, 0)) 
       + Sum(COALESCE(price_increase, 0)) AS column_first 
FROM   l_foods; 

-- QUESTION B1 
-- NO INITCAP IN MYSQL 
SELECT Concat(Upper(Substr(first_name, 1, 1)), '. ', Upper( 
       Substr(last_name, 1, 1)), 
              Lower(Substr(last_name, 2, 255))) AS name 
FROM   l_employees; 

-- QUESTION B2 
SELECT description, 
       price, 
       COALESCE(price_increase, 0)                                  AS 
       price_increase, 
       Concat(Ceil(COALESCE(price_increase, 0) / price * 100), '%') AS 
       percent_increase 
FROM   l_foods; 

-- QUESTION B3 
SELECT description, 
       price 
FROM   l_foods 
WHERE  price != Floor(price); 

-- QUESTION B4 
SELECT To_days(Sysdate()) - To_days('1969-07-29'); 

-- QUESTION B5 
SELECT * 
FROM   l_suppliers 
WHERE  Substr(Soundex(supplier_name), 1, 4) = Substr(Soundex('frenc'), 1, 4); 

-- QUESTION C1 
INSERT INTO l_employees 
            (employee_id, 
             first_name, 
             last_name, 
             dept_code, 
             hire_date, 
             credit_limit, 
             phone_number, 
             manager_id) 
VALUES      (211, 
             'D. Fisher', 
             'Evans', 
             NULL, 
             '2014-01-28', 
             9999999, 
             1234, 
             211); 

-- QUESTION C2 
CREATE TABLE l_suppliers_new 
  ( 
     supplier_id   VARCHAR(3) NOT NULL, 
     supplier_name VARCHAR(30) NOT NULL, 
     contact_name  VARCHAR(30) NULL 
  ); 

INSERT INTO l_suppliers_new 
SELECT supplier_id, 
       supplier_name, 
       NULL 
FROM   l_suppliers; 

-- QUESTION C3 
INSERT INTO l_suppliers 
            (supplier_id, 
             supplier_name) 
VALUES      ('CFF', 
             'Cathy''s Fresh Fish'); 

INSERT INTO l_foods 
            (supplier_id, 
             product_code, 
             menu_item, 
             description, 
             price, 
             price_increase) 
VALUES      ('CFF', 
             'SA', 
             11, 
             'FRESH SALMON', 
             8.25, 
             NULL); 

SELECT s.supplier_name, 
       f.description 
FROM   l_suppliers AS s, 
       l_foods AS f 
WHERE  s.supplier_id = f.supplier_id; 

-- QUESTION C4 
DELETE FROM l_suppliers_new 
WHERE  supplier_id = 'ASP'; 

-- QUESTION C5 
DELETE FROM l_suppliers_new; 

-- QUESTION C6
UPDATE l_employees 
SET    dept_code = 'EXE' 
WHERE  employee_id = 211; 