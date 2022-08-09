package main

import "leetcode-go/leetcode/chapter02"

func main() {
	head := &chapter02.ListNode{}
	node1 := &chapter02.ListNode{}
	node2 := &chapter02.ListNode{}
	node3 := &chapter02.ListNode{}
	node4 := &chapter02.ListNode{}
	node5 := &chapter02.ListNode{}
	head.Value = 1
	head.Next = node1
	node1.Value = 4
	node1.Next = node2
	node2.Value = 3
	node2.Next = node3
	node3.Value = 2
	node3.Next = node4
	node4.Value = 5
	node4.Next = node5
	node5.Value = 2

	chapter02.Partition(head, 3)
}
