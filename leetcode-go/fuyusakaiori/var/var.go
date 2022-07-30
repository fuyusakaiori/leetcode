package main

import "fmt"

/*
四种变量的声明方式:
(1) 如果局部变量声明后没有使用, 那么编译就会报错, 全局变量不会
(2) 全局变量只能使用方法一、二、三, 局部变量可以使用任何方法来声明
*/
var global = 2333

func main() {
	//====================== 单变量声明 ======================
	// 方法一: 声明一个变量, 默认值 ""
	var name string
	fmt.Println("name = ", name)
	// 获取变量类型
	fmt.Printf("type of name = %T\n", name)
	// 方法二: 声明一个变量, 赋值
	var title string = "leetcode"
	fmt.Println("title = ", title)
	fmt.Printf("type of title = %T\n", title)
	// 方法三: 声明一个变量, 不指定类型
	var rank = 1
	fmt.Println("rank = ", rank)
	fmt.Printf("type of rank = %T\n", rank)
	// 方法四: 不使用任何关键声明一个变量
	flag := "true"
	fmt.Printf("flag = %s, type of flag = %T\n", flag, flag)

	pi := 3.1415926
	fmt.Printf("pi = %f, type of flag = %T\n", pi, pi)

	// 全局变量
	fmt.Printf("global = %d, type of global = %T\n", global, global)

	//====================== 声明多个变量 ======================
	var first, second int = 1, 2
	fmt.Printf("first = %d, second = %d\n", first, second)
	var id, nick, age = 1, "fuyusakaiori", 24
	fmt.Printf("id = %d, nick = %s age = %d\n", id, nick, age)
	var (
		key   int  = 1
		value bool = true
	)
	fmt.Println("key = ", key, "value = ", value)
}
