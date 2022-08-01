package chapter02

// 删除链表中的重复元素 I
func deleteDuplicates1(head *ListNode) *ListNode {
	if head == nil {
		return nil
	}
	current := head
	for current.Next != nil {
		if current.Value == current.Next.Value {
			current.Next = current.Next.Next
		} else {
			current = current.Next
		}
	}
	return head

}

// 删除链表中的重复元素 II
func deleteDuplicates2(head *ListNode) *ListNode {
	dummy := &ListNode{0, head}
	current := dummy
	for current.Next != nil && current.Next.Next != nil {
		if current.Next.Value == current.Next.Next.Value {
			value := current.Next.Value
			for current.Next != nil && current.Next.Value == value {
				current.Next = current.Next.Next
			}
		} else {
			current = current.Next
		}
	}
	return dummy.Next
}

// 删除链表倒数第 N 个结点
func removeNthFromEnd(head *ListNode, n int) *ListNode {
	dummy := &ListNode{0, head}
	slow, fast := dummy, head
	for n > 0 {
		fast = fast.Next
		n--
	}
	for fast != nil {
		slow = slow.Next
		fast = fast.Next
	}
	slow.Next = slow.Next.Next
	return dummy.Next
}
