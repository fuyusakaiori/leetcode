package main

import "fmt"

func main() {
	// 切片声明方式
	// 方法一
	firstSlice := []int{1, 2, 3}
	// %v 表示详细信息
	fmt.Printf("length = %d, slice = %v\n", len(firstSlice), firstSlice)
	// 方法二
	secondSlice := make([]int, 3)
	fmt.Printf("length = %d, slice = %v\n", len(secondSlice), secondSlice)
	// 判断数组是否为空: 没有分配空间, 既是 nil 切片也是空切片, 分配 0, 只是空切片
	var thirdSlice []int = make([]int, 0)
	if thirdSlice == nil || len(thirdSlice) == 0 {
		fmt.Println("third slice is empty")
	}
}
