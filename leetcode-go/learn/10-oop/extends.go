package main

import "fmt"

// Human 父类
type Human struct {
	sex string
	age int
}

func (human *Human) Walk() {
	fmt.Println("human walk...")
}

func (human *Human) Run() {
	fmt.Println("human run...")
}

// SuperHuman 子类
type SuperHuman struct {
	human Human
	name  string
}

func (human *SuperHuman) Walk() {
	fmt.Println("super human walk...")
}

func (human *SuperHuman) Run() {
	fmt.Println("super human run...")
}

func (human *SuperHuman) Fly() {
	fmt.Println("super human fly...")
}

func main() {
	// 方法一
	human1 := SuperHuman{
		human: Human{"female", 1},
		name:  "superhuman",
	}
	human1.Run()
	human1.Walk()
	human1.Fly()
	fmt.Println(human1)
	// 方法二
	var human2 SuperHuman
	human2.human.age = 24
	human2.human.sex = "male"
	human2.name = "shinobu"
	fmt.Println(human2)
}
