package chapter02

// 两数相加
func addTwoNumbers1(l1 *ListNode, l2 *ListNode) *ListNode {
	carry, result := 0, 0
	first, second := l1, l2
	dummy := &ListNode{0, nil}
	current := dummy

	for first != nil && second != nil {
		result = first.Value + second.Value + carry
		carry = result / 10
		current.Next = &ListNode{result % 10, nil}
		current = current.Next
		first = first.Next
		second = second.Next
	}

	for first != nil {
		result = first.Value + carry
		carry = result / 10
		current.Next = &ListNode{result % 10, nil}
		current = current.Next
		first = first.Next
	}

	for second != nil {
		result = second.Value + carry
		carry = result / 10
		current.Next = &ListNode{result % 10, nil}
		current = current.Next
		second = second.Next
	}

	if carry == 1 {
		current.Next = &ListNode{carry, nil}
		current = current.Next
	}
	return dummy.Next
}

// 两数相加 II
func addTwoNumbers2(l1 *ListNode, l2 *ListNode) *ListNode {
	l1 = reverse(l1)
	l2 = reverse(l2)
	carry, result := 0, 0
	first, second := l1, l2
	dummy := &ListNode{0, nil}
	current := dummy

	for first != nil && second != nil {
		result = first.Value + second.Value + carry
		carry = result / 10
		current.Next = &ListNode{result % 10, nil}
		current = current.Next
		first = first.Next
		second = second.Next
	}

	for first != nil {
		result = first.Value + carry
		carry = result / 10
		current.Next = &ListNode{result % 10, nil}
		current = current.Next
		first = first.Next
	}

	for second != nil {
		result = second.Value + carry
		carry = result / 10
		current.Next = &ListNode{result % 10, nil}
		current = current.Next
		second = second.Next
	}

	if carry == 1 {
		current.Next = &ListNode{carry, nil}
		current = current.Next
	}
	return reverse(dummy.Next)
}

func reverse(head *ListNode) *ListNode {
	current := head
	var previous, next *ListNode
	for current != nil {
		next = current.Next
		current.Next = previous
		previous = current
		current = next
	}
	return previous
}
