package main

// 扁平化多级双向链表
func flatten(root *Node) *Node {
	if root != nil {
		dfs(root)
	}
	return root
}

func dfs(head *Node) *Node {
	current, tail := head, head
	for current != nil {
		if current.Child == nil {
			tail = current
			current = current.Next
		} else {
			next := current.Next
			cHead := current.Child
			cTail := dfs(cHead)
			// 如果后继结点为空, 那么就不需要指向新增前驱结点
			if next != nil {
				next.Prev, cTail.Next = cTail, next
			}
			current.Next, cHead.Prev = cHead, current
			current.Child = nil
			current, tail = cTail, cTail
		}
	}
	return tail
}
