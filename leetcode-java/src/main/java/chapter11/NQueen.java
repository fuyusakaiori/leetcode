package chapter11;

/**
 * <h2>N 皇后问题</h2>
 * <p>问题描述: </p>
 * <p>1. 存在 NxN 的棋盘, 现在将 N 个皇后全部放在棋盘中</p>
 * <p>2. 要求棋盘中所有皇后都是不可以放在同一行、同一列、同一斜线的</p>
 * <p>3. 问总共有多少种解法</p>
 */
public class NQueen
{

    /**
     * <p>仔细观察, 其实可以看出来</p>
     * <p>递归实际上在对二维数组做行遍历</p>
     * <p>循环实际上在对二维数组做列遍历</p>
     */
    public static void main(String[] args)
    {
        System.out.println(queen(4, ""));
    }

    /**
     * <p>暴力递归求解思路</p>
     * <p>1. 确定第一个皇后的位置之后, 第二行每个位置都去尝试</p>
     * <p>2. 只要不违反规则, 那么这个皇后就可以放在第二行的这个位置</p>
     * <p>3. 然后依次去尝试后面的行数</p>
     * <p>4. 如果到某行的时候, 发现不满足规则, 那就直接回溯, 如果到最后一行, 那就证明这种摆放方式是可行的</p>
     * @param queens 皇后的数量
     * @return 摆放的种数
     */
    private static int queen(int queens, String method){
        if ("violence".equals(method)){
            if (queens == 0)
                return 0;
            // 0. 用于记录已经摆放好的皇后, 所在的列; 索引对应的就是皇后所在的行
            int[] record = new int[queens];
            return process(0, record, queens);
        }
        int limit = queens == 32 ? -1 : (1 << queens) - 1;
        return limit(limit, 0, 0,0);
    }

    /**
     * <p>1. 确定当前皇后所在的位置</p>
     * <p>2. 验证确定的位置是否违反规则, 如果没有违反规则, 那么就继续递归调用</p>
     * <p>3. 如果违反规则, 那么就继续尝试之后的位置</p>
     * @param start 最初开始尝试的位置
     * @param record 此前已经摆放好的皇后所在的列信息
     * @param last 最后尝试的位置
     * @return 摆放的种数
     */
    private static int process(int start, int[] record, int last){
        // 5. 尝试到最后一行的时候依然没有违规, 那就证明这种方法就是合适的
        if (start == last)
            return 1;
        int res = 0;
        // 1. 循环尝试该行的每一个位置
        for (int index = 0;index < last;index++){
            // 2. 验证是否违反规则
            if (isValid(start, record, index)){
                // 3. 不违反规则, 就需要更新数组, 也就是将该皇后所在的列信息放入数组
                record[start] = index;
                // 4. 然后递归调用, 看之后的皇后放在什么位置
                res += process(start + 1 , record, last);
            }
        }
        return res;
    }

    /**
     * 验证是否违反规则
     * @param start 上一个皇后的位置
     * @param current 当前尝试的位置
     */
    private static boolean isValid(int start, int[] record, int current){
        // 7. 循环遍历 record 中保存的此前的皇后的信息
        for (int i = 0;i < start;i++){
            // 8. 判断是否违规: 不能够在同一列, 不能够在同一对角线
            if (record[i] == current || Math.abs(record[i] - current) == Math.abs(start - i))
                return false;
        }
        return true;
    }


    /**
     * <p>这个思路非常巧妙, 使用了位运算来加速递归的过程</p>
     * <p>+-----------------------------+</p>
     * <p>column&nbsp;&nbsp;00010000              </p>
     * <p>left&nbsp;&nbsp;&nbsp;00100000              </p>
     * <p>right&nbsp;&nbsp;&nbsp;00001000              </p>
     * <p>合并&nbsp;&nbsp;&nbsp;&nbsp;00111000              </p>
     * <p>+-----------------------------+</p>
     * @param limit 总的限制
     * @param columnLimit 列限制
     * @param leftLimit 左斜线限制
     * @param rightLimit 右斜线限制
     * @return 摆放种数
     */
    private static int limit(int limit, int columnLimit, int leftLimit, int rightLimit){
        // 0. 如果列限制和总的限制相同时, 就证明已经遍历到棋盘的最后一行了, 因为所有限制位置都已经摆放满了
        if (columnLimit == limit)
            return 1;
        int res = 0;
        // 1. 列限制、左斜线限制、右斜线限制做或运算就可以得到此前皇后摆放的位置对之后的限制了
        // 2. 总限制再和当前的限制的反做与运算, 得到之后的皇后可以摆放的位置
        int current = limit & (~(columnLimit | leftLimit | rightLimit));
        // 3. 然后从最左边开始尝试, 只要是 1, 就认为和之前的摆放是不冲突的
        int mostRight = 0;
        // 终止条件: 只要二进制数中还存在 1, 那就证明还可以继续尝试
        while (current != 0){
            // 方法: 取二进制数最右侧的 1 的方式
            mostRight = (~current + 1) & current;
            // 4. 这次尝试就结束, 将最右侧的 1 置为 0
            current = current - mostRight;
            // 5. 确定当前皇后位置之后, 就可以继续递归调用, 确定之后的皇后的位置
            res += limit(limit,
                    columnLimit | mostRight,
                    (leftLimit | mostRight) << 1,
                    (rightLimit | mostRight) >>> 1);
        }

        return res;
    }


    /**
     * <p>采用动态规划实现</p>
     */
    private static int dynamicPlanning(){

        return 0;
    }


}
