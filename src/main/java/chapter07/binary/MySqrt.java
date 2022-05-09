package chapter07.binary;

/**
 * <h2>X 的平方根</h2>
 */
public class MySqrt {

    /**
     * <h3>思路: </h3>
     * <h3>1. 袖珍计算器: 避免直接使用平方根函数, 采用其他函数组合达到平方根函数的效果</h3>
     * <h3>2. 二分查找: 这种方式会比挨着比较会快很多</h3>
     * <h3>3. 牛顿迭代: 数值分析算法</h3>
     * <h3>扩展: 保留到小数点后 N 位数字</h3>
     */
    private static int mySqrt1(int target){
        long number = 0;
        while(number * number <= target){
            number++;
        }
        return number * number > target ? (int) (number - 1): (int) number;
    }

    private static int mySqrt2(int target){
        long left = 0, right = target, result = 0;
        while (left <= right){
            long number = left + ((right - left) >> 1);
            if (number * number <= target){
                result = number;
                left = number + 1;
            }else{
                right = number - 1;
            }
        }

        return (int) result;
    }

    /**
     * <h3>注: 指数函数只能以 e 为底数</h3>
     */
    private static int mySqrt3(int target){
        // 注: 这里强制转换回舍弃掉小数, 可能在取整的时候出现错误
        int number = (int) Math.exp(0.5 * Math.log(target));
        // 注: 内置函数在计算结果的时候会存在误差, 需要我们手动去修正误差
        return (long) (number + 1) * (number + 1) <= target ? number + 1: number;
    }

    /**
     * <h3>注: 精确到多少位只需要调整每次左右指针移动的距离就行</h3>
     * <h3>注: 感觉只能在输出上确保是多少位小数, 你返回的值肯定不只那么多位小数</h3>
     */
    private static double mySqrt4(int target){
        double left = 0, right = target, result = 0;
        while (left <= right){
            double number = (left + right) / 2;
            if (number * number <= target){
                result = number;
                left = number + 1e-3;
            }else{
                right = number - 1e-3;
            }
        }
        return result;
    }

}
