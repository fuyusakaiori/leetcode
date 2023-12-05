package chapter01;

import java.util.Random;

public class SortAlgorithm {

    private static void bubbleSort(int[] nums){
        for (int first = 0; first < nums.length; first++) {
            for (int second = 0; second < nums.length - first - 1; second++) {
                if (nums[second] > nums[second + 1]){
                    swap(nums, second, second + 1);
                }
            }
        }
    }

    private static void selectSort(int[] nums){
        for (int first = 0; first < nums.length; first++) {
            int minIndex = first;
            for (int second = first + 1; second < nums.length; second++) {
                if (nums[second] < nums[minIndex]){
                    minIndex = second;
                }
            }
            swap(nums, minIndex, first);
        }
    }

    private static void insertSort(int[] nums){
        for (int first = 0; first < nums.length; first++) {
            for (int second = first; second > 0; second--) {
                if (nums[second] < nums[second - 1]){
                    swap(nums, second, second - 1);
                }
            }
        }
    }

    private static void heapSort(int[] nums){
        int heapSize = 1;
        while (heapSize < nums.length){
            heapInsert(nums, heapSize++);
        }
        swap(nums, 0, --heapSize);
        while (heapSize > 0){
            heapify(nums, 0, heapSize);
            swap(nums, 0, --heapSize);
        }
    }

    private static void heapInsert(int[] nums, int index){
        int parent = (index - 1) / 2;
        while (nums[index] > nums[parent]){
            swap(nums, index, parent);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    private static void heapify(int[] nums, int parentIndex, int heapSize){
        int leftIndex = (parentIndex << 1) + 1;

        while (leftIndex < heapSize){
            int largest = leftIndex + 1 < heapSize &&
                                  nums[leftIndex] < nums[leftIndex + 1] ? leftIndex + 1: leftIndex;
            largest = nums[parentIndex] < nums[largest] ? largest: parentIndex;
            if (parentIndex == largest)
                break;
            swap(nums, parentIndex, largest);
            parentIndex = largest;
            leftIndex = (parentIndex << 1) + 1;
        }
    }

    private static void quickSort(int[] numbers){
        process(numbers, 0, numbers.length - 1);
    }

    private static void process(int[] numbers, int left, int right){
        if (left >= right)
            return;
        swap(numbers, left + new Random().nextInt(right - left + 1), right);
        int[] partition = partition(numbers, left, right, numbers[right]);
        process(numbers, left, partition[0]);
        process(numbers, partition[1], right);
    }

    private static int[] partition(int[] numbers, int left, int right, int target){
        int index = left;
        int leftIndex = left;
        int rightIndex = right;
        while (index < rightIndex + 1){
            if (numbers[index] < target){
                swap(numbers, index++, leftIndex++);
            }else if (numbers[index] > target){
                swap(numbers, index, rightIndex--);
            }else{
                index++;
            }
        }
        return new int[]{--leftIndex, ++rightIndex};
    }



    private static void mergeSort(int[] nums){
        fork(nums, 0, nums.length - 1);
    }

    private static void fork(int[] nums, int left, int right){
        if (left >= right){
            return;
        }
        int mid = left + ((right - left) >> 1);
        fork(nums, left, mid);
        fork(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    private static void merge(int[] nums, int left, int mid, int right){
        int index = 0;
        int leftIndex = left, rightIndex = mid + 1;
        int[] helper = new int[right - left + 1];
        while (leftIndex <= mid && rightIndex <= right){
            helper[index++] = nums[leftIndex] < nums[rightIndex]
                                      ? nums[leftIndex++] : nums[rightIndex++];
        }
        while (leftIndex <= mid){
            helper[index++] = nums[leftIndex++];
        }
        while (rightIndex <= right){
            helper[index++] = nums[rightIndex++];
        }
        System.arraycopy(helper, 0, nums, left, helper.length);
    }

    private static void swap(int[] nums, int first, int second){
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }

    private static int maxBits(int[] numbers){
        // 1. 先遍历寻找到的最大值
        int max = 0;
        for (int number : numbers) {
            max = Math.max(number, max);
        }
        // 2. 判断最大值的位数
        int res = 0;
        while (max != 0){
            max /= 10;
            res++;
        }
        return res;
    }

    private static int getDigit(int number, int digit){
        return (number / (int) Math.pow(10, digit - 1)) % 10;
    }

    private static void radixSort(int[] numbers, int left, int right, int bits){
        // 1. 准备词频数组, 辅助数组
        final int radix = 10;
        int[] bucket = new int[right - left + 1];
        // 2. 统计词频: 相当于入桶的操作
        // 有几位就会执行几次入桶出桶的操作
        for (int k = 1; k <= bits; k++) {
            // 注意: 每次词频数组都需要清空再添加, 否则就会越界
            int[] count = new int[radix];
            for (int i = left; i <= right; i++) {
                int digit = getDigit(numbers[i], k);
                count[digit]++;
            }
            // 3. 计算前缀和
            for (int i = 1; i < count.length; i++) {
                count[i] = count[i] + count[i - 1];
            }
            // 4. 从右向左遍历: 相当于出桶
            for (int i = right;i >= left;i--){
                int digit = getDigit(numbers[i], k);
                bucket[count[digit] - 1] = numbers[i];
                count[digit]--;
            }
            // 5. 更新原数组
            for (int i = 0, j = left; i <= right; i++, j++) {
                numbers[i] = bucket[j];
            }
        }
    }

}
