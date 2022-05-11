package chapter01.sort;



public class HeapSort {

    // 利用大顶堆实现的递增排序
    private static void heapSort(int[] numbers){
        if (numbers == null || numbers.length < 2)
            return;
        // 开始向数组中"添加"元素, 构建大顶堆
        int heapSize = 1;
        while (heapSize != numbers.length){
            heapInsert(numbers, heapSize);
            heapSize++;
        }
        swap(numbers, 0, --heapSize);
        while (heapSize > 0){
            // 重新调整为大顶堆
            heapify(numbers, 0, heapSize);
            // 交换根结点和最后一个结点
            swap(numbers, 0, --heapSize);
        }
    }

    // 构建大顶堆或者小顶堆
    private static void heapInsert(int[] numbers, int index){

        int parentIndex = (index - 1) >> 1;
        while (parentIndex >= 0 && numbers[index] > numbers[parentIndex]){
            // 如果新加入的结点大于父结点, 那就需要交换
            swap(numbers, index, parentIndex);
            // 继续向上递归判断是否大于祖父结点
            index = parentIndex;
            parentIndex = (index - 1) >> 1;
        }
    }

    // 重新调整为大顶堆或者小顶堆
    private static void heapify(int[] numbers, int index, int heapSize){
        // 左子结点
        int leftIndex = (index << 1) + 1;
        // 判断子结点是否存在, 或者说是否越界
        while (leftIndex < heapSize){
            // 确定子结点中最大的元素, 前提是右子结点存在
            int largest = leftIndex + 1 < heapSize &&
                                  numbers[leftIndex] < numbers[leftIndex + 1] ? leftIndex + 1: leftIndex;
            // 缺点父结点和子结点的关系
            largest = numbers[largest] > numbers[index] ? largest: index;
            // 如果最大结点就是父结点, 那么就不用再向下递归判断了
            if (largest == index)
                break;
            swap(numbers, largest, index);
            // index 指针也必须移动, 因为在比较父结点和子结点大小时是需要使用的
            index = largest;
            leftIndex = (largest << 1) + 1;
        }
    }

    private static void swap(int[] numbers, int first, int second){
        int temp = numbers[first];
        numbers[first] = numbers[second];
        numbers[second] = temp;
    }
}
