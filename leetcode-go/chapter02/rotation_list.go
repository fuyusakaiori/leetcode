package main

// 旋转链表: 类似于删除倒数第 N 个节点
func rotateRight(head *ListNode, k int) *ListNode {
	if head == nil || head.Next == nil {
		return head
	}
	// 1. 获取链表长度
	length := 0
	for current := head; current != nil; current = current.Next {
		length++
	}
	// 2. 取模
	k = k % length
	if k == 0 {
		return head
	}
	// 3. 遍历链表
	slow, fast := head, head
	for index := 0; index < k; index++ {
		fast = fast.Next
	}
	for fast.Next != nil {
		slow, fast = slow.Next, fast.Next
	}
	// 4. 拆分链表
	nHead := slow.Next
	slow.Next, fast.Next = nil, head
	return nHead
}
