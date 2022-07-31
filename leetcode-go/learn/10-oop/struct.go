package main

import "fmt"

// 声明类型别名
type neptune int

// Book 声明结构体
type Book struct {
	title string
	auth  string
}

func changBook(book *Book) {
	book.title = "java"
}

func main() {
	// 方法一
	var book Book
	book.title = "golang"
	book.auth = "zhangsan"
	fmt.Println(book)
	changBook(&book)
	fmt.Println(book)
}
