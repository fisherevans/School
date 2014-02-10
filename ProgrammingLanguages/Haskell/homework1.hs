-- Filename   homework1.hs
-- Created    2013-08-27
-- Modified   2013-08-27
-- Author     David Fisher Evans
-- Notes
--            This files was made by following
--            homework assignment #1 in
--            Programming Languages with Peter
--            Chapin

-- Problem #1
getSWR za zo = let rho = abs ((za-zo)/(za+zo))
                in (1+rho)/(1-rho)
        

-- Problem #4
rotateLeftN list n = let modN = mod n (length list)
                     in (drop modN list) ++ (take modN list)
-- Problem #2
rotateLeft list = rotateLeftN list 1
-- Problem #3
rotateLeft2 list = rotateLeftN list 2
             
-- Problem #5        
basicMath x y = (x+y, x-y, x*y, x/y)

-- Problem #6
makeLists x y = (x:[y], y:[x])

-- Problem #7
factors x = [y | y <- [1..x], (mod x y) == 0]

-- Given function to use in Problem #8
isPrime n = all (\x -> mod n x /= 0) [ 2 .. (n - 1) ]

-- Problem #8
getPrime100 = getPrimes 100
getPrimes n = getPrimesRange 1 n
getPrimesRange low high = [x | x <- [low..high], isPrime x]

-- Problem #9
getPrimeFactors x = [y | y <- (factors x), isPrime y]
