package main

// 回文链表: 数组 + 双指针
func isPalindromeV1(head *ListNode) bool {
	// 1. 遍历链表
	stack := make([]*ListNode, 0)
	for head != nil {
		stack = append(stack, head)
		head = head.Next
	}
	// 2. 双指针遍历
	first, second := 0, len(stack)-1
	for first <= second {
		if stack[first].Val != stack[second].Val {
			return false
		}
		first++
		second--
	}
	return true
}

// 回文链表: 快慢指针 + 反转链表
func isPalindromeV2(head *ListNode) bool {
	slow, fast := head, head
	// 1. 快慢指针找中点
	for fast != nil && fast.Next != nil {
		slow = slow.Next
		fast = fast.Next.Next
	}
	// 2. 反转链表
	var current *ListNode = slow
	var previous *ListNode = nil
	for current != nil {
		next := current.Next
		current.Next = previous
		previous = current
		current = next
	}
	// 3. 遍历链表对比
	slow, fast = head, previous
	for fast != nil {
		if slow.Val != fast.Val {
			return false
		}
		slow, fast = slow.Next, fast.Next
	}
	return true
}
