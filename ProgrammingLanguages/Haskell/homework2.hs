-- Filename   homework2.hs
-- Created    2013-09-17
-- Modified   2013-09-17
-- Author     David Fisher Evans
-- Notes
--            Methods to work with PNGs
--            Could not finish. Would have liked to have asked for help,
--            but more important things than homework have come up.
   
module VTC.PNG(hasPNGHeader, word8ListToIntList, chunkCount, splitPNG, computeCRC, verifyCRC) where

import Data.Bits
import Data.Word
import Data.Int

-- Returns True if the given list starts with a valid PNG signature.
hasPNGHeader :: [Word8] -> Bool
hasPNGHeader pngData = (take 8 pngData) == [137, 80, 78, 71, 13, 10, 26, 10]
						
					
-- converts a list of word8s into a list of int's by folding from consecutive word8s
word8ListToIntList :: [Word8] -> [Int]
word8ListToIntList list = if (length list) < 4
                          then []
						  else [listToInt (reverse (take 4 list)) 0] ++ word8ListToIntList (drop 4 list)

-- folds a list of word8's into a single int, shift bits to the left by 8 every fold						  
listToInt :: [Word8] -> Int -> Int
listToInt list shift = if list == []
                  then 0
				  else(fromIntegral(head list) :: Int)*(2^shift) + (listToInt (tail list) (shift + 8))


-- Returns the number of PNG chunks in the list.
chunkCount :: [Word8] -> Int
chunkCount pngData = let intList = word8ListToIntList pngData
                     in 
					     if intList == []
                         then 0
					     else 1 + (chunkCount (drop ((head intList) + 8) pngData))


-- Breaks the PNG file into a list of its chunks.
splitPNG :: [Word8] -> [[Word8]]
splitPNG pngData = []


-- Computes the CRC checksum over the given list using the algorithm in the PNG spec.
computeCRC :: [Word8] -> Word32
computeCRC rawData = complement $ foldl update 0xFFFFFFFF rawData
    where update :: Word32 -> Word8 -> Word32
          update c dataItem =
              let cLowByte = fromIntegral (c .&. 0x000000FF)
                  index    = cLowByte `xor` dataItem
                  intIndex = fromIntegral index
              in (crcTable !! intIndex) `xor` shift c (-8)

          crcTable :: [Word32]
          crcTable = [ processByte n | n <- [0 .. 255]]
              where processByte :: Word32 -> Word32
                    processByte c = bitLoop 7 c

                    bitLoop :: Int -> Word32 -> Word32
                    bitLoop 0       c = processBit c
                    bitLoop counter c = processBit $ bitLoop (counter - 1) c

                    processBit :: Word32 -> Word32
                    processBit c
                        | testBit c 0 = 0xEDB88320 `xor` shift c (-1)
                        | otherwise   = shift c (-1)


-- Verifies the CRC checksum on each chunk in a list of chunks.
verifyCRC :: [[Word8]] -> [Bool]
verifyCRC chunkList = []