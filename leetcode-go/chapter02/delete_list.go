package main

// 删除重复元素
func deleteDuplicatesV1(head *ListNode) *ListNode {
	current := head
	for current != nil && current.Next != nil {
		if current.Val == current.Next.Val {
			current.Next = current.Next.Next
		} else {
			current = current.Next
		}
	}
	return head
}

// 删除重复元素 II
func deleteDuplicatesV2(head *ListNode) *ListNode {
	dummy := &ListNode{Val: 0, Next: head}
	current := dummy
	for current.Next != nil && current.Next.Next != nil {
		if current.Next.Val == current.Next.Next.Val {
			val := current.Next.Val
			for current.Next != nil && current.Next.Val == val {
				current.Next = current.Next.Next
			}
		} else {
			current = current.Next
		}
	}
	return dummy.Next
}

// 删除链表倒数第 N 个节点
func removeNthFromEnd(head *ListNode, n int) *ListNode {
	dummy := &ListNode{Val: 0, Next: head}
	slow, fast := dummy, dummy
	for index := 0; index <= n; index++ {
		fast = fast.Next
	}
	for fast != nil {
		slow, fast = slow.Next, fast.Next
	}
	if slow.Next != nil {
		slow.Next = slow.Next.Next
	}
	return dummy.Next
}
