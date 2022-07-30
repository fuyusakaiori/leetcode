package main

import "fmt"

/*
枚举类型
(1) 常量定义枚举类型
(2) iota 可以实现自增, 类似于自增器, 0,1,2,3...
(3) iota 只可以在 const 中使用, 不可以单独使用
(4) iota 可以参与表达式计算, 不可以和字符串类型运算
(5) iota 的表达式可以中途变化, 但是值不会变
*/
const (
	BEIJING = 1.5 * iota
	SHANGHAI
	SHENZHEN
	GUANGZHOU

	CHONGQING = 2 * iota
	CHENGDU   = 2 * iota
	HANGZHOU  = 2 * iota
)

func main() {
	// 定义常量
	const length int = 10
	fmt.Printf("length = %d\n", length)
	// 定义枚举
	fmt.Println("BEIJING = ", BEIJING)
	fmt.Println("SHANGHAI = ", SHANGHAI)
	fmt.Println("SHENZHEN = ", SHENZHEN)
	fmt.Println("GUANGZHOU = ", GUANGZHOU)

	fmt.Println("CHONGQING = ", CHONGQING)
	fmt.Println("CHENGDU = ", CHENGDU)
	fmt.Println("HANGZHOU = ", HANGZHOU)

}
