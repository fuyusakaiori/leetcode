package chapter05.binary;

/**
 * <h2>寻找正序数组中位数</h2>
 * <h3>补充题: 寻找两个有序数组中的第 K 小的数</h3>
 */
public class FindMedianSortedArray {

    /**
     * <h3>最简单难的做法: 合并数组</h3>
     */
    private static double findMedianSortedArray1(int[] nums1, int[] nums2){
        int index = 0;
        int first = 0, second = 0;
        int[] merge = new int[nums1.length + nums2.length];

        while (first < nums1.length && second < nums2.length){
            merge[index++] = nums1[first] < nums2[second] ? nums1[first++]: nums2[second++];
        }
        while (first < nums1.length){
            merge[index++] = nums1[first++];
        }
        while (second < nums2.length){
            merge[index++] = nums2[second++];
        }
        double right = merge[merge.length / 2], left = merge[(merge.length - 1) / 2];
        return (right + left) / 2;
    }

    /**
     * <h3>优化解法: 不使用额外空间</h3>
     * <h3>注: 本质上我们是不需要去合并数组的, 可以采用双指针的方式去替代</h3>
     */
    private static double findMedianSortedArray2(int[] nums1, int[] nums2){
        // 1. 两个数组的长度是知道的, 所以在逻辑上是可以知道中位数所在的位置的
        int length = nums1.length + nums2.length;
        int first = 0, second = 0;
        // 注: 表示两个中位数, 如果长度为偶数, 那么两个中位数肯定不同, 如果长度为奇数, 那么右侧的才是中位数, 左侧的不是
        double left = -1, right = -1;
        // 2. 既然知道中位数的位置, 那么显然只需要遍历到中位数的位置就可以了
        for (int index = 0;index < length / 2 + 1;index++){
            left = right;
            // 注: 这里将三个循环合并到一起写了
            // 3. 如果第一个指针小于长度, 那么说明还没有遍历结束, 进入后续的判断
            // 4. 如果第二个指针大于长度, 那么说明第二个数组已经遍历结束, 直接遍历第一个数组
            if (first < nums1.length
                        && (second >= nums2.length || nums1[first] < nums2[second])){
                right = nums1[first++];
            }else{
                right = nums2[second++];
            }
        }
        return length % 2 == 0 ? (left + right) / 2: right;
    }

    /**
     * <h3>二分查找: 没做过基本想不出来的解法</h3>
     */
    private static double findMedianSortedArray3(int[] nums1, int[] nums2){
        // 1. 左侧的中位数是第 (len1 + len2 + 1) / 2 小的数
        int leftTh = (nums1.length + nums2.length + 1) / 2;
        int rightTh = (nums1.length + nums2.length + 2) / 2;
        int left = findKSortedArray(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, leftTh);
        int right = findKSortedArray(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, rightTh);
        return (left + right) * 0.5;
    }

    /**
     * <h3>补充题: 寻找两个有序数组中第 K 小的数</h3>
     */
    private static int findKSortedArray(int[] nums1, int s1, int e1, int[] nums2, int s2, int e2, int k){
        // 1. 计算当期需要进行比较的数组的长度
        int len1 = e1 - s1 + 1, len2 = e2 - s2 + 1;
        // 2.1 终止条件: 如果在没有满足 k == 1 的情况下, 数组已经遍历结束, 那么只需要选取另外一个数组的 k 个元素就可以
        if (len1 == 0 || len2 == 0)
            return len1 == 0 ? nums2[s2 + k - 1]: nums1[s1 + k - 1];
        if (k == 1)
            return Math.min(nums1[s1], nums2[s2]);
        // 3. 现在需要选择 K 个数, 那么分别在两个数组中选取 K / 2 个
        int first = s1 + Math.min(len1, k / 2) - 1;
        int second = s2 + Math.min(len2, k / 2) - 1;
        // 注: 这里取最小值的原因是因为剩余的元素可能不够要选取的数量
        if (nums1[first] > nums2[second])
            return findKSortedArray(nums1, s1, e1, nums2, second + 1, e2, k - (second + 1 - s2));
        return findKSortedArray(nums1, first + 1, e1, nums2, s2, e2, k - (first + 1 - s1));
    }



}
