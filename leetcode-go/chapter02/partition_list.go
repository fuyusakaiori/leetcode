package main

import (
	"math"
)

// 分隔链表
func partition(head *ListNode, target int) *ListNode {
	// 1. 准备大小链表
	sHead, bHead := new(ListNode), new(ListNode)
	sTail, bTail := sHead, bHead
	// 2. 遍历链表
	current := head
	for current != nil {
		if current.Val < target {
			sTail.Next = current
			sTail = sTail.Next
		} else {
			bTail.Next = current
			bTail = bTail.Next
		}
		current = current.Next
	}
	// 3. 衔接链表
	sTail.Next = bHead.Next
	return sHead.Next
}

// 分隔链表
func splitListToParts(head *ListNode, cnt int) []*ListNode {
	// 1. 遍历链表获取长度
	length := 0
	for current := head; current != nil; current = current.Next {
		length++
	}
	// 2. 遍历链表
	part, lists := 0, make([]*ListNode, cnt)
	for index, current := 0, head; index < cnt && current != nil; index++ {
		length = length - part
		part = int(math.Ceil(float64(length) / float64(cnt-index)))
		nHead := current
		for index := 0; index < part-1; index++ {
			current = current.Next
		}
		current, current.Next = current.Next, nil
		lists[index] = nHead
	}
	return lists
}
