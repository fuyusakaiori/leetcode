package main

/*
导包
(1) GOPATH & GOMODULES
(2) 导包后必须要使用, 否则编译报错
(3) 匿名导入: _ 允许导入包但导入不使用
(3) 别名导入
(4) 静态导入
*/
import (
	_ "leetcode-go/learn/5-init/lib1"
	alias "leetcode-go/learn/5-init/lib2"
	. "leetcode-go/learn/5-init/lib3"
)

func main() {
	alias.Lib2Test()
	Lib3Test()
}
