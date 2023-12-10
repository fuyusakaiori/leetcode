package main

// 重排链表
func reorderList(head *ListNode) {
	// 1. 快慢指针找中点
	slow, fast := head, head
	for fast != nil && fast.Next != nil {
		slow, fast = slow.Next, fast.Next.Next
	}
	// 2. 反转链表
	var previous *ListNode
	for slow != nil {
		next := slow.Next
		slow.Next = previous
		previous = slow
		slow = next
	}
	// 3. 重排链表
	first, last := head, previous
	for last.Next != nil {
		fNext, lNext := first.Next, last.Next
		first.Next = last
		first = fNext
		last.Next = first
		last = lNext
	}
}
