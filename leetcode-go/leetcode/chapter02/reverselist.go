package chapter02

// 反转链表: 迭代
func reverseList1(head *ListNode) *ListNode {
	var current *ListNode = head
	var previous, next *ListNode
	// 注: 在不需要修改指针指向内容的情况下, 不要使用星号来调用内容
	for current != nil {
		next = current.Next
		current.Next = previous
		previous = current
		current = next
	}
	return previous
}

// 反转链表: 递归
func reverseList2(head *ListNode) *ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	tail := reverseList2(head.Next)
	head.Next.Next = head
	head.Next = nil
	return tail
}

// 反转链表 II
func reverseListBetween(head *ListNode, left int, right int) *ListNode {
	// 注: 采用哑元可以避免很多空指针问题
	dummy := &ListNode{0, head}
	// 1. 找到左边界的前驱
	previous := dummy
	for index := 0; index < left-1; index++ {
		previous = previous.Next
	}
	// 2. 找到右边界
	rightNode := previous
	for index := 0; index < right-left+1; index++ {
		rightNode = rightNode.Next
	}
	// 3. 切割链表
	leftNode := previous.Next
	next := rightNode.Next
	previous.Next = nil
	rightNode.Next = nil
	// 4. 反转链表
	reverseList1(leftNode)
	previous.Next = rightNode
	leftNode.Next = next
	return dummy.Next
}

// K 个一组反转链表
func reverseListKGroup(head *ListNode, k int) *ListNode {
	cur := head
	dummy := &ListNode{0, head}
	pre, tail := dummy, dummy
	for cur != nil {
		for index := 0; index < k; index++ {
			tail = tail.Next
			if tail == nil {
				return dummy.Next
			}
		}
		next := tail.Next
		pre.Next = nil
		tail.Next = nil
		reverseList1(cur)
		pre.Next = tail
		cur.Next = next
		pre, tail = cur, cur
		cur = next
	}
	return dummy.Next
}
