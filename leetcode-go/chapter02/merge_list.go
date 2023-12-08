package main

import (
	"container/heap"
	pq "github.com/emirpasic/gods/queues/priorityqueue"
)

// 合并有序链表
func mergeTwoLists(first *ListNode, second *ListNode) *ListNode {
	dummy := new(ListNode)
	current := dummy
	for first != nil && second != nil {
		if first.Val < second.Val {
			current.Next = first
			first = first.Next
		} else {
			current.Next = second
			second = second.Next
		}
		current = current.Next
	}
	if first != nil {
		current.Next = first
	}
	if second != nil {
		current.Next = second
	}
	return dummy.Next
}

// 合并 K 个升序链表: 归并
func mergeKListsV1(lists []*ListNode) *ListNode {
	if len(lists) == 0 {
		return nil
	}
	return fork(lists, 0, len(lists)-1)
}

func fork(lists []*ListNode, left int, right int) *ListNode {
	if left >= right {
		return lists[left]
	}
	mid := left + ((right - left) >> 1)
	leftList := fork(lists, left, mid)
	rightList := fork(lists, mid+1, right)
	return merge(leftList, rightList)
}

func merge(leftList, rightList *ListNode) *ListNode {
	dummy := new(ListNode)
	current := dummy
	for leftList != nil && rightList != nil {
		if leftList.Val < rightList.Val {
			current.Next = leftList
			leftList = leftList.Next
		} else if leftList.Val >= rightList.Val {
			current.Next = rightList
			rightList = rightList.Next
		}
		current = current.Next
	}
	if leftList != nil {
		current.Next = leftList
	}
	if rightList != nil {
		current.Next = rightList
	}
	return dummy.Next
}

// 合并 K 个升序链表: 最小堆 (第三方库)
func mergeKListsV2(lists []*ListNode) *ListNode {
	// 1. 初始化最小堆
	priorityQueue := pq.NewWith(func(first, second interface{}) int {
		return first.(*ListNode).Val - second.(*ListNode).Val
	})
	for _, list := range lists {
		priorityQueue.Enqueue(list)
	}
	// 2. 遍历最小堆
	dummy := &ListNode{Val: 0}
	current := dummy
	for !priorityQueue.Empty() {
		node, _ := priorityQueue.Dequeue()
		// 如果节点还有下一个节点, 那么加入最小堆
		if node.(*ListNode).Next != nil {
			priorityQueue.Enqueue(node.(*ListNode).Next)
		}
		current.Next = node.(*ListNode)
		current = current.Next
	}
	return dummy.Next
}

// 合并 K 个升序链表: 最小堆 (自行实现)
func mergeKListsV3(lists []*ListNode) *ListNode {
	minHeap := nodeHeap{}
	// 1. 初始化最小堆
	for _, list := range lists {
		if list != nil {
			minHeap = append(minHeap, list)
		}
	}
	heap.Init(&minHeap)
	// 2. 遍历最小堆
	dummy := &ListNode{Val: 0}
	current := dummy
	for minHeap.Len() > 0 {
		node := heap.Pop(&minHeap).(*ListNode)
		if node.Next != nil {
			heap.Push(&minHeap, node.Next)
		}
		current.Next = node
		current = current.Next
	}
	return dummy.Next
}

type nodeHeap []*ListNode

func (heap *nodeHeap) Len() int {
	return len(*heap)
}

// 最小堆
func (heap *nodeHeap) Less(first, second int) bool {
	return (*heap)[first].Val < (*heap)[second].Val
}

func (heap *nodeHeap) Swap(first, second int) {
	(*heap)[first], (*heap)[second] = (*heap)[second], (*heap)[first]
}

func (heap *nodeHeap) Push(node any) {
	*heap = append(*heap, node.(*ListNode))
}

func (heap *nodeHeap) Pop() any {
	node := (*heap)[len(*heap)-1]
	*heap = (*heap)[:len(*heap)-1]
	return node
}
