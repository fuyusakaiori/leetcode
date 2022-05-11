package chapter07;

import java.util.LinkedList;

/**
 * <h2>容器盛水</h2>
 * <h3>1. 盛最多的水</h3>
 * <h3>2. 接雨水</h3>
 */
public class ContainerWithWater {

    /**
     * <h3>思路: 盛最多的水</h3>
     * <h3>1. 动态规划改进形成的贪心算法</h3>
     * <h3>2. 直接采用双指针实现贪心算法</h3>
     * <h3>注: 后者非常不容易直接想到, 而前者和后者的思路基本一致, 但是前者是基于动态规划更容易想到</h3>
     * <h3>注: 这个题不能使用记忆化搜索, 只能采用剪枝的策略, 否则会超出内存</h3>
     */
    private static int maxArea1(int[] height){
        return dfs(height, 0, height.length - 1);
    }

    private static int dfs(int[] height, int left, int right){
        if (left >= right)
            return 0;
        // 1. 计算当前的面积大小
        int cur = Math.min(height[left], height[right]) * (right - left);
        int first = 0, second = 0;
        // 2. 如果左边界比右边界小, 那么之后移动右边界的行为就是没有必要的, 因为面积始终受限于左边界, 算出来的面积一定小于等于此前的面积
        if (height[left] <= height[right])
            first = dfs(height, left + 1, right);
        if (height[left] > height[right])
            second = dfs(height, left, right - 1);
        return Math.max(cur, Math.max(first, second));
    }

    /**
     * <h3>基于动态规划的贪心改成迭代的形式就是双指针</h3>
     */
    private static int maxArea2(int[] height){
        int max = 0;
        int left = 0, right = height.length - 1;
        while (left < right){
            max = Math.max(max, Math.min(height[left], height[right]) * (right - left));
            if (height[left] <= height[right]) {
                left++;
            }else{
                right--;
            }
        }
        return max;
    }

    /**
     * <h3>思路: 接雨水</h3>
     * <h3>1. 单调栈: 这个我自己实现的思路代码不够优雅, 有点面向用例编程的意思</h3>
     * <h3>2. 动态规划: 老实说这个题我没有太感觉到动态规划的思想, 感觉很奇怪</h3>
     * <h3>3. 双指针</h3>
     */
    private static int trap1(int[] height){
        int total = 0;
        LinkedList<Integer> stack = new LinkedList<>();
        for (int index = 0;index < height.length;index++){
            // 注: 上一个边界, 上一个边界对应的高度
            int bound = 0, prev = 0;
            // 注: 确保是单调递减的
            while (!stack.isEmpty() && height[stack.peek()] <= height[index]){
                bound = stack.pop();
                // 注: 计算面积
                total += (index - bound - 1) * (height[bound] - prev);
                // 注: 记录上次高度
                prev = bound;
            }
            // 注: 在入栈之前要再次计算面积, 否则之后可能会漏算
            if (!stack.isEmpty()){
                bound = stack.peek();
                total += (index - bound - 1) * (height[index] - prev);
            }
            stack.push(index);
        }
        return total;
    }

    private static int trap2(int[] height){
        int total = 0;
        for(int index = 0;index < height.length;index++){
            // 1. 寻找左侧最大值
            int leftMax = 0;
            for (int left = 0;left <= index;left++){
                leftMax = Math.max(leftMax, height[left]);
            }
            // 2. 寻找右侧最大值
            int rightMax = 0;
            for (int right = height.length - 1;right >= index;right--){
                rightMax = Math.max(rightMax, height[right]);
            }
            // 3. 计算可以盛的水
            total += Math.min(leftMax, rightMax) - height[index];
        }
        return total;
    }

    /**
     * <h3>动态规划的思路是基于暴力解实现的</h3>
     * <h3>1. 暴力解的思路就是对每个位置求可以接的雨水量, 而不是对区域求, 这个思路是挺奇怪的</h3>
     * <h3>2. 从当前位置开始向左右两次扫描, 找到左侧和右侧的最大值, 也就是边界值</h3>
     * <h3>3. 然后选择最小的那个边界值, 也就是当前这个位置理论上可以接的雨水量</h3>
     * <h3>4. 然后用最小边界值 - 当前值对应的高度, 就可以知道这个位置可以接的雨水量, 也就说高度就是雨水量</h3>
     * <h3>5. 这里是将宽度默认为 1 的, 所以高度就是雨水量</h3>
     * <h3>6. 暴力解的问题在于需要对每个位置都向左右求最大值, 所以效率低, 动态规划就可以记录左右侧的最大值</h3>
     * <h3>注: 这种方式其实有点像回文串的动态规划解法, 先用动态规划计算出结果, 然后再利用动态规划的结果</h3>
     */
    private static int trap3(int[] height){
        // 1. 记录当前值和左侧值中的最大值
        int[] leftMax = new int[height.length];
        leftMax[0] = height[0];
        for (int index = 1;index < leftMax.length;index++){
            // 注: 记录每个位置的左侧最大值
            leftMax[index] = Math.max(height[index], leftMax[index - 1]);
        }
        // 2. 记录当前值和右侧值中的最大值
        int[] rightMax = new int[height.length];
        rightMax[rightMax.length - 1] = height[height.length - 1];
        for (int index = rightMax.length - 2;index >= 0;index--){
            rightMax[index] = Math.max(height[index], rightMax[index + 1]);
        }
        // 3. 选择两个最大值中的最小的那个, 然后和当前值作差, 得到高度, 然后将 1 作为宽度得到结果
        int total = 0;
        for (int index = 0;index < height.length;index++){
            total += Math.min(leftMax[index], rightMax[index]) - height[index];
        }
        return total;
    }

    /**
     * <h3>双指针的思路也是基于暴力解的, 不过是在动态规划思路上改的</h3>
     */
    private static int trap4(int[] height){
        int total = 0;
        int left = 0, right = height.length - 1;
        int leftMax = height[0], rightMax = height[height.length - 1];
        while (left <= right){
            if (leftMax <= rightMax){
                // 注: 这里是不能确定左侧最大值大于自己的, 所以要准备默认值
                total += Math.max(0, leftMax - height[left]);
                leftMax = Math.max(leftMax, height[left++]);
            }else{
                // 注: 这里不能减左侧, 因为中间的值没有遍历, 所以对于左侧来说, 这个右侧的最大值不一定是最大的
                total += Math.max(0, rightMax - height[right]);
                rightMax = Math.max(rightMax, height[right--]);
            }
        }
        return total;
    }

}
