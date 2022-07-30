package main

import "fmt"

func foo1() {
	fmt.Printf("main end 1\n")
}

func foo2() {
	fmt.Printf("main end 2\n")
}

func deferFunc() int {
	fmt.Println("defer func called")
	return 0
}

func returnFunc() int {
	fmt.Println("return fun called")
	return 0
}

/*
(1) return 不是原子操作, 分为两个步骤执行
(2) defer 在这两个步骤的中间执行
*/
func returnAndDefer() int {
	defer deferFunc()
	return returnFunc()
}

func main() {
	// defer 出栈入栈顺序
	defer foo1()
	defer foo2()

	fmt.Println("main::hello go 1")
	fmt.Println("main::hello go 2")

	returnAndDefer()
}
