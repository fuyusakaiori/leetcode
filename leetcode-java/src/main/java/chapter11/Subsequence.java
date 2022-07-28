package chapter11;

import java.util.Arrays;

/**
 * <h2>子序列问题</h2>
 * <h3>1. 最长递增子序列</h3>
 * <h3>2. 最长公共子序列</h3>
 * <h3>3. 最长回文子序列</h3>
 */
public class Subsequence {

    /**
     * <h3>思路: 最长递增子序列</h3>
     */
    private static int lengthOfLIS1(int[] nums){
        int maxLength = 0;
        int[] dp = new int[nums.length + 1];
        for (int index = nums.length - 1;index >= 0;index--){
            dp[index] = 1;
            for (int start = index + 1;start < nums.length;start++){
                if (nums[index] < nums[start]){
                    dp[index] = Math.max(dp[index], dp[start] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[index]);
        }
        return maxLength;
    }

    /**
     * <h3>1、之前写的暴力解存在点问题, 因为在递归的时候使用了两个参数, 所以导致表结构也是二维的</h3>
     * <h3>2、本着尽可能少用参数的原则, 可以在递归的外围添加循环, 从而减少参数的使用, 最终让表成为一维的</h3>
     */
    private static int lengthOfLIS2(int[] nums){
        int maxLength = 0;
        int[] dp = new int[nums.length];
        for (int index = 0;index < nums.length;index++){
            maxLength = Math.max(maxLength, dfs(nums, index, dp));
        }
        return maxLength;
    }

    private static int dfs(int[] nums, int index, int[] dp){
        if (index == nums.length)
            return 0;
        if (dp[index] != 0)
            return dp[index];
        dp[index] = 1;
        for (int start = index + 1;start < nums.length;start++){
            // 2. 如果后面的值比当前值大, 那么就以下个值为起点继续判断
            if (nums[start] > nums[index]){
                // 3. 这里不要判断之后立刻跳出循环, 因为很有可能选择这个数会导致子序列变小
                dp[index] = Math.max(dp[index], dfs(nums, start, dp)) + 1;
            }
        }
        return dp[index];
    }

    /**
     * <h3>1、这个解法就是利用尝试的思想来做的, 但是会使用两个参数, 这就导致表结构是二维的</h3>
     * <h3>2、我暂时没有想明白这种做法为什么会非常慢, 难道是因为二维表？并且这个做法不好改成动态规划</h3>
     * <h3>3、如果笔试记不得优化的解法, 那么这种解法一定要会, 但是面试记不住的话就噶了</h3>
     */
    private static int lengthOfLIS3(int[] nums){
        int[][] dp = new int[nums.length + 1][nums.length];
        for (int[] memory : dp) {
            Arrays.fill(memory, -1);
        }
        return dfs(nums, -1, 0, dp);
    }

    private static int dfs(int[] nums, int previous, int index, int[][] dp){
        if (index == nums.length)
            return 0;
        if (dp[previous + 1][index] != -1)
            return dp[previous + 1][index];

        int skip = dfs(nums, previous, index + 1, dp);
        int get = 0;
        if (previous == -1 || nums[previous] < nums[index])
            get = dfs(nums, index, index + 1, dp) + 1;
        return dp[previous + 1][index] = Math.max(skip, get);
    }

    /**
     * <h3>思路: 最长公共子序列</h3>
     */
    private static int lengthOfLCS1(String text1, String text2){
        char[] fArray = text1.toCharArray(), sArray = text2.toCharArray();
        int[][] dp = new int[fArray.length + 1][sArray.length + 1];
        for (int first = fArray.length - 1;first >= 0;first--){
            for (int second = sArray.length - 1;second >= 0;second--){
                if (fArray[first] == sArray[second]){
                    dp[first][second] = dp[first + 1][second + 1] + 1;
                }else{
                    dp[first][second] = Math.max(dp[first][second + 1], dp[first + 1][second]);
                }
            }
        }
        return dp[0][0];
    }

    private static int lengthOfLCS2(String text1, String text2){
        Integer[][] dp = new Integer[text1.length()][text2.length()];
        return dfs(text1.toCharArray(), 0, text2.toCharArray(), 0, dp);
    }

    private static int dfs(char[] fArray, int first, char[] sArray, int second, Integer[][] dp){
        if(first == fArray.length || second == sArray.length)
            return 0;
        if(dp[first][second] != null)
            return dp[first][second];
        if(fArray[first] == sArray[second])
            return dp[first][second] = dfs(fArray, first + 1, sArray, second + 1, dp) + 1;
        return dp[first][second] = Math.max(dfs(fArray, first + 1, sArray, second, dp),
                dfs(fArray, first , sArray, second + 1, dp));
    }

    /**
     * <h3>思路: 最长回文子序列</h3>
     * <h3>1. 尝试思路</h3>
     * <h3>2. 镜像反转 + LCS</h3>
     * <h3>注: 在这里看到一个叫做区间动态规划的概念, 不太明白是什么</h3>
     * <h3>注: 这个题最巧的思路就是镜像反转 + LCS, 也可以做出来, 但是不知道怎么证明</h3>
     */
    private static int lengthOfLPS1(String str){
        // 1. 反转字符串
        String reverse = new StringBuilder(str).reverse().toString();
        // 2. 寻找公共子序列
        return lengthOfLCS1(reverse, str);
    }

    private static int lengthOfLPS2(String str){
        char[] chars = str.toCharArray();
        int[][] dp = new int[chars.length + 1][chars.length + 1];

        for (int left = chars.length - 1;left >= 0;left--){
            for (int right = chars.length - 1;right >= left;right--){
                if (left == right){
                    dp[left][right] = 1;
                    continue;
                }
                int noLeft = dp[left + 1][right];
                int noRight = dp[left][right + 1];
                int noLeftAndRight = dp[left + 1][right + 1];
                noLeftAndRight += chars[left] == chars[right] ? 2: 0;
                dp[left][right] = Math.max(noLeft, Math.max(noRight, noLeftAndRight));
            }
        }
        return dp[0][0];
    }

    private static int lengthOfLPS3(String str){
        Integer[][] dp = new Integer[str.length()][str.length()];
        return dfs(str.toCharArray(), 0, str.length() - 1, dp);
    }

    private static int dfs(char[] chars, int left, int right, Integer[][] dp){
        // 注: 只剩一个字符肯定就是回文字符了
        if (left == right)
            return 1;
        if (left > right)
            return 0;
        if (dp[left][right] != null)
            return dp[left][right];
        // 1. 回文子序列不从左字符开始
        int noLeft = dfs(chars, left + 1, right, dp);
        // 2. 回文子序列不从右字符开始
        int noRight = dfs(chars, left, right - 1, dp);
        // 3. 回文子序列不从两侧开始
        int noLeftAndRight = dfs(chars, left + 1, right - 1, dp);
        // 4. 如果两侧的字符本来就相等, 那么需要加上去
        noLeftAndRight += chars[left] == chars[right] ? 2: 0;

        return dp[left][right] = Math.max(noLeft, Math.max(noRight, noLeftAndRight));
    }

    /**
     * <h3>思路: 递增子序列的数量</h3>
     * <h3>注: 这个题是在微众银行的笔试题中见到的, 虽然不难但是还是挺有意义的题</h3>
     */
    private static int countOfLIS(int[] nums){
        int count = 0;
        int[] dp = new int[nums.length + 1];
        for (int index = nums.length - 1;index >= 0;index--){
            dp[index] = 1;
            for (int start = index + 1;start < nums.length;start++){
                if (nums[index] < nums[start]){
                    dp[index] += dp[start];
                }
            }
            count += dp[index];
        }
        return count;
    }


}
