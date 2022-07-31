package main

import "fmt"

type IAnimal interface {
	Sleep()
}

type Cat struct {
	color string
}

func (cat *Cat) Sleep() {
	fmt.Println("cat is Sleep")
}

func (cat *Cat) GetColor() string {
	return cat.color
}

func main() {
	// 接口本身就是指针
	var animal IAnimal
	animal = &Cat{
		color: "red",
	}
	animal.Sleep()
	fmt.Println(animal)
}
