package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class TestUtil
{
    private static final int LOOP = 100;
    // 专门用测试排序的方法
    public static void sortTest(RandomUtil random, Consumer<int[]> consumer, String methodName){
        System.out.println("=======================" + methodName + "=========================");
        int[] array = random.randomArrayWithReplica();
        System.out.println("排序前: " + Arrays.toString(array));
        consumer.accept(array);
        System.out.println("排序后: " + Arrays.toString(array));

    }

    // 测试查找算法是否正确
    public static void findTest(RandomUtil random, BiFunction<Integer, int[], Integer> function, String methodName){
        System.out.println("=======================" + methodName + "=========================");
        int[] array = random.randomArrayNoReplica();
        Arrays.sort(array);
        int target = new Random().nextInt(random.getMaxValue());
        System.out.println("查找元素:\t" + target);
        System.out.println("查找数组:\t" + Arrays.toString(array));
        System.out.println("是否查找成功:\t" + function.apply(target, array));
    }


    public static boolean isRight(RandomUtil random, BiFunction<Integer, int[], Integer> function, String methodName){
        System.out.println("=======================" + methodName + "=========================");
        for (int k = 0; k < LOOP; k++) {
            int[] array = random.randomArrayWithReplica();
            Arrays.sort(array);
            int target = new Random().nextInt(random.getMaxValue());
            int correct = -1;
            // 直接遍历得到结果
            for (int i = 0; i < array.length; i++) {
                if (array[i] >= target){
                    correct = array[i];
                    break;
                }
            }
            System.out.println("查找元素:\t" + target);
            System.out.println("查找数组:\t" + Arrays.toString(array));
            int test = function.apply(target, array);
            System.out.println("查找是否成功:\t" + test);
            if (correct != test)
                return false;
        }
        return true;
    }


    // 验证实现的排序算法是否正确
    public static boolean isRight(RandomUtil random, Consumer<int[]> consumer, String methodName){
        System.out.println("=======================" + methodName + "=========================");
        for (int i = 0; i < LOOP; i++) {
            // 绝对正确的算法使用的数组
            int[] array = random.randomArrayNoReplica();
            // 测试的算法使用的数组
            int[] copyArray = Arrays.copyOf(array, array.length);
            // JDK 提供的工具类对其排序
            Arrays.sort(array);
            // 自己实现的算法对其排序
            consumer.accept(copyArray);
            // 验证两个数组内容是否完全相同
            for (int j = 0; j < array.length; j++) {
                if (array[j] != array[j]){
                    // 如果存在任何一个内容不等, 那么测试的算法肯定出错
                    return false;
                }
            }
        }

        return true;
    }

    // 专门输出链表的方法
    public static void printlnList(ListNode head){
        ListNode temp = head;
        while (temp != null){
            String msg = temp.next == null ? "" : " => ";
            System.out.print(temp.value + msg);
            temp = temp.next;
        }
        System.out.println();
    }


}
