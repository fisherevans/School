-- LAB 04
-- D. FISHER EVANS 
-- 2014-02-06
USE lunches; 
SET sql_mode = 'ONLY_FULL_GROUP_BY'; 

-- A1
select e.last_name, l.employee_id, l.lunch_date
from l_employees e
left join l_lunches l using(employee_id)
where l.lunch_date is null;

-- A2
select s.supplier_id, s.supplier_name, count(f.supplier_id)
from l_foods f
right outer join l_suppliers s using(supplier_id)
group by s.supplier_id, s.supplier_name;

-- A3
