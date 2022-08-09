package chapter02

// 排序链表
func sortList(head *ListNode) *ListNode {
	length := getLength(head)
	dummy := &ListNode{0, head}
	for subLength := 1; subLength < length; subLength <<= 1 {
		current, previous := dummy.Next, dummy
		for current != nil {
			firstHead := current
			for index := 1; index < subLength && current.Next != nil; index++ {
				current = current.Next
			}
			firstTail := current
			current = current.Next
			secondHead := current
			for index := 1; index < subLength && current != nil && current.Next != nil; index++ {
				current = current.Next
			}
			secondTail := current
			firstTail.Next = nil
			var next *ListNode
			if secondTail != nil {
				next = secondTail.Next
				secondTail.Next = nil
			}
			previous.Next = merge(firstHead, secondHead)
			for previous.Next != nil {
				previous = previous.Next
			}
			current = next
		}
	}
	return dummy.Next
}

func merge(list1 *ListNode, list2 *ListNode) *ListNode {
	dummy := &ListNode{0, nil}
	current := dummy
	for list1 != nil && list2 != nil {
		if list1.Value < list2.Value {
			current.Next = list1
			list1 = list1.Next
		} else {
			current.Next = list2
			list2 = list2.Next
		}
		current = current.Next
	}
	if list1 != nil {
		current.Next = list1
	}
	if list2 != nil {
		current.Next = list2
	}
	return dummy.Next
}
