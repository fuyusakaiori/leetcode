package chapter01.sort;

import utils.RandomUtil;
import utils.TestUtil;

import java.util.Random;

public class Summary {

    //===========================基于比较的算法=================================

    /**
     * <h3>冒泡排序: 相邻交换 从零开始</h3>
     * <h3>基本有序的情况下会达到时间复杂度为 O(N)</h3>
     */
    private static void bubbleSort(int[] numbers) {
        for (int i = 0;i < numbers.length;i++){
            for (int j = 0;j < numbers.length - 1 - i;j++){
                if (numbers[j] > numbers[j + 1])
                    swap(numbers, j, j + 1);
            }
        }
    }

    /**
     * <h3>选择排序: 向后看 ++</h3>
     * <h3>时间复杂度为 O(N)</h3>
     */
    private static void selectionSort(int[] numbers) {
        for (int i = 0;i < numbers.length;i++){
            int minIndex = i;
            for (int j = i; j < numbers.length;j++){
                if (numbers[minIndex] > numbers[j])
                    minIndex = j;
            }
            swap(numbers, minIndex, i);
        }
    }

    /**
     * <h3>插入排序: 向前看 --</h3>
     * <h3>基本有序的情况下会达到时间复杂度为 O(N)</h3>
     */
    private static void insertionSort(int[] numbers) {
        for (int i = 0;i < numbers.length;i++){
            for (int j = i;j > 0;j--){
                if (numbers[j] < numbers[j - 1])
                    swap(numbers, j ,j - 1);
            }
        }
    }

    /**
     * <h3>归并排序</h3>
     * <h3>时间复杂度为 O(N*logN), 空间复杂度为 O(N), 稳定</h3>
     */
    private static void mergeSort(int[] numbers){
        fork(numbers, 0, numbers.length - 1);
    }

    private static void fork(int[] numbers, int left, int right){
        if (left >= right)
            return;
        int mid = left + ((right - left) >> 1);
        fork(numbers, left, mid);
        fork(numbers, mid + 1, right);
        merge(numbers, left, mid, right);
    }

    private static void merge(int[] numbers, int left, int mid, int right){
        int[] helper = new int[right - left + 1];
        int leftIndex = left;
        int rightIndex = mid + 1;
        int index = 0;
        while (leftIndex <= mid && rightIndex <= right){
            helper[index++] = numbers[leftIndex] < numbers[rightIndex] ?
                                      numbers[leftIndex++]: numbers[rightIndex++];
        }
        while (leftIndex <= mid){
            helper[index++] = numbers[leftIndex++];
        }
        while (rightIndex <= right){
            helper[index++] = numbers[rightIndex++];
        }
        for (int i = 0;i < helper.length;i++){
            numbers[i + left] = helper[i];
        }
    }

    /**
     * <h3>快速排序</h3>
     * <h3>期望的时间复杂度为 O(N*logN), 空间复杂度为 O(1), 不稳定</h3>
     */
    private static void quickSort(int[] numbers){
        process(numbers, 0, numbers.length - 1);
    }

    private static void process(int[] numbers, int left, int right){
        if (left >= right)
            return;
        swap(numbers, new Random().nextInt(right - left + 1), right);
        int[] partition = partition(numbers, left, right);
        process(numbers, left, partition[0]);
        process(numbers, partition[1], right);
    }

    private static int[] partition(int[] numbers, int left, int right){
        int index = left;
        int leftIndex = left;
        int rightIndex = right;
        while (index < rightIndex){
            if (numbers[index] < numbers[right]){
                swap(numbers, index++, leftIndex++);
            }else if (numbers[index] > numbers[right]){
                swap(numbers, index, --rightIndex);
            }else{
                index++;
            }
        }
        return new int[]{--leftIndex, ++rightIndex};
    }

    /**
     * <h3>堆排序</h3>
     * <h3>时间复杂度为 O(N*logN), 空间复杂度为O(1), 不稳定</h3>
     */
    private static void heapSort(int[] numbers){
        int heapSize = 1;
        while (heapSize < numbers.length){
            heapInsert(numbers, heapSize++);
        }
        swap(numbers, 0, --heapSize);
        while (heapSize > 0){
            heapify(numbers, 0, heapSize);
            swap(numbers, 0, --heapSize);
        }
    }

    private static void heapInsert(int[] numbers, int index){
        int parent = (index - 1) / 2;
        while (numbers[index] > numbers[parent]){
            swap(numbers, index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    private static void heapify(int[] numbers, int parent, int heapSize){
        int leftIndex = (parent << 1) + 1;

        while (leftIndex < heapSize){
            int largest = leftIndex + 1 < heapSize &&
                                  numbers[leftIndex] < numbers[leftIndex + 1] ? leftIndex + 1: leftIndex;
            largest = numbers[parent] < numbers[largest] ? largest: parent;
            if (parent == largest)
                break;
            swap(numbers, parent, largest);
            parent = largest;
            leftIndex = (parent << 1) + 1;
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

    /**
     * <h3>基数排序</h3>
     */
    private static void radixSort(int[] numbers){
        final int radix = 10;
        int[] bucket = new int[numbers.length];
        for (int k = 1;k <= maxBits(numbers);k++){
            int[] counts = new int[radix];
            for (int i = 0;i < numbers.length;i++){
                counts[getDigit(numbers[i], k)]++;
            }
            for (int i = 1;i < numbers.length;i++){
                counts[i] = counts[i] + counts[i - 1];
            }
            for (int i = numbers.length - 1;i >= 0;i--){
                int digit = getDigit(numbers[i], k);
                bucket[counts[digit] - 1] = numbers[i];
                counts[digit]--;
            }
            for (int i = 0;i < numbers.length;i++){
                numbers[i] = bucket[i];
            }
        }
    }

    private static int maxBits(int[] numbers){
        int max = 0;
        for (int i = 0;i < numbers.length;i++){
            if (numbers[i] > max)
                max = numbers[i];
        }
        int bits = 0;
        while (max != 0){
            max /= 10;
            bits++;
        }
        return bits;
    }

    private static int getDigit(int number, int digit){
        return (number / (int)Math.pow(10, digit - 1)) % 10;
    }

    private static void swap(int[] numbers, int first, int second) {
        int temp = numbers[first];
        numbers[first] = numbers[second];
        numbers[second] = temp;
    }

}
