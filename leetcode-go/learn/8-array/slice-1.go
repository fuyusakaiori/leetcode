package main

import "fmt"

func main() {
	// 固定长度的数组
	var firstArray [10]int
	secondArray := [8]int{1, 2, 3, 4}
	thirdArray := [5]int{1, 2, 3, 4}
	// 遍历数组
	for index := 0; index < len(firstArray); index++ {
		fmt.Println("first array index = ", index, "value = ", firstArray[index])
	}
	for index, value := range secondArray {
		fmt.Println("second array index = ", index, "value = ", value)
	}
	// 数组类型
	fmt.Printf("type of first array = %T\n", firstArray)
	fmt.Printf("type of first array = %T\n", secondArray)
	fmt.Printf("type of first array = %T\n", thirdArray)
	// 函数中的数组依然是值拷贝

	// 动态数组 (切片)
	firstSlice := []int{1, 2, 3, 4}
	fmt.Printf("type of first array = %T\n", firstSlice)
	for index, value := range firstSlice {
		fmt.Println("first slice index = ", index, "value = ", value)
	}
}
