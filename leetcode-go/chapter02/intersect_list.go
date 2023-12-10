package main

// 相交链表 (不带环): 哈希表
func getIntersectionNodeV1(first *ListNode, second *ListNode) *ListNode {
	// 1. 遍历链表
	set := make(map[*ListNode]struct{})
	for first != nil {
		set[first] = struct{}{}
		first = first.Next
	}
	// 2. 查询集合
	for second != nil {
		if _, ok := set[second]; ok {
			return second
		}
		second = second.Next
	}
	return nil
}

// 相交链表 (不带环): 长度之差
func getIntersectionNodeV2(first *ListNode, second *ListNode) *ListNode {
	// 1. 计算链表长度
	firstLength := getLength(first)
	secondLength := getLength(second)
	// 2. 获取长度差
	distance, largest, smallest := firstLength-secondLength, first, second
	if firstLength < secondLength {
		distance = secondLength - firstLength
		smallest, largest = first, second
	}
	// 3. 提前移动
	for index := 0; index < distance; index++ {
		largest = largest.Next
	}
	// 4. 同时移动
	for largest != smallest {
		largest = largest.Next
		smallest = smallest.Next
	}
	return smallest
}

func getLength(list *ListNode) int {
	length := 0
	for list != nil {
		list = list.Next
		length++
	}
	return length
}
