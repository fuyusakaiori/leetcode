package chapter09.dynamic.subsequence;

/**
 * <h3>判断子序列</h3>
 */
public class IsSubsequence {

    /**
     * <h3>双指针</h3>
     */
    public static boolean isSubsequence(String s, String t) {
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

}
