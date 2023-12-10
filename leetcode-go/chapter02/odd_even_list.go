package main

// 奇偶链表
func oddEvenList(head *ListNode) *ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	oHead, eHead := head, head.Next
	oCurrent, eCurrent := oHead, eHead
	for oCurrent != nil && oCurrent.Next != nil &&
		eCurrent != nil && eCurrent.Next != nil {
		oCurrent.Next = eCurrent.Next
		oCurrent = oCurrent.Next
		eCurrent.Next = oCurrent.Next
		eCurrent = eCurrent.Next
	}
	oCurrent.Next = eHead
	return head
}
