package chapter02

// 合并两个有序链表
func mergeTwoList(list1 *ListNode, list2 *ListNode) *ListNode {
	dummy := &ListNode{0, nil}
	newCur := dummy
	firstCur, secondCur := list1, list2
	for firstCur != nil && secondCur != nil {
		if firstCur.Value < secondCur.Value {
			newCur.Next = firstCur
			firstCur = firstCur.Next
		} else {
			newCur.Next = secondCur
			secondCur = secondCur.Next
		}
		newCur = newCur.Next
	}
	if firstCur != nil {
		newCur.Next = firstCur
	}
	if secondCur != nil {
		newCur.Next = secondCur
	}
	return dummy.Next
}

// TODO 大致思路没有问题, 但是无法通过
func mergeKLists(lists []*ListNode) *ListNode {
	return fork(lists, 0, len(lists)-1)
}

func fork(lists []*ListNode, left int, right int) *ListNode {
	if left >= right {
		return lists[left]
	}
	mid := left + ((right - left) >> 1)
	list1 := fork(lists, left, mid)
	list2 := fork(lists, mid+1, right)
	return mergeTwoList(list1, list2)
}
