package chapter11.dynamic;

/**
 * <h2>子序列问题</h2>
 * <h3>1. 判断子序列</h3>
 * <h3>2. 最长递增子序列</h3>
 * <h3>3. 最长公共子序列</h3>
 * <h3>4. 最长回文子序列</h3>
 * <h3>5. 不同的子序列</h3>
 */
public class Subsequence {

    /**
     * <h3>判断子序列</h3>
     * <h3>核心: 双指针</h3>
     */
    public boolean isSubsequence(String s, String t) {
        int first = 0, second = 0;
        char[] sCharArray = s.toCharArray();
        char[] tCharArray = t.toCharArray();
        while(first < sCharArray.length && second < tCharArray.length){
            if(sCharArray[first] == tCharArray[second])
                first++;
            second++;
        }
        return first == sCharArray.length;
    }

    /**
     * <h3>思路: 最长递增子序列 (LIS) </h3>
     * <h3>核心: ?</h3>
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
     * <h3>1、此前写的暴力递归由于需要记录递增序列的结尾 -> 会使用两个变化参数 -> 动态规划表是二维的</h3>
     * <h3>2、本着尽可能少用参数的原则: 可以在递归外围添加循环从而减少参数的使用, 最终让表成为一维的</h3>
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
     * <h3>思路: 最长公共子序列 (LCS)</h3>
     * <h3>核心: 双指针</h3>
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
     * <h3>思路: 最长回文子序列 (LPS)</h3>
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
        int[][] dp = new int[chars.length][chars.length];
        for (int first = chars.length - 1;first >= 0;first--){
            // 注: 这里只能从左向右填, 依赖关系决定的
            for (int second = first;second <= chars.length - 1;second++){
                if (first == second){
                    dp[first][second] = 1;
                    continue;
                }
                if (chars[first] == chars[second]){
                    dp[first][second] = dp[first + 1][second - 1] + 2;
                }else{
                    dp[first][second] = Math.max(dp[first + 1][second], dp[first][second - 1]);
                }
            }
        }
        return dp[0][chars.length - 1];
    }

    private static int lengthOfLPS3(String str){
        int[][] dp = new int[str.length()][str.length()];
        return dfs(str.toCharArray(), 0, str.length() - 1, dp);
    }

    private static int dfs(char[] chars, int left, int right, int[][] dp){
        // 1. 左指针可能恰好和右指针相等 -> 单个字符肯定是回文字符串
        if (left == right)
            return 1;
        // 2. 左指针大于右指针时 -> 肯定就不是回文字符串
        if (left > right)
            return 0;
        // 3. 如果两个字符相同, 那么肯定要作为回文子序列的一部分
        if (chars[left] == chars[right])
            return dp[left][right] = dfs(chars, left + 1, right - 1, dp);
        // 4. 如果两个字符不等, 那么就尝试删除其中一个
        return dp[left][right] = Math.max(dfs(chars, left + 1, right, dp),
                dfs(chars, left, right + 1, dp));
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

    /**
     * <h3>思路: 不同子序列的数量</h3>
     * <h3>核心: 双指针</h3>
     */
    private static int numDistinct1(String s, String t){
        char[] fArray = s.toCharArray();
        char[] sArray = t.toCharArray();
        int[][] dp = new int[fArray.length + 1][sArray.length + 1];
        for (int index = 0;index < dp.length;index++){
            dp[index][sArray.length] = 1;
        }
        for (int first = fArray.length - 1;first >= 0;first--){
            for (int second = sArray.length - 1;second >= 0;second--){
                if (fArray[first] == sArray[second]){
                    dp[first][second] += dp[first + 1][second + 1];
                }
                dp[first][second] += dp[first + 1][second];
            }
        }
        return dp[0][0];
    }
    public static int numDistinct2(String s, String t){
        char[] fArray = s.toCharArray();
        char[] sArray = t.toCharArray();
        Integer[][] dp = new Integer[fArray.length][s.length()];
        return dfs(0, fArray, 0, sArray, dp);
    }

    public static int dfs(int first, char[] fArray, int second, char[] sArray, Integer[][] dp){
        if(second == sArray.length)
            return 1;
        if(first == fArray.length)
            return 0;
        if(dp[first][second] != null)
            return dp[first][second];
        int result = 0;
        if(fArray[first] == sArray[second])
            result = dfs(first + 1, fArray, second + 1, sArray, dp);
        result += dfs(first + 1, fArray, second, sArray, dp);

        return dp[first][second] = result;
    }


}
