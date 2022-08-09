package chapter02

// 复制带随机指针的链表
func copyRandomList(head *Node) *Node {
	originAndCopy := make(map[*Node]*Node)
	current := head
	for current != nil {
		originAndCopy[current] = &Node{current.Value, nil, nil}
		current = current.Next
	}
	current = head
	for current != nil {
		clone := originAndCopy[current]
		clone.Next = originAndCopy[current.Next]
		clone.Random = originAndCopy[current.Random]
		current = current.Next
	}
	return originAndCopy[head]
}
