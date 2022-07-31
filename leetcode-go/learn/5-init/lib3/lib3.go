package lib3

import "fmt"

// Lib3Test 全局可见
func Lib3Test() {
	fmt.Println("lib3Test()...")
}

func init() {
	fmt.Println("lib3 init()...")
}
