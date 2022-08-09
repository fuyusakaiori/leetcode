package chapter02

// 复制带随机指针的链表
func copyRandomList(head *ListNode) *ListNode {
	originAndCopy := make(map[*ListNode]*ListNode)
	current := head
	for current != nil {
		originAndCopy[current] = &ListNode{current.Value, nil, nil}
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
