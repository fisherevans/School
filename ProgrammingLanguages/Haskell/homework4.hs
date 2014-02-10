-- Filename   homework4.hs
-- Created    2013-11-19
-- Modified   2013-11-22
-- Author     David Fisher Evans
-- Notes
--            Homework assignment based on custom data types.

module HomeworkFourFisher   
(
	Expression(..), evaluate,
	Distance(..), Dimensioned(..), toFeet, toMeters, toFurlongs,
	Box(..), ffmap
) where  

data Expression = Add Expression Expression | Subtract Expression Expression | Multiply Expression Expression | Divide Expression Expression | Constant Int deriving(Show)

evaluate (Add a b) = (evaluate a) + (evaluate b)
evaluate (Subtract a b) = (evaluate a) - (evaluate b)
evaluate (Multiply a b) = (evaluate a) * (evaluate b)
evaluate (Divide a b) = (evaluate a) `div` (evaluate b)
evaluate (Constant a) = a

data Distance = Feet | Meters | Furlongs deriving(Show)
data Dimensioned = Dimensioned (Double, Distance) deriving(Show)

toFeet :: Dimensioned -> Dimensioned
toFeet (Dimensioned (a, Feet)) = Dimensioned (a, Feet)
toFeet (Dimensioned (a, Meters)) = Dimensioned (a * 3.28084, Feet) 
toFeet (Dimensioned (a, Furlongs)) = Dimensioned (a * 660, Feet) 

toMeters :: Dimensioned -> Dimensioned
toMeters (Dimensioned (a, Feet)) = Dimensioned (a * 0.3048, Meters) 
toMeters (Dimensioned (a, Meters)) = Dimensioned (a, Meters)
toMeters (Dimensioned (a, Furlongs)) = Dimensioned (a * 201.16800, Meters) 

toFurlongs :: Dimensioned -> Dimensioned
toFurlongs (Dimensioned (a, Feet)) = Dimensioned (a * 0.00151515152 , Furlongs) 
toFurlongs (Dimensioned (a, Meters)) = Dimensioned (a * 0.00497096954, Furlongs) 
toFurlongs (Dimensioned (a, Furlongs)) = Dimensioned (a, Furlongs)

data Box a = Box a deriving(Show)
ffmap :: (a -> b) -> Box a -> Box b
ffmap f (Box a) = Box (f a)