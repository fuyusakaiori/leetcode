package main

// 两数相加
func addTwoNumbers(first *ListNode, second *ListNode) *ListNode {
	carry, dummy := 0, new(ListNode)
	tail := dummy
	for first != nil || second != nil {
		firstVal, secondVal := 0, 0
		if first != nil {
			firstVal = first.Val
			first = first.Next
		}
		if second != nil {
			secondVal = second.Val
			second = second.Next
		}
		result := (firstVal + secondVal + carry) % 10
		carry = (firstVal + secondVal + carry) / 10
		node := &ListNode{Val: result}
		tail.Next = node
		tail = tail.Next
	}
	if carry != 0 {
		tail.Next = &ListNode{Val: carry}
	}
	return dummy.Next
}

// 两数相加 II
func addTwoNumbersV2(first *ListNode, second *ListNode) *ListNode {
	// 反转链表
	first, second = reverseListV1(first), reverseListV1(second)
	// 模拟计算
	carry, dummy := 0, new(ListNode)
	tail := dummy
	for first != nil || second != nil {
		firstVal, secondVal := 0, 0
		if first != nil {
			firstVal = first.Val
			first = first.Next
		}
		if second != nil {
			secondVal = second.Val
			second = second.Next
		}
		result := (firstVal + secondVal + carry) % 10
		carry = (firstVal + secondVal + carry) / 10
		node := &ListNode{Val: result}
		tail.Next = node
		tail = tail.Next
	}
	if carry != 0 {
		tail.Next = &ListNode{Val: carry}
	}
	return reverseListV1(dummy.Next)
}
