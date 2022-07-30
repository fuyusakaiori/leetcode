package main

import "fmt"

func main() {
	city := make(map[string]string)
	// 添加
	city["China"] = "ShangHai"
	city["Japan"] = "Kyoto"
	city["American"] = "New York"
	// 遍历
	for key, value := range city {
		fmt.Printf("key = %s value = %s\n", key, value)
	}
	// 删除
	delete(city, "China")
	// 修改
	city["American"] = "nil"

	for key, value := range city {
		fmt.Printf("key = %s value = %s\n", key, value)
	}
	// 引用传递
}
