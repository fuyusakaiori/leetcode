package main

import "fmt"

const (
	SUCCESS = 200
)

// foo1 返回单个值
func foo1(name string, param int) int {
	fmt.Println("function name = ", name)
	fmt.Println("function param = ", param)

	return SUCCESS
}

// foo2 返回两个值
func foo2(name string, param int) (int, string) {
	fmt.Println("function name = ", name)
	fmt.Println("function param = ", param)

	return SUCCESS, "success"
}

// foo3 返回两个值, 指定返回值名称
func foo3(name string, param int) (code int, message string) {
	fmt.Println("function name = ", name)
	fmt.Println("function param = ", param)

	return SUCCESS, "success"
}

// foo4 返回多个值, 同类型缩写
func foo4(name string, param int) (code int, message, result string) {
	fmt.Println("function name = ", name)
	fmt.Println("function param = ", param)
	// 返回参数在没有赋值的时候都是类型默认值
	return SUCCESS, "success", "neptune"
}

func main() {
	code1 := foo1("foo1", 1)
	fmt.Println("code1 = ", code1)
	code2, message2 := foo2("foo2", 2)
	fmt.Println("code2 = ", code2, "message2 = ", message2)
	code3, message3 := foo3("foo3", 3)
	fmt.Println("code3 = ", code3, "message3 = ", message3)
	code4, message4, value4 := foo4("foo4", 4)
	fmt.Println("code4 = ", code4, "message4 = ", message4, "value4 = ", value4)
}
