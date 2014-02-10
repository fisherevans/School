sumList :: [Int] -> Int
sumList (x:y) = if y == []
				then x
				else x + sum y
(testAdd) x y = x + y