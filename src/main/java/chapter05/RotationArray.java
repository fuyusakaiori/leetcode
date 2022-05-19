package chapter05;

/**
 * <h2>轮转数组</h2>
 */
public class RotationArray {

    /**
     * <h3>额外空间: 数组</h3>
     * <h3>注: 这基本上是最朴素的想法了</h3>
     */
    private static void rotate1(int[] numbers, int k){
        int[] move = new int[numbers.length];
        for (int index = 0;index < numbers.length;index++){
            move[(index + k) % numbers.length] = numbers[index];
        }
        System.arraycopy(move, 0, numbers, 0, numbers.length);
    }

    /**
     * <h3>环状数组</h3>
     * <h3>注: 暂时还没有看明白</h3>
     */
    private static void rotate2(int[] numbers, int k){

    }

    /**
     * <h3>数组翻转</h3>
     * <h3>非常巧妙的思路, 看似很简单, 实际真的不好想</h3>
     */
    private static void rotate3(int[] numbers, int k){
        // 注: 记得取模, 因为移动的位数可能超过数组的长度
        k %= numbers.length;
        // 1. 先将整个数组翻转
        reverse(numbers, 0, numbers.length - 1);
        // 2. 翻转后半部分
        reverse(numbers, k, numbers.length - 1);
        // 3. 翻转前半部分
        reverse(numbers, 0, k - 1);
    }

    private static void reverse(int[] numbers, int first, int last){
        while (first < last){
            int temp = numbers[first];
            numbers[first] = numbers[last];
            numbers[last] = temp;
            first++;last--;
        }
    }

}
