// 包名: 包名可以任意指定, 和文件名目录名没有任何关系
package main

/* 导包: (1) 导入单个包 (2) 导入多个包
import "fmt"
import "time"
*/

import (
	"fmt"
	"time"
)

/* main 函数
(1) 函数括号只能采用压缩风格, 不能使用标准风格, 否则编译报错 => 强制代码风格
*/
func main() {
	fmt.Println("Hello Golang")
	time.Sleep(1 * time.Second)
	fmt.Println("Hello World")
}
