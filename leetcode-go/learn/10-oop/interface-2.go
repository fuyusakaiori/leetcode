package main

import "fmt"

func InterfaceAssert(arg interface{}) {
	value, ok := arg.(string)
	if !ok {
		fmt.Println("arg is not string")
	} else {
		fmt.Println("arg is string, value = ", value)
	}
}

func main() {
	InterfaceAssert(1)
	InterfaceAssert("shinobu")
}
