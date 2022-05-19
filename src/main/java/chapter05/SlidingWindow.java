package chapter05;


import java.util.*;

/**
 * <h2>滑动窗口相关题目</h2>
 * <h3>1. 无重复字符最长子串</h3>
 * <h3>2. 最小覆盖子串</h3>
 * <h3>3. 字符串的排列</h3>
 * <h3>4. 找到字符串中的所有异位词</h3>
 * <h3>5. 滑动窗口最大值</h3>
 * <h3>6. 至少有 K 个重复字符的最长子串</h3>
 * <h3>7. 存在重复元素 II</h3>
 * <h3>8. 存在重复元素 III</h3>
 * <h3>9. 长度最小子数组</h3>
 * <h3>10. 最大连续 1 的个数 III</h3>
 * <h3>注: 捏麻麻的, 怎么滑动窗口的题也这么难, 人傻了</h3>
 */
public class SlidingWindow {

    /**
     * <h3>思路: 无重复字符最长子串</h3>
     * <h3>1. 哈希集合 + 滑动窗口思想: 两次遍历</h3>
     * <h3>2. 哈希表 + 滑动窗口: 一次遍历 (实现)</h3>
     * <h3>3. 数组 + 滑动窗口: 效率高</h3>
     * <h3>4. 动态规划</h3>
     * <h3>注: 重新按照套路写一版</h3>
     */
    private static int lengthOfLongestSubstring(String str){
        // 0. 准备变量
        int maxLength = 0;
        int left = 0, right = 0;
        // 1. 准备滑动窗口
        Set<Character> window = new HashSet<>();
        // 2. 开始移动窗口
        while (right < str.length()){
            // 3. 现在窗口中的内容是题意的: 让右指针移动, 直到不满足条件也就是出现重复位置
            if (!window.contains(str.charAt(right))){
                // 注: 执行完数据更新后立刻移动右指针, 就代表左闭右开
                window.add(str.charAt(right++));
            }else{
                // 4. 移动左指针之前先结算, 不过这里可以移动到外面去, 避免一些用例不通过
                // 5. 现在窗口不满足题意了: 左指针移动, 直到让窗口内没有重复元素
                window.remove(str.charAt(left++));
            }
            maxLength = Math.max(maxLength, right - left);
        }
        // 注: 这么写的好处在于最后不用额外多判断一次
        return maxLength;
    }

    /**
     * <h3>思路: 最小覆盖子串</h3>
     * <h3>1. 首先需要将目标子串中的所有字符存放在哈希表中, 称为 needs, 用于之后的滑动窗口判断使用</h3>
     * <h3>2. 然后再创建一个哈希表 window, 用于记录窗口中的字符情况</h3>
     * <h3>3. 然后右指针开始移动, 如果发现当前的字符存在于 needs 中, 那么就将这个字符添加到 window 中</h3>
     * <h3>4. 如果这个字符已经存在 window 中了, 那么就将次数 +1</h3>
     * <h3>5. 然后再判断此时这个字符在 window 中的次数是否和 needs 相等</h3>
     * <h3>6. 如果相等, 那么就认为窗口中的这个字符的次数已经满足条件了, 有效数字 +1</h3>
     * <h3>7. 然后不断移动右指针, 直到有效数字的个数等于 needs 的长度, 就认为窗口中的字符串满足条件了</h3>
     * <h3>8. 然后移动左指针, 不断移除字符, 直到不满足条件为止</h3>
     */
    private static String minWindow(String firstStr, String secondStr){
        // 1. 如果第一个子串长度小于第二个子串, 那么显然就不需要继续判断了
        if (firstStr.length() < secondStr.length()) return "";
        // 2. 准备变量
        char[] fArray = firstStr.toCharArray();
        char[] sArray = secondStr.toCharArray();
        // 注: 1.这里不使用哈希集合, 因为字符串里可能存在重复的元素, 到时候结算的条件就会出现问题
        // 注: 2.比如第二个字符串是 "AABC", 那么就会认为滑动窗口中只有 3 个元素也是满足条件的, 这肯定是不对的
        Map<Character, Integer> needs = new HashMap<>();
        for (int index = 0;index < secondStr.length();index++){
            needs.put(sArray[index], needs.getOrDefault(sArray[index], 0) + 1);
        }
        int count = 0;
        int left = 0, right = 0;
        String minStr = "";
        Map<Character, Integer> window = new HashMap<>();
        while (right < sArray.length){
            // 3. 不断移动右指针直到满足条件: 如果碰见符合要求的字符就添加进 window, 然后判断是否需要增加有效数字
            char ch = fArray[right++];
            if (needs.containsKey(ch)){
                window.put(ch, window.getOrDefault(ch, 0) + 1);
                if (window.get(ch).intValue() == needs.get(ch).intValue()) count++;
            }
            // 4. 如果发现窗口中满足条件, 那么就考虑左移指针让窗口不满足, 有效数字和 needs 相等的时候就是满足了, 也就是认为这个时候就找到覆盖字符串了
            while (count == needs.size()){
                if (minStr.equals("") || minStr.length() > right - left)
                    minStr = firstStr.substring(left, right);
                char removed = fArray[left++];
                if (needs.containsKey(removed)){
                    if (window.get(removed).intValue() == needs.get(removed).intValue()) count--;
                    window.put(removed, window.get(removed) - 1);
                }
            }
        }
        return minStr;
    }

    /**
     * <h3>思路: 字符串的排列</h3>
     * <h3>注: 基本和最小覆盖子串类似</h3>
     */
    private static boolean checkInclusion(String firstStr, String secondStr){
        char[] fArray = firstStr.toCharArray();
        char[] sArray = secondStr.toCharArray();
        Map<Character, Integer> needs = new HashMap<>();
        for (int index = 0;index < fArray.length;index++){
            needs.put(sArray[index], needs.getOrDefault(sArray[index], 0) + 1);
        }
        int valid = 0;
        int left = 0, right = 0;
        Map<Character, Integer> window = new HashMap<>();
        while (right < fArray.length){
            char rch = fArray[right++];
            if (needs.containsKey(rch)){
                window.put(rch, window.getOrDefault(rch, 0) + 1);
                if (window.get(rch).intValue() == needs.get(rch).intValue())
                    valid++;
            }
            if (right - left == sArray.length){
                if (valid == needs.size())
                    return true;
                char lch = fArray[left++];
                if (needs.containsKey(lch)){
                    if (window.get(lch).intValue() == needs.get(lch).intValue())
                        valid--;
                    window.put(lch, window.get(lch) - 1);
                }
            }
        }
        return false;
    }

    /**
     * <h3>思路: 找到字符串中所有异位词</h3>
     */
    private static List<Integer> findAnagrams(String firstStr, String secondStr){
        char[] fArray = firstStr.toCharArray();
        char[] sArray = secondStr.toCharArray();
        Map<Character, Integer> needs = new HashMap<>();
        for (int index = 0;index < fArray.length;index++){
            needs.put(sArray[index], needs.getOrDefault(sArray[index], 0) + 1);
        }
        int valid = 0;
        int left = 0, right = 0;
        Map<Character, Integer> window = new HashMap<>();
        List<Integer> anagrams = new LinkedList<>();
        while (right < fArray.length){
            char rch = fArray[right++];
            if (needs.containsKey(rch)){
                window.put(rch, window.getOrDefault(rch, 0) + 1);
                if (window.get(rch).intValue() == needs.get(rch).intValue())
                    valid++;
            }
            if (right - left == sArray.length){
                if (valid == needs.size())
                    anagrams.add(left);
                char lch = fArray[left++];
                if (needs.containsKey(lch)){
                    if (window.get(lch).intValue() == needs.get(lch).intValue())
                        valid--;
                    window.put(lch, window.get(lch) - 1);
                }
            }
        }
        return anagrams;
    }

    /**
     * <h3>思路: 滑动窗口最大值</h3>
     * <h3>1. 滑动窗口 + 优先权队列</h3>
     * <h3>1.1 每次同时右移左指针和右指针, 将新进入的元素和当前的最大值比较, 谁大谁作为当前窗口的最大值</h3>
     * <h3>1.2 如果在比较最大值的时候发现当前最值已经不在窗口中了, 那么就将最大值淘汰, 看下一个是否在范围内</h3>
     * <h3>2. 单调队列 / 双端队列</h3>
     */
    private static int[] maxSlidingWindow1(int[] nums, int k){
        int left = 0, right = 0;
        int[] maxArray = new int[nums.length - k + 1];
        PriorityQueue<int[]> window = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        while (right < nums.length){
            if (right - left == k - 1){
                while (window.peek()[0] < left)
                    window.poll();
                maxArray[left++] = Math.max(window.peek()[1], nums[right]);
            }
            window.offer(new int[]{right, nums[right]});
            right++;
        }
        return maxArray;
    }

    private static int[] maxSlidingWindow2(int[] nums, int k){
        // 1。 滑动窗口必然需要准备的变量
        int left = 0, right = 0;
        int[] maxArray = new int[nums.length - k + 1];
        // 2. 大多数的滑动窗口都需要相应的数据结构
        Deque<Integer> window = new ArrayDeque<>();
        // 3. 开始移动滑动窗口: 这里的滑动窗口大小是固定的, 之前有些题窗口大小不是固定的
        while (right < nums.length){
            // 4. 确保队列是单调递减的, 相当于单调栈
            while (!window.isEmpty() && nums[window.peekLast()] < nums[right])
                window.pollLast();
            // 5. 确保单调性之后再将当前元素入队
            window.offerFirst(right++);
            // 6. 确保当前的最大值还在窗口中
            if (window.peekFirst() < left)
                window.pollFirst();
            // 7. 窗口大小满足之后就可以开始移动窗口, 填充最大值了
            if (right >= k)
                maxArray[left] = nums[window.pollFirst()];
        }
        return maxArray;
    }


    /**
     * <h3>思路: 至少有 K 个重复字符的最长子串</h3>
     * <h3>1. 分段</h3>
     * <h3>2. 枚举 + 滑动窗口</h3>
     * <h3>2.1 这道题和常规的滑动窗口非常不一样, 没有办法直接利用左右指针的移动来确定窗口</h3>
     * <h3>2.2 这里会进行比较详细的解释, 以便让自己理解为什么这里没有办法直接使用滑动窗口</h3>
     * <h3>2.2.1 普通的滑动窗口, 在右指针移动到某个元素时, 发现不满足某种条件, 那么就让左指针移动</h3>
     * <h3>2.2.2 这样就让左指针的行为是确定的, 感觉就是具有所谓的单调性, 也就是行为固定</h3>
     * <h3>2.2.3 但是这个题目中不行, 你会发现每次右指针向右移动, 会有两种情况</h3>
     * <h3>2.2.4 如果新的字符是窗口中有的, 那么肯定会放入窗口中</h3>
     * <h3>2.2.5 但如果是窗口中没有的呢?到底是移动左指针舍弃前面有的, 还是奢望之后还会有这个字符呢?</h3>
     * <h3>2.2.6 如果之后再也没有这个字符那么显然就是结算缩小窗口, 如果后面有那么就不能够舍弃</h3>
     * <h3>2.2.7 所以这里的左指针的行为就是不确定的了, 也就是没有所谓的单调性了, 没有办法确定</h3>
     * <h3>2.2 所以最后可以通过字符种类的数量来使得左指针增加单调性</h3>
     * <h3>2.3 每次都限制窗口中可以出现的字符种类的数量</h3>
     * <h3>2.3.1 那么只要新来的字符没有使得窗口中字符种类超过限制, 那么就可以进入</h3>
     * <h3>2.3.2 如果使得字符种类数超过限制, 那么就需要结算, 移动左指针去减少字符种类数</h3>
     * <h3>2.4 发现没有? 自己增加了某个条件, 使得滑动窗口的指针行为确定了, 所以这个题才能够做</h3>
     */
    private static int longestSubstring(String str, int k){
        return 0;
    }

    /**
     * <h3>1. 首先需要注意到有些字符出现的次数是小于目标次数的, 所以最长子串肯定不会包含这些字符</h3>
     * <h3>2. 所以我们可以想到根据这些不可能被选中的字符进行划分</h3>
     * <h3>3. 也就是找到字符串中的一个次数不满足条件的字符, 然后划分为左右两段, 然后递归进行</h3>
     * <h3>4. 直到没有不满足条件的字符出现, 那么这个字符串就是符合要求的</h3>
     * <h3>注: 这个思路和我最早的思路非常相似, 但是没有想到用递归, 而是用的滑动窗口</h3>
     * <h3>注: 从整体思路来看, 这个题</h3>
     */
    private static int dfs(String str, int k, int left, int right){
        // 1. 统计该段中各个字符出现的次数
        int[] counts = getCounts(str, left, right);
        // 2. 找到第一个不满足条件的字符, 那么就是分界点
        char splitPoint = getSplitPoint(counts, k);
        // 3. 如果分界点为 0, 代表当前没有不符和条件的, 那么直接返回即可
        if (splitPoint == 0) return right - left + 1;
        // 4. 如果依然存在不满足条件的, 那么需要继续递归, 找到左右边界
        int index = left;
        while (index <= right && str.charAt(index) != splitPoint)
            index++;
        return Math.max(dfs(str, k, left, index - 1), dfs(str, k, index + 1, right));
    }

    /**
     * <h3>统计每段中各个字符出现的次数</h3>
     * <h3>注: 不用哈希表的原因, 是因为数组索引比较快, 空间占用也小, 对于字符很合适</h3>
     */
    private static int[] getCounts(String str, int left, int right){
        int[] counts = new int[26];
        for (int index = left;index <= right;index++){
            counts[str.charAt(index) - 'a']++;
        }
        return counts;
    }

    /**
     * <h3>找到分界点</h3>
     * <h3>注: 这里没有办法直接返回字符在字符串中的位置, 需要循环去找</h3>
     */
    private static char getSplitPoint(int[] counts, int k){
        for (int index = 0; index < counts.length; index++) {
            if (counts[index] > 0 && counts[index] < k)
                return (char) (index + 'a');
        }
        return 0;
    }


    /**
     * <h3>思路: 存在重复元素 II</h3>
     * <h3>1. 固定大小的滑动窗口都应该有初始化的过程</h3>
     * <h3>2. 只需要判断新进入的元素是否在窗口中有重复就行</h3>
     * <h3>注: 滑动窗口大小也是固定的</h3>
     */
    private static boolean containsNearbyDuplicate(int[] nums, int k){
        // 1. 滑动窗口必然准备的变量
        int left = 0, right = 0;
        // 2. 准备数据结构
        Set<Integer> window = new HashSet<>();
        // 3. 开始移动滑动窗口
        while (right < nums.length){
            // 4. 判断是否重复
            if (window.contains(nums[right]))
                return true;
            // 5. 如果不重复就直接放进去
            window.add(nums[right++]);
            // 6. 窗口达到要求的大小时, 开始移动左指针
            if (right >= k + 1)
                window.remove(nums[left++]);
        }
        return false;
    }

    /**
     * <h3>思路: 存在重复元素 III</h3>
     * <h3>1. 第一个参数还是固定了窗口的大小, 这个显然是没有问题的</h3>
     * <h3>2. 但是现在要求的不是找窗口中的重复元素, 而是要求找到 abs(nums[i] - nums[j]) < t 的一对元素</h3>
     * <h3>3. 那么我们应该想到只要窗口中存在最逼近新加入的元素, 那么肯定可以满足上面的条件</h3>
     * <h3>4. 这里的最逼近指的就是 nums[i] - t < nums[i] < nums[i] + t</h3>
     * <h3>5. 只要窗口中存在某个元素在这个范围内, 那么肯定和新加入的元素相减得到差一定满足条件</h3>
     * <h3>6. 那么怎样才能够在窗口找到这个最逼近元素呢? 使用 TreeSet 就可以</h3>
     * <h3>注: 这题实际上还有个思路, 是桶排序, 但是太麻烦了, 就不写了</h3>
     */
    private static boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t){
        int left = 0, right = 0;
        NavigableSet<Integer> window = new TreeSet<>();
        while (right < nums.length){
            // 注: 找到小于等于和大于等于新加入的元素
            Integer floor = window.floor(nums[right]);
            Integer ceil = window.ceiling(nums[right]);
            if (floor != null && nums[right] - floor <= t) return true;
            if (ceil != null && ceil - nums[right] <= t) return true;
            window.add(nums[right++]);
            if (right >= k + 1)
                window.remove(nums[left++]);
        }
        return false;
    }

    /**
     * <h3>思路: 长度最小子数组</h3>
     * <h3>1. 滑动窗口</h3>
     * <h3>1.1 如果目前窗口中的和小于目标值, 就不断向右移动指针去增加和</h3>
     * <h3>1.2 如果目前窗口中的和大于目标值, 就右移左指针去减少和</h3>
     * <h3>1.3 这里的终止条件因为我的写法原因会有些许不同的, 不能够在右指针到达终点就截止</h3>
     * <h3>1.4 还需要考虑此时的和是否可以继续减小, 如果可以那么继续右移左指针, 如果不可以直接退出</h3>
     * <h3>2. 前缀和 + 二分查找</h3>
     * <h3>注: 昨晚那个题感觉也是这样的</h3>
     */
    private static int minSubArrayLen(int[] nums, int target){
        int minLength = Integer.MAX_VALUE;
        int total = 0;
        int left = -1, right = 0;
        while(left < right){
            if(total < target){
                total += nums[right++];
            }else{
                minLength = Math.min(right - left - 1, minLength);
                total -= nums[++left];
            }
            if(right == nums.length && total < target) break;
        }
        return minLength == Integer.MAX_VALUE ? 0: minLength;
    }

}
