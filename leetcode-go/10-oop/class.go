package main

import "fmt"

type Hero struct {
	name  string
	age   int
	level int
}

// GetName 方法接收者
func (hero *Hero) GetName() string {
	// 指针访问对象属性时不需要 * => hero.name <=> (*hero).name
	return (*hero).name
}

func (hero *Hero) GetAge() int {
	return hero.age
}

func (hero *Hero) GetLevel() int {
	return hero.level
}

func main() {
	// 结构体创建后得到的是指针, 但是作为参数传递是值传递
	hero := Hero{
		name:  "hero",
		age:   24,
		level: 6,
	}
	// TODO 对象调用方法 hero.GetName() <=> (&hero) 为什么是等价的
	fmt.Println((&hero).GetName())
	fmt.Printf("address = %p\n", &hero)
}
