package chapter06;

/**
 * <h2>纸牌博弈问题</h2>
 * <p>问题描述: </p>
 * <p>1. 给定一个整型的数组</p>
 * <p>2. 两个玩家每次都只能从左侧或者右侧拿走一个数字</p>
 * <p>3. 两个玩家都是聪明绝顶的玩家, 请返回最后获胜者的分数</p>
 */
public class StoneGame
{
    public static void main(String[] args)
    {
        int[] card = {1, 2, 100, 4};
        System.out.println(cardGame(card));
    }

    /**
     * <p>大致逻辑</p>
     * <p>1. 玩家 A 拿走最左侧或者最右侧数字, 然后看玩家 B 拿走最左侧还是最右侧的数字</p>
     * <p>2. 玩家 B 能够是基于玩家 A 拿走之后的, 而拿走之后剩余的数字一定是最小的</p>
     * @param card 纸牌数组
     */
    private static int cardGame(int[] card){
        // 两个玩家分别调用拿牌的函数
        return Math.max(first(card, 0, card.length - 1),
                first(card, 0, card.length - 1));
    }

    /**
     * 先手的函数
     */
    private static int first(int[] card, int left, int right){
        // 0. 拿到最后一张, 显然就可以直接返回数字
        if (left == right)
            return card[left];
        // 1. 要么拿左边, 要么拿右边, 两种解法都去尝试, 最后哪种方式最大, 肯定就返回谁
        // 2. 玩家 A 拿完卡片之后, 显然轮到玩家 B 拿, 所以自己要调用后手函数
        return Math.max(
                card[left] + second(card, left + 1, right),
                card[right] + second(card, left, right - 1));
    }

    /**
     * 后手的函数
     */
    private static int second(int[] card, int left, int right){
        if (left == right)
            return card[left];
        // 3. 玩家 A 再次拿牌的时候, 需要等待玩家 B 先拿牌
        // 4. 所以这里实际上是玩家 B 调用的先手函数, 返回给玩家 A 的一定是最小值, 因为玩家 B 要确保自己获胜
        return Math.min(first(card, left + 1, right), first(card, left, right - 1));
    }
}
