package chapter05.windows;

/**
 * <h3>爱生气的书店老板</h3>
 */
public class MaxSatisfied {


    private static int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
        int clients = 0, window = 0, extra = 0;
        int left = 0, right = 0;
        while(right < customers.length){
            // 1. 获取所有不会被生气影响的顾客
            clients += (1 - grumpy[right]) * customers[right];
            // 2. 获取可能的额外顾客
            window += grumpy[right] * customers[right++];
            // 3. 计算最大值
            extra = Math.max(extra, window);
            // 4. 移动窗口
            if(right - left == minutes)
                window -= grumpy[left] * customers[left++];
        }
        return clients + extra;
    }

}
