package main

import "fmt"

func main() {
	// 方法一
	var hashmap map[string]string
	if hashmap == nil {
		fmt.Println("hashmap is empty")
	}
	hashmap = make(map[string]string, 10)
	hashmap["first"] = "java"
	hashmap["second"] = "c++"
	hashmap["third"] = "c#"
	fmt.Println(hashmap)
	// 方法二
	language := make(map[int]string)
	language[0] = "java"
	language[1] = "c++"
	language[2] = "c#"
	fmt.Println(language)
	// 方法三
	kv := map[string]string{
		"first": "python",
		"two":   "golang",
		"third": "rust",
	}
	fmt.Println(kv)
}
