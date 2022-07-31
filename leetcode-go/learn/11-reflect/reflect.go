package main

import (
	"fmt"
	"reflect"
)

type User struct {
	Id   int
	Name string
	Age  int
}

func (user *User) Call() {
	fmt.Printf("user = %v", user)
}

func ReflectNum(arg interface{}) {
	fmt.Println("type: ", reflect.TypeOf(arg))
	fmt.Println("value: ", reflect.ValueOf(arg))
}

func ReflectStruct(input interface{}) {
	inputType := reflect.TypeOf(input)
	fmt.Println("type name = ", inputType.Name())
	inputValue := reflect.ValueOf(input)
	fmt.Println("input value = ", inputValue)
	for index := 0; index < inputType.NumField(); index++ {
		// 反射不可以获取小写的字段
		field := inputType.Field(index)
		value := inputValue.Field(index).Interface()
		fmt.Println("field = ", field, "value = ", value)
	}
}

func main() {
	var num float64 = 1.23456
	ReflectNum(num)
	user := User{1, "shinobu", 2}
	ReflectStruct(user)
}
