package main

import "fmt"

func swap(first *int, second *int) {
	var temp int = *first
	*first = *second
	*second = temp
}

func main() {
	// 指针应用
	var first, second int = 1, 2
	fmt.Println("swap before first = ", first, "second = ", second)
	swap(&first, &second)
	fmt.Println("swap after first = ", first, "second = ", second)
	// 一级指针
	var value int = 0
	var pointer *int = &value
	fmt.Println(&value)
	fmt.Println(pointer)
	// 指针是单独的类型
	fmt.Printf("%T\n", pointer)
	// 二级指针
	var pointerpointer **int = &pointer
	fmt.Println(&pointer)
	fmt.Println(pointerpointer)

}
