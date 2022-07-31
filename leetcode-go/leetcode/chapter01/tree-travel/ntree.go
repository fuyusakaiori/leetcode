package main

import "container/list"

// N叉树前序遍历: 递归
func preorderNTree1(root *NTreeNode) (values []int) {
	var dfs func(node *NTreeNode)
	dfs = func(node *NTreeNode) {
		if node == nil {
			return
		}
		values = append(values, node.Value)
		for _, child := range node.Children {
			dfs(child)
		}
	}
	dfs(root)
	return values
}

// N叉树前序遍历: 迭代
func preorderNTree2(root *NTreeNode) (values []int) {
	stack := list.New()
	if root != nil {
		stack.PushBack(root)
	}
	for stack.Len() > 0 {
		root = stack.Remove(stack.Back()).(*NTreeNode)
		values = append(values, root.Value)
		for index := len(root.Children) - 1; index >= 0; index-- {
			if root.Children[index] != nil {
				stack.PushBack(root.Children[index])
			}
		}
	}
	return values
}

// N叉树后序遍历: 递归
func postorderNTree1(root *NTreeNode) (values []int) {
	var dfs func(node *NTreeNode)
	dfs = func(node *NTreeNode) {
		if node == nil {
			return
		}
		for _, child := range node.Children {
			dfs(child)
		}
		values = append(values, node.Value)
	}
	dfs(root)
	return values
}

// N叉树后序遍历: 迭代
func postorderNTree2(root *NTreeNode) (values []int) {
	stack := list.New()
	collection := list.New()
	if root != nil {
		stack.PushBack(root)
	}
	for stack.Len() > 0 {
		node := stack.Remove(stack.Back()).(*NTreeNode)
		collection.PushBack(node)
		for _, child := range node.Children {
			if child != nil {
				stack.PushBack(child)
			}
		}
	}
	for collection.Len() > 0 {
		values = append(values, collection.Remove(collection.Back()).(*NTreeNode).Value)
	}
	return values
}
