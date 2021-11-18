package chapter01.sort;

import utils.RandomUtil;
import utils.TestUtil;

import java.util.Random;

// 总结排序算法

/**
 * ① 注意数组是否可能越界
 * ② 注意 > < = 符号怎么取
 * ③ 注意到底使用哪个索引去访问数组
 */
public class Summary
{
    public static void main(String[] args)
    {
        RandomUtil random = new RandomUtil(10, 20);
        TestUtil.sortTest(random, Summary::bubbleSort, "冒泡排序");
        TestUtil.sortTest(random, Summary::selectionSort, "选择排序");
        TestUtil.sortTest(random, Summary::insertionSort, "插入排序");
        TestUtil.sortTest(random, Summary::mergeSort, "归并排序");
        TestUtil.sortTest(random, Summary::quickSort, "快速排序");
        TestUtil.sortTest(random, Summary::heapSort, "堆排序");
        TestUtil.sortTest(random, Summary::radixSort, "基数排序");
        System.out.println();
        System.out.println(TestUtil.isRight(random, Summary::bubbleSort, "冒泡排序"));
    }

    //===========================基于比较的算法=================================
    // 1. 冒泡排序: 相邻交换
    private static void bubbleSort(int[] numbers)
    {
        // 1. 执行 N 轮
        for (int i = 0; i < numbers.length; i++) {
            // 2. 相邻两个数比较, 每次确定一个数
            // 细节: ① 每次不一定要比到数组末尾, 因为每次确定一个数, 所以可以少比较几次 ② 长度 - 1 避免数组越界
            for (int j = 0; j < numbers.length - i - 1; j++) {
                if (numbers[j] > numbers[j + 1])
                    swap(numbers, j, j + 1);
            }
        }
        // 特点: 丢失了非常多的比较信息, 但是相对稳定
    }

    // 2. 选择排序: 向后看
    private static void selectionSort(int[] numbers)
    {
        // 1. 执行 N 次
        for (int i = 0; i < numbers.length; i++)
        {
            // 2. 每次向后寻找最小值, 每次确定一个数
            // 细节: ① 每次最小值默认为自己
            int minIndex = i;
            for (int j = i + 1; j < numbers.length; j++) {
                // 细节: ② 每次是和最小值比, 不是和自己比
                if (numbers[minIndex] > numbers[j]) {
                    minIndex = j;
                }
            }
            // 3. 交换第一个值和最小值
            swap(numbers, minIndex, i);
        }
    }

    // 3. 插入排序: 向前看
    private static void insertionSort(int[] numbers)
    {
        // 1. 执行 N 次
        for (int i = 0; i < numbers.length; i++) {
            // 2. 每次向前看寻找插入位置, 每次确定一个数
            for (int j = i; j > 0; j--) {
                if (numbers[j] < numbers[j - 1]) {
                    swap(numbers, j - 1, j);
                }
                else {
                    break;
                }
            }
        }
    }

    // 4. 归并排序
    private static void mergeSort(int[] numbers) {
        fork(numbers, 0, numbers.length - 1);
    }

    // 拆分
    private static void fork(int[] numbers, int left, int right) {
        // 数组中仅存在唯一元素时拆分
        if (left == right)
            return;

        int mid = left + ((right - left) >> 1);
        fork(numbers, left, mid);
        fork(numbers, mid + 1, right);
        join(numbers, left, mid, right);
    }

    // 合并
    private static void join(int[] numbers, int left, int mid, int right) {
        // 1. 使用辅助数组
        int[] help = new int[right - left + 1];
        // 2. 比较大小
        int index = 0;
        int leftIndex = left;
        // 细节: ① 右侧数组左边界是从中间 + 1开始的, 不是直接从中间值开始的
        int rightIndex = mid + 1;
        // 细节: ② 必须是 <= 不可以是 !=
        while (leftIndex <= mid && rightIndex <= right) {
            // 简化写法
            help[index++] = numbers[leftIndex] < numbers[rightIndex] ?
                                    numbers[leftIndex++] : numbers[rightIndex++];
        }
        // 3. 将数组剩余内容放入辅助数组
        while (leftIndex <= mid) {
            help[index] = numbers[leftIndex++];
            index++;
        }
        while (rightIndex <= right) {
            help[index] = numbers[rightIndex++];
            index++;
        }
        // 4. 更新原数组
        // 细节: ③ 不要直接使用 left, right 作为右边界, 因为 left, right 是原数组的索引, 可能大于辅助数组长度
        for (int i = 0; i < help.length; i++) {
            numbers[i + left] = help[i];
        }
    }

    // 5. 快速排序
    private static void quickSort(int[] numbers){
        process(numbers, 0, numbers.length - 1);
    }

    // 拆分
    private static void process(int[] numbers, int left, int right){

       if (left < right){
           // 随机抽取元素作为基准值
           int pivotIndex = new Random().nextInt(right - left + 1);
           swap(numbers, left + pivotIndex, right);
           int[] partition = partition(numbers, left, right);
           process(numbers, left, partition[0]);
           process(numbers, partition[1], right);
       }

    }

    // 分区
    private static int[] partition(int[] numbers, int left, int right){
        // 注意: 不是从零开始的！
        int index = left;
        int leftIndex = left;
        int rightIndex = right;
        while (index < rightIndex){
            if (numbers[index] < numbers[right]){
                swap(numbers, index, leftIndex);
                index++;
                leftIndex++;
            }else if (numbers[index] > numbers[right]){
                swap(numbers, index, --rightIndex);
            }else{
                index++;
            }
        }
        swap(numbers, rightIndex, right);
        return new int[]{--leftIndex, ++rightIndex};
    }

    // 6. 堆排序
    private static void heapSort(int[] numbers){
        int heapSize = 0;

        // 构建大顶堆
        while (heapSize < numbers.length){
            heapInsert(numbers, heapSize, "小顶堆");
            heapSize++;
        }

        swap(numbers, 0, --heapSize);
        // 调整大顶堆
        while (heapSize > 0){
            heapify(numbers, 0, heapSize, "小顶堆");
            swap(numbers, 0, --heapSize);
        }

    }

    // 构建大顶堆
    private static void heapInsert(int[] numbers, int index){
        // 细节: ① 不要借助位运算, 会多加一层判断
        int parentIndex = (index - 1) / 2;
        while (numbers[index] > numbers[parentIndex]){
            swap(numbers, index, parentIndex);
            // 继续向上递归判断是否大于父结点
            index = parentIndex;
            // 更新父结点
            parentIndex = (index - 1) / 2;
        }
    }

    // 调整大顶堆
    private static void heapify(int[] numbers, int index, int heapSize){
        int leftIndex = index * 2 + 1;
        while (leftIndex < heapSize){
            // 判断两个子结点谁最大
            int rightIndex = leftIndex + 1;
            int largest = rightIndex < heapSize &&
                                  numbers[rightIndex] > numbers[leftIndex] ? rightIndex : leftIndex;
            // 父结点和子结点比较大小
            largest = numbers[largest] > numbers[index] ? largest : index;
            // 如果最大结点就是父结点, 那么就不用继续调整了
            if (largest == index)
                break;
            // 否则交换后继续调整
            swap(numbers, index, largest);
            index = largest;
            leftIndex = largest * 2 + 1;
        }
    }

    // 构建小顶堆: 优先级队列就是小顶堆
    private static void heapInsert(int[] numbers, int index, Object...args){
        int parentIndex = (index - 1) / 2;
        while (numbers[parentIndex] > numbers[index]){
            swap(numbers, index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }
    // 调整小顶堆
    private static void heapify(int[] numbers, int index, int heapSize, Object...args){
        int leftIndex = index * 2 + 1;
        while (leftIndex < heapSize){
            int rightIndex = leftIndex + 1;
            int minor = rightIndex < heapSize &&
                                  numbers[rightIndex] < numbers[leftIndex] ? rightIndex : leftIndex;
            minor = numbers[minor] < numbers[index] ? minor: index;
            if (minor == index)
                break;
            swap(numbers, minor, index);
            index = minor;
            leftIndex = minor * 2 + 1;
        }
    }

    //===========================基于统计的排序=================================
    // 7. 基数排序
    private static void radixSort(int[] numbers){
        final int radix = 10;
        int bits = maxBits(numbers);
        int[] bucket = new int[numbers.length];
        for (int d = 1; d <= bits; d++) {
            int[] count = new int[radix];
            // 统计词频
            for (int i = 0; i < numbers.length; i++) {
                int digit = getDigit(numbers[i], d);
                count[digit]++;
            }
            // 计算前缀和
            for (int i = 1; i < count.length; i++) {
                count[i] = count[i] + count[i - 1];
            }
            // 出桶
            for (int i = numbers.length - 1;i >= 0;i--){
                int digit = getDigit(numbers[i], d);
                bucket[--count[digit]] = numbers[i];
            }
            // 更新数组
            for (int i = 0;i < numbers.length;i++){
                numbers[i] = bucket[i];
            }
        }
    }

    private static int maxBits(int[] numbers){
        int max = 0;
        for (int i = 0; i < numbers.length; i++) {
            max = Math.max(max, numbers[i]);
        }
        int res = 0;
        while (max != 0){
            max /= 10;
            res++;
        }
        return res;
    }

    private static int getDigit(int number, int digit){
        return (number / (int)Math.pow(10, digit - 1)) % 10;
    }

    // 8. 希尔排序

    //===========================工具方法=================================
    // 交换

    private static void swap(int[] numbers, int first, int second)
    {

        // 不采用位运算的方式进行交换, 避免自己和自己交换
        int temp = numbers[first];
        numbers[first] = numbers[second];
        numbers[second] = temp;
    }

}
