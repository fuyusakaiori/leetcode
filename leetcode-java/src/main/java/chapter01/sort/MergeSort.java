package chapter01.sort;


public class MergeSort {
    private static void mergeSort(int[] numbers){
        fork(numbers, 0, numbers.length - 1);
    }

    // 拆分数组
    private static void fork(int[] numbers, int left, int right){
        // 持续拆分数组, 直到数组中元素只剩一个就开始合并
        if (left == right)
            return;
        // 位运算的优先级比四则运算低
        int mid = left + ((right - left) >> 1);
        fork(numbers, left, mid);
        fork(numbers, mid + 1, right);

        // 开始合并数组
        join(numbers, left, mid, right);
    }

    // 合并有序数组
    private static void join(int[] numbers, int left, int mid, int right){
        // 辅助存储的数组
        int[] helper = new int[right - left + 1];
        int index = 0;
        int leftIndex = left;
        int rightIndex = mid + 1;
        // 开始比较遍历
        while (leftIndex <= mid && rightIndex <= right){
            helper[index++] = numbers[leftIndex] < numbers[rightIndex] ?
                                       numbers[leftIndex++] : numbers[rightIndex++];
        }
        // 将剩余的数放入辅助数组
        while (leftIndex <= mid){
            helper[index++] = numbers[leftIndex++];
        }
        while (rightIndex <= right){
            helper[index++] = numbers[rightIndex++];
        }
        // 更新数组
        // 注意①: 不要直接将原数组指向辅助数组, Java 中都是值传递, 你是没有办法修改引用的
        // 注意②: 左指针不一定是 0, 千万不要搞错
        for (int i = 0; i < helper.length; i++) {
            numbers[i + left] = helper[i];
        }
    }
}
