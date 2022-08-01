package chapter02

// 环形链表: 哈希表
func hasCycle1(head *ListNode) *ListNode {
	current := head
	set := make(map[*ListNode]string)
	for current != nil {
		if _, ok := set[current]; ok {
			return current
		}
		set[current] = ""
		current = current.Next
	}
	return nil
}

// 环形链表: 快慢指针
func hasCycle2(head *ListNode) *ListNode {
	flag := true
	slow, fast := head, head

	for flag || slow != fast {
		if fast == nil || fast.Next == nil {
			return nil
		}
		flag = false
		slow = slow.Next
		fast = fast.Next
	}
	fast = head
	for slow != fast {
		slow = slow.Next
		fast = fast.Next
	}
	return slow
}
