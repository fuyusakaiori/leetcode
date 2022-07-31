package main

import "fmt"

func main() {
	// 预开辟: 如果没有预先分配, 那么是可以动态扩容的
	numbers := make([]int, 3, 3)
	numbers[2] = 2
	fmt.Printf("numbers length = %d, capcity = %d, numbers=%v address=%p\n", len(numbers), cap(numbers), numbers, &numbers)
	// 追加
	numbers = append(numbers, 1)
	fmt.Printf("numbers length = %d, capcity = %d, numbers=%v address=%p\n", len(numbers), cap(numbers), numbers, &numbers)
	// 截取: 左闭右开
	nums1 := numbers[0:2]
	fmt.Printf("numbers length = %d, capcity = %d, numbers=%v address=%p\n", len(nums1), cap(nums1), nums1, &nums1)
	// 切片截取后依然指向同一块内存区域
	nums1[0] = 100
	fmt.Printf("numbers length = %d, capcity = %d, numbers=%v address=%p\n", len(numbers), cap(numbers), numbers, &numbers)
	fmt.Printf("numbers length = %d, capcity = %d, numbers=%v address=%p\n", len(nums1), cap(nums1), nums1, &nums1)
	// 复制
	nums2 := make([]int, 4)
	// 拷贝就是真的拷贝一个新的切片出去
	copy(nums2, numbers)
	fmt.Printf("numbers length = %d, capcity = %d, numbers=%v address=%p\n", len(nums2), cap(nums2), nums2, &nums2)
}
