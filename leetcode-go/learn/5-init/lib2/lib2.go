package lib2

import "fmt"

// Lib2Test 全局可见
func Lib2Test() {
	fmt.Println("lib2Test()...")
}

func init() {
	fmt.Println("lib2 init()...")
}
