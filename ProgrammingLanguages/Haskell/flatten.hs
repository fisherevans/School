data Tree a = Leaf | Node (Tree a) a (Tree a)

countLeaves :: Tree a -> Int
countLeaves Leaf = 0
countLeaves (Node Leaf _ Leaf) = 1
countLeaves (Node tree1 _ tree2) = countLeaves(tree1) + countLeaves(tree2)