package chapter01;

import java.util.Arrays;

public class ExclusiveOR
{
    public static void main(String[] args)
    {
        System.out.println(findOddCount(new int[]{2, 1, 2, 3, 3, 5, 5, 1, 1}));
        System.out.println(Arrays.toString(findTwoOddCount(new int[]{2, 1, 2, 3, 3, 5, 5, 5, 1, 1})));
    }

    private static void xor(){
        // 异或运算
        int first = 10;
        int second = 20;
        System.out.println(first ^ second);

        // 异或实现交换
        first = first ^ second;
        second = first ^ second;
        first = first ^ second;
        System.out.println(first + "\t" + second);

        // 数组元素
        int[] array = {10, 10};
        array[0] = array[0] ^ array[1];
        array[1] = array[0] ^ array[1];
        array[0] = array[0] ^ array[1];
        System.out.println(Arrays.toString(array));

        // 提取最右侧的 1
        System.out.println((~first + 1) & first);
    }

    /* 问题:
    1、整型数组中, 存在一种数出现的次数为奇数次, 其余数出现的次数为偶数次
    2、整型数组中, 存在两种数出现的次数为奇数次, 其余数出现的次数为偶数次
    如何找出这些数来?
     */
    private static int findOddCount(int[] nums){
        int eor = 0;
        for (int num : nums) {
            eor ^= num;
        }
        return eor;
    }

    private static int[] findTwoOddCount(int[] nums){
        // 1、首先将数组中每个元素都进行异或
        int eor = findOddCount(nums); // O(N)
        /* 2、
        2.1、异或得到的结果显然是两个奇数次的数的异或: eor = a ^ b
        2.2、那接下来只要知道 a、b 中的一个, 就可以将 a、b 和 eor 做异或运算得到另一个数
        2.3、首先明确 eor 肯定不可能为 0, 那么就意味着 eor 的二进制表现形式上必然存在为 1 的位
        2.4、而要使异或结果为 1, 那么 a、b 在该位上必然是不同的, 利用这个依据就可以区分a、b
        2.5、重新异或一遍数组中元素, 但是只异或在该位上是 1 或者 0 的数, 这样最后得到的结果就是 a 或者 b
         */
        // 2、确定异或结果中为 1 的位, 可能存在很多为 1 的位数, 但是挑选最左边的
        // 原码 + 补码 = 最左侧的 1
        int rightOne = eor & (~eor + 1);

        // 3、再次异或数组中的元素, 但是只异或在该位上为 1 的数
        int first = 0;
        for (int num : nums) {
            if ((num & rightOne) == 0){
                first ^= num;
            }
        }

        // 4、将第二次异或得到的结果和第一次异或得到的结果再进行异或
        int second = first ^ eor;

        return new int[]{first, second};
    }
}
