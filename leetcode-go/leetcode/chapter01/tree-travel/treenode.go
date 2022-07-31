package main

type TreeNode struct {
	Value int
	Left  *TreeNode
	Right *TreeNode
}

type NTreeNode struct {
	Value    int
	Children []*NTreeNode
}
