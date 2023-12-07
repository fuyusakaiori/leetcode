package main

// 反转链表: 迭代
func reverseListV1(head *ListNode) *ListNode {
	var previous *ListNode = nil
	var current *ListNode = head
	for current != nil {
		next := current.Next
		current.Next = previous
		previous = current
		current = next
	}
	return previous
}

// 反转链表: 递归
func reverseListV2(head *ListNode) *ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	tail := reverseListV2(head.Next)
	head.Next.Next = head
	head.Next = nil
	return tail
}

// 反转链表 II
func reverseBetween(head *ListNode, left int, right int) *ListNode {
	dummy := &ListNode{0, head}
	leftBound := dummy
	// 1. 找到左右边界的前驱和后驱节点
	for index := 0; index < left - 1; index++ {
		leftBound = leftBound.Next
	}
	rightBound := leftBound
	for index := left - 1; index < right; index++ {
		rightBound = rightBound.Next
	}
	// 2. 断开左右边界的前驱和后驱节点
	start, end := leftBound.Next, rightBound.Next
	leftBound.Next, rightBound.Next = nil, nil
	// 3. 开始反转
	var previous *ListNode = nil
	var current *ListNode = start
	for current != nil {
		next := current.Next
		current.Next = previous
		previous = current
		current = next
	}
	leftBound.Next = previous
	start.Next = end
	return dummy.Next
}

// 反转链表: 两两交换
// 方法一：模拟
// 方法二：套 K 个一组的解法
func swapPairs(head *ListNode) *ListNode {
	dummy := &ListNode{Val: 0, Next: head}
	current := dummy
	for current.Next != nil && current.Next.Next != nil {
		node1, node2 := current.Next, current.Next.Next
		current.Next = node2
		node1.Next = node2.Next
		node2.Next = node1
		current = node2.Next
	}
	return dummy.Next
}

// 反转链表: K 个一组反转
func reverseKGroup(head *ListNode, k int) *ListNode {
	dummy := &ListNode{Val: 0, Next: head}
	previous, current := dummy, head
	for current != nil {
		leftBound, rightBound := previous, previous
		// 找到链接的前驱和后继结点
		for index := 0; index < k; index++ {
			rightBound = rightBound.Next
			if rightBound == nil {
				return dummy.Next
			}
		}
		// 断开前驱后继结点
		start, end := leftBound.Next, rightBound.Next
		leftBound.Next, rightBound.Next = nil, nil
		// 反转链表
		for current != nil {
			next := current.Next
			current.Next = previous
			previous = current
			current = next
		}
		leftBound.Next = previous
		start.Next = end
		previous, current = start, end
	}
	return dummy.Next
}
