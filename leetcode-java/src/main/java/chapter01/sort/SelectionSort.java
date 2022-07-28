package chapter01.sort;

public class SelectionSort {

    private static void selectionSort(int[] numbers){
        // 选中第一个数, 然后向后看是否存在比自己更小的数, 如果存在就记录下来, 遍历结束后交换
        int length = numbers.length;
        for (int i = 0; i < length; i++) {
            int minIndex = i;
            // 每轮排序确定的都是前面的位置
            for (int j = i; j < length; j++) {
                // 每次都需要和之前记录的最小值比, 而不是和选中的元素比
                if (numbers[minIndex] > numbers[j]){
                    minIndex = j;
                }
            }
            if (i != minIndex){
                swap(numbers, i, minIndex);
            }
        }
    }

    private static void swap(int[] numbers, int first, int second){
        numbers[first] = numbers[first] ^ numbers[second];
        numbers[second] = numbers[first] ^ numbers[second];
        numbers[first] = numbers[first] ^ numbers[second];
    }
}
