package chapter01.sort;



public class InsertionSort {


    private static void insertionSort(int[] numbers){
        int length = numbers.length;
        for (int i = 0; i < length; i++) {
            for (int j = i; j > 0; j--) {
                if (numbers[j] >= numbers[j - 1])
                    break;
                swap(numbers, j, j - 1);
            }
        }
    }

    private static void swap(int[] numbers, int first, int second){
        numbers[first] = numbers[first] ^ numbers[second];
        numbers[second] = numbers[first] ^ numbers[second];
        numbers[first] = numbers[first] ^ numbers[second];
    }
}
