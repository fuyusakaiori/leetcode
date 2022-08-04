package chapter02

func Partition(head *ListNode, target int) *ListNode {
	// 1. 双指针
	bigHead, smallHead := &ListNode{0, nil}, &ListNode{0, nil}
	bigTail, smallTail := bigHead, smallHead
	// 2. 遍历链表
	current := head
	for current != nil {
		if current.Value < target {
			smallTail.Next = current
			smallTail = smallTail.Next
		} else {
			bigTail.Next = current
			bigTail = bigTail.Next
		}
		current = current.Next
	}
	// 3. 连接两个链表
	bigTail.Next = nil
	smallTail.Next = bigHead.Next
	return smallHead.Next
}
