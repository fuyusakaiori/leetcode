package chapter02

// 奇偶链表
func oddEvenList(head *ListNode) *ListNode {
	if head == nil {
		return head
	}
	node := head.Next
	oddHead, evenHead := head, head.Next
	for evenHead != nil && evenHead.Next != nil {
		oddHead.Next = evenHead.Next
		oddHead = oddHead.Next
		evenHead.Next = oddHead.Next
		evenHead = evenHead.Next
	}
	oddHead.Next = node
	return head
}
