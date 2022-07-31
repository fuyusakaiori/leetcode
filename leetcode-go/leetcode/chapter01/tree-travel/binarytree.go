package main

import "container/list"

// 前序遍历: 递归
func preorder1(root *TreeNode) (values []int) {
	// 定义函数
	var dfs func(node *TreeNode)
	// 函数赋值
	dfs = func(node *TreeNode) {
		if node == nil {
			return
		}
		values = append(values, node.Value)
		dfs(node.Left)
		dfs(node.Right)
	}
	// 调用函数
	dfs(root)
	return values
}

// 前序遍历: 迭代
func preorder2(root *TreeNode) (values []int) {
	// 创建栈
	stack := list.New()
	if root != nil {
		stack.PushBack(root)
	}
	for stack.Len() > 0 {
		// 出栈
		element := stack.Back()
		stack.Remove(element)
		// 强制转换
		node := element.Value.(*TreeNode)
		values = append(values, node.Value)
		// 先压右
		if node.Right != nil {
			stack.PushBack(node.Right)
		}
		// 再压左
		if node.Left != nil {
			stack.PushBack(node.Left)
		}
	}
	return values
}

// 前序遍历: morris
func preorder3(root *TreeNode) (values []int) {
	var current *TreeNode = root
	var mostRight *TreeNode
	for current != nil {
		// 如果左子结点不为空, 那么持续右移
		if current.Left != nil {
			mostRight = current.Left
			// 不停的右移直到为空或者重新指向当前结点
			for mostRight.Right != nil && mostRight.Right != current {
				mostRight = mostRight.Right
			}
			// 第一次到达
			if mostRight.Right == nil {
				values = append(values, current.Value)
				mostRight.Right = current
				// 当前结点左移
				current = current.Left
				continue
			} else {
				// 第二次到达
				mostRight.Right = nil
			}
		} else {
			values = append(values, current.Value)
		}
		// 如果左子结点为空, 那么直接右移
		current = current.Right
	}
	return values
}

// 中序遍历: 递归
func infixorder1(root *TreeNode) (values []int) {
	var dfs func(node *TreeNode)
	dfs = func(node *TreeNode) {
		if node == nil {
			return
		}
		dfs(node.Left)
		values = append(values, node.Value)
		dfs(node.Right)
	}
	dfs(root)
	return values
}

// 中序遍历: 迭代
func infixorder2(root *TreeNode) (values []int) {
	stack := list.New()
	for stack.Len() > 0 || root != nil {
		// 一直左压
		if root != nil {
			stack.PushBack(root)
			root = root.Left
		} else {
			// 直到为空开始右压
			root = stack.Remove(stack.Back()).(*TreeNode)
			values = append(values, root.Value)
			root = root.Right
		}
	}
	return values
}

// 中序遍历: morris
func infixorder3(root *TreeNode) (values []int) {
	var current *TreeNode = root
	var mostRight *TreeNode
	for current != nil {
		if current.Left != nil {
			mostRight = current.Left
			for mostRight.Right != nil && mostRight.Right != current {
				mostRight = mostRight.Right
			}
			if mostRight.Right == nil {
				mostRight.Right = current
				current = current.Left
				continue
			} else {
				mostRight.Right = nil
				values = append(values, current.Value)
			}
		} else {
			values = append(values, current.Value)
		}
		current = current.Right
	}

	return values
}

// 后序遍历: 递归
func postorder1(root *TreeNode) (values []int) {
	var dfs func(node *TreeNode)
	dfs = func(node *TreeNode) {
		if node == nil {
			return
		}
		dfs(node.Left)
		dfs(node.Right)
		values = append(values, node.Value)
	}
	dfs(root)
	return values
}

// 后序遍历: 迭代
func postorder2(root *TreeNode) (values []int) {
	// 创建栈
	stack := list.New()
	collection := list.New()
	if root != nil {
		stack.PushBack(root)
	}
	// 依次入栈
	for stack.Len() > 0 {
		node := stack.Remove(stack.Back()).(*TreeNode)
		collection.PushBack(node)
		if node.Left != nil {
			stack.PushBack(node.Left)
		}
		if node.Right != nil {
			stack.PushBack(node.Right)
		}
	}
	// 依次出栈
	for collection.Len() > 0 {
		node := collection.Remove(collection.Back()).(*TreeNode)
		values = append(values, node.Value)
	}
	return values
}

// 后序遍历: morris
func postorder3(root *TreeNode) (values []int) {

	return values
}

func main() {
}
