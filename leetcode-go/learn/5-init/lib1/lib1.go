package lib1

import "fmt"

// Lib1Test 全局可见
func Lib1Test() {
	fmt.Println("lib1Test()...")
}

// init 初始化方法, 默认自动执行
func init() {
	fmt.Println("lib1 init()...")
}
