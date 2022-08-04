package chapter02

func rotateRight(head *ListNode, k int) *ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	offset := k % getLength(head)
	dummy := &ListNode{0, head}
	slow, fast := dummy, head
	for index := 0; index < offset; index++ {
		fast = fast.Next
	}
	tail := fast
	for fast != nil {
		if fast.Next == nil {
			tail = fast
		}
		fast = fast.Next
		slow = slow.Next
	}
	tail.Next = dummy.Next
	dummy.Next = slow.Next
	slow.Next = nil
	return dummy.Next
}

func getLength(head *ListNode) (length int) {
	for head != nil {
		head = head.Next
		length++
	}
	return length
}
