package chapter02

// 分隔链表
func partition(head *ListNode, target int) *ListNode {
	// 1. 双指针
	bigHead, smallHead := &ListNode{0, nil}, &ListNode{0, nil}
	bigTail, smallTail := bigHead, smallHead
	// 2. 遍历链表
	current := head
	for current != nil {
		if current.Value < target {
			smallTail.Next = current
			smallTail = smallTail.Next
		} else {
			bigTail.Next = current
			bigTail = bigTail.Next
		}
		current = current.Next
	}
	// 3. 连接两个链表
	bigTail.Next = nil
	smallTail.Next = bigHead.Next
	return smallHead.Next
}

// 分隔链表
func splitListToParts(head *ListNode, k int) []*ListNode {
	if head == nil {
		return make([]*ListNode, k)
	}
	// 1. 获取长度
	length := getLength(head)
	// 2. 分配链表数组
	lists := make([]*ListNode, k)
	// 3. 分隔链表
	groupIndex := 0
	current := head
	for groupIndex < k && current != nil {
		// 4. 记录每组链表的头结点
		start := current
		// 5. 计算每组大小
		groupSize := (length + (k - groupIndex) - 1) / (k - groupIndex)
		// 6. 减去每组大小
		length -= groupSize
		for groupSize > 1 {
			current = current.Next
			groupSize--
		}
		next := current.Next
		current.Next = nil
		lists[groupIndex] = start
		current = next
		groupIndex++
	}
	return lists
}
