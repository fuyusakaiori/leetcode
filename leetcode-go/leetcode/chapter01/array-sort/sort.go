package main

import (
	"fmt"
	"math/rand"
)

const LIMIT = 50

// BubbleSort 冒泡排序
func BubbleSort(numbers []int) {
	length := len(numbers)
	for first := 0; first < length; first++ {
		// 注: 冒泡每轮可以确定最后一个元素的位置, 所以长度应该递减
		for second := 0; second < length-1-first; second++ {
			if numbers[second] > numbers[second+1] {
				Swap(&numbers[second], &numbers[second+1])
			}
		}
	}
}

// InsertSort 插入排序: 向前看
func InsertSort(numbers []int) {
	length := len(numbers)
	for first := 0; first < length; first++ {
		for second := first; second > 0; second-- {
			if numbers[second-1] > numbers[second] {
				Swap(&numbers[second], &numbers[second-1])
			}
		}
	}
}

// SelectSort 插入排序: 向后看
func SelectSort(numbers []int) {
	length := len(numbers)
	for first := 0; first < length; first++ {
		minIndex := first
		for second := first; second < length; second++ {
			if numbers[second] < numbers[minIndex] {
				minIndex = second
			}
		}
		Swap(&numbers[first], &numbers[minIndex])
	}
}

// MergeSort 归并排序
func MergeSort(numbers []int) {
	Fork(numbers, 0, len(numbers)-1)
}

func Fork(numbers []int, left int, right int) {
	if left >= right {
		return
	}
	mid := left + ((right - left) >> 1)
	Fork(numbers, left, mid)
	Fork(numbers, mid+1, right)
	Merge(numbers, left, mid, right)
}

func Merge(numbers []int, left int, mid int, right int) {
	helper := make([]int, right-left+1)
	index, leftIndex, rightIndex := 0, left, mid+1
	for leftIndex <= mid && rightIndex <= right {
		// 注: 不支持三元表达式, 快捷运算符只能作为语句而无法作为表达式使用, 没有前置的快捷运算符
		if numbers[leftIndex] < numbers[rightIndex] {
			helper[index] = numbers[leftIndex]
			leftIndex++
		} else {
			helper[index] = numbers[rightIndex]
			rightIndex++
		}
		index++
	}
	if leftIndex <= mid {
		copy(helper[index:], numbers[leftIndex:mid+1])
	}
	if rightIndex <= right {
		copy(helper[index:], numbers[rightIndex:right+1])
	}
	copy(numbers[left:right+1], helper)
}

// QuickSort 快速排序
func QuickSort(numbers []int) {
	Process(numbers, 0, len(numbers)-1)
}

func Process(numbers []int, left int, right int) {
	if left >= right {
		return
	}
	Swap(&numbers[left+rand.Intn(right-left+1)], &numbers[right])
	leftBound, rightBound := Partition(numbers, left, right, numbers[right])
	Process(numbers, left, leftBound)
	Process(numbers, rightBound, right)
}

func Partition(numbers []int, left int, right int, target int) (leftBound int, rightBound int) {
	index, leftIndex, rightIndex := left, left, right
	for index < rightIndex+1 {
		if numbers[index] < target {
			Swap(&numbers[index], &numbers[leftIndex])
			index++
			leftIndex++
		} else if numbers[index] > target {
			Swap(&numbers[index], &numbers[rightIndex])
			rightIndex--
		} else {
			index++
		}
	}
	return leftIndex - 1, rightIndex + 1
}

// HeapSort 堆排序
func HeapSort(numbers []int) {
	heapSize := 1
	// 构建大顶堆
	for heapSize < len(numbers) {
		HeapInsert(numbers, heapSize)
		heapSize++
	}
	heapSize--
	Swap(&numbers[0], &numbers[heapSize])
	// 重新调整为大顶堆
	for heapSize > 0 {
		Heapify(numbers, 0, heapSize)
		heapSize--
		Swap(&numbers[0], &numbers[heapSize])
	}
}

func HeapInsert(numbers []int, index int) {
	// 注: 从小到大排序, 调整成大顶堆
	parentIndex := (index - 1) / 2
	for numbers[parentIndex] < numbers[index] {
		Swap(&numbers[parentIndex], &numbers[index])
		index = parentIndex
		parentIndex = (index - 1) / 2
	}
}

func Heapify(numbers []int, parentIndex int, heapSize int) {
	leftIndex := parentIndex*2 + 1
	for leftIndex < heapSize {
		largest := 0
		rightIndex := leftIndex + 1
		if rightIndex < heapSize && numbers[leftIndex] < numbers[rightIndex] {
			largest = rightIndex
		} else {
			largest = leftIndex
		}
		if numbers[largest] < numbers[parentIndex] {
			largest = parentIndex
		}
		if parentIndex == largest {
			break
		}
		Swap(&numbers[largest], &numbers[parentIndex])
		parentIndex = largest
		leftIndex = parentIndex*2 + 1
	}
}

func Swap(first *int, second *int) {
	var temp = *first
	*first = *second
	*second = temp
}

func RandomArray(length int) (numbers []int) {
	numbers = make([]int, length)
	for index := 0; index < length; index++ {
		numbers[index] = rand.Intn(LIMIT)
	}
	return numbers
}

func main() {
	// 1. 冒泡排序
	numbers := RandomArray(10)
	fmt.Printf("before sort bubble numbers = %v\n", numbers)
	BubbleSort(numbers)
	fmt.Printf("after sort bubble numbers = %v\n", numbers)
	// 2. 插入排序
	numbers = RandomArray(10)
	fmt.Printf("before sort insert numbers = %v\n", numbers)
	InsertSort(numbers)
	fmt.Printf("after sort insert numbers = %v\n", numbers)
	// 3. 选择排序
	numbers = RandomArray(10)
	fmt.Printf("before sort select numbers = %v\n", numbers)
	SelectSort(numbers)
	fmt.Printf("after sort select numbers = %v\n", numbers)
	// 4. 归并排序
	numbers = RandomArray(10)
	fmt.Printf("before sort merge numbers = %v\n", numbers)
	MergeSort(numbers)
	fmt.Printf("after sort merge numbers = %v\n", numbers)
	numbers = RandomArray(10)
	fmt.Printf("before sort quick numbers = %v\n", numbers)
	QuickSort(numbers)
	fmt.Printf("after sort quick numbers = %v\n", numbers)
	numbers = RandomArray(10)
	fmt.Printf("before sort heap numbers = %v\n", numbers)
	HeapSort(numbers)
	fmt.Printf("after sort heap numbers = %v\n", numbers)

}
