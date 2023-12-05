package main

import (
	"fmt"
	"math/rand"
	"time"
)

const (
	NumsLength = 20
	RandLimit  = 100
)

// 冒泡排序
func bubbleSort(nums []int) {
	for first := 0; first < len(nums); first++ {
		for second := first + 1; second < len(nums); second++ {
			if nums[first] > nums[second] {
				swap(nums, first, second)
			}
		}
	}
}

// 选择排序
func selectSort(nums []int) {
	for first := 0; first < len(nums); first++ {
		minIndex := first
		// 向后遍历找到最小的元素
		for second := first + 1; second < len(nums); second++ {
			if nums[minIndex] > nums[second] {
				minIndex = second
			}
		}
		swap(nums, minIndex, first)
	}
}

// 插入排序
func insertSort(nums []int) {
	for first := 0; first < len(nums); first++ {
		// 向前遍历不断交换元素
		for second := first; second > 0; second-- {
			if nums[second] < nums[second-1] {
				swap(nums, second-1, second)
			}
		}
	}
}

// 堆排序
func heapSort(nums []int)  {
	// 构建大顶堆
	index := 0;
	for index < len(nums) {
		heapInsert(nums, index)
		index++
	}
	// 根节点换到最后
	for index > 0 {
		index--
		swap(nums, 0, index)
		heapify(nums, index)
	}
}

// 注: 大顶堆
func heapInsert(nums []int, index int)  {
	parent := (index - 1) / 2
	for nums[index] > nums[parent] {
		swap(nums, index, parent)
		index = parent
		parent = (index - 1) / 2
	}
}

func heapify(nums []int, limit int)  {
	parentIndex, leftIndex := 0, 1
	for leftIndex < limit {
		largest := leftIndex
		if leftIndex + 1 < limit && nums[leftIndex] < nums[leftIndex + 1] {
			largest = leftIndex + 1
		}
		// 大顶堆父节点一定大于子节点: 如果相等, 那么就没有必要再调整了
		if nums[largest] <= nums[parentIndex] {
			break
		}
		swap(nums, parentIndex, largest)
		parentIndex = largest
		leftIndex = (parentIndex << 1) + 1
	}
}

// 归并排序
func mergeSort(nums []int) {
	fork(nums, 0, len(nums) - 1)
}

func fork(nums []int, left int, right int) {
	if left >= right {
		return
	}
	mid := left + ((right - left) >> 1)
	fork(nums, left, mid)
	fork(nums, mid + 1, right)
	merge(nums, left, mid, right)
}

func merge(nums []int, left int, mid int, right int) {
	index := 0
	leftIndex, rightIndex := left, mid + 1
	helper := make([]int, right - left + 1)
	for leftIndex <= mid && rightIndex <= right {
		if nums[leftIndex] <= nums[rightIndex] {
			helper[index] = nums[leftIndex]
			index++
			leftIndex++
		}
		if nums[leftIndex] > nums[rightIndex] {
			helper[index] = nums[rightIndex]
			index++
			rightIndex++
		}
	}
	if leftIndex <= mid {
		copy(helper[index:], nums[leftIndex : mid + 1])
	}
	if rightIndex <= right {
		copy(helper[index:], nums[rightIndex : right + 1])
	}
	copy(nums[left : right + 1], helper)
}

// 快速排序
func quickSort(nums []int)  {
	divide(nums, 0, len(nums) - 1)
}

func divide(nums []int, left int, right int)  {
	if left >= right {
		return
	}
	// 随机选择元素作为目标元素
	target := left + rand.Intn(right - left + 1)
	// 交换目标元素到末尾
	swap(nums, target, right)
	// 分区
	leftBound, rightBound := partition(nums, left, right)
	// 继续
	divide(nums, left, leftBound)
	divide(nums, rightBound, right)
}

func partition(nums []int, left int, right int) (int, int) {
	target := nums[right]
	index, leftIndex, rightIndex := left, left, right
	for index <= rightIndex {
		if nums[index] < target {
			swap(nums, index, leftIndex)
			leftIndex++
			index++
		} else if nums[index] > target {
			swap(nums, index, rightIndex)
			rightIndex--
		} else {
			index++
		}
	}
	return leftIndex - 1, rightIndex + 1
}

// 交换数组元素
func swap(nums []int, first int, second int) {
	temp := nums[first]
	nums[first] = nums[second]
	nums[second] = temp
}

// 生成随机元素数组
func generateRandNums() []int {
	nums := make([]int, NumsLength)
	generator := rand.New(rand.NewSource(time.Now().UnixNano()))
	// 0. 随机生成数组
	for index := 0; index < len(nums); index++ {
		nums[index] = generator.Intn(RandLimit)
	}
	return nums
}

func main() {
	// 1. 冒泡排序
	bubbleSortNums := generateRandNums()
	bubbleSort(bubbleSortNums)
	fmt.Printf("bubble sort nums = %v\n", bubbleSortNums)
	// 2. 选择排序: 向后看
	selectSortNums := generateRandNums()
	selectSort(selectSortNums)
	fmt.Printf("select sort nums = %v\n", selectSortNums)
	// 3. 插入排序: 向前看
	insertSortNums := generateRandNums()
	insertSort(insertSortNums)
	fmt.Printf("insert sort nums = %v\n", insertSortNums)
	// 4. 堆排序
	heapSortNums := generateRandNums()
	heapSort(heapSortNums)
	fmt.Printf("heap sort nums = %v\n", heapSortNums)
	// 5. 归并排序
	mergeSortNums := generateRandNums()
	mergeSort(mergeSortNums)
	fmt.Printf("merge sort nums = %v\n", mergeSortNums)
	// 6. 快速排序
	quickSortNums := generateRandNums()
	quickSort(quickSortNums)
	fmt.Printf("quick sort nums = %v\n", quickSortNums)
	// 7. 桶排序
}
