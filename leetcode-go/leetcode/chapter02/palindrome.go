package chapter02

// 回文链表
func isPalindrome(head *ListNode) bool {
	if head == nil {
		return true
	}
	// 1. 确定链表中点
	fast, slow := head, head
	for fast != nil && fast.Next != nil {
		slow = slow.Next
		fast = fast.Next.Next
	}
	// 2. 反转链表
	current := slow
	var previous, next *ListNode
	for current != nil {
		next = current.Next
		current.Next = previous
		previous = current
		current = next
	}
	// 3. 开始比较
	slow, fast = previous, head
	for slow != nil {
		if slow.Value != fast.Value {
			return false
		}
		slow = slow.Next
		fast = fast.Next
	}
	return true
}
