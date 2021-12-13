package chapter07;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * <h2>并查集</h2>
 * <p>1. 内部可以使用哈希表实现</p>
 * <p>2. 内部也可以使用链表去实现</p>
 * <p>注: 并查集的证明非常复杂, 花了 25 年才证明出来</p>
 * <p>时间复杂度: 逼近 O(1), 调用次数非常频繁的时候</p>
 */
public class UnionFindSet<V>
{
    /**
     * 每个集合中的元素
     * @param <V> 元素的值类型
     */
    private static class Element<V>{
        // 0. 仅保存结点的值, 不采用链表的方式记录父结点
        private final V value;
        public Element(V value) {
            this.value = value;
        }
    }

    // 1. 保存所有的元素, 并且可以通过元素值获取元素结点
    private final HashMap<V, Element<V>> elementMap;
    // 2. 保存所有元素的父结点
    private final HashMap<Element<V>, Element<V>> fatherMap;
    // 3. 保存所有集合的大小, 合并集合的时候, 需要将小集合接到大集合上去
    private final HashMap<Element<V>, Integer> sizeMap;

    // 初始化并查集
    public UnionFindSet(List<V> list){
        // 0. 初始化所有的哈希表 => 初始化并查集结构
        this.elementMap = new HashMap<>();
        this.fatherMap = new HashMap<>();
        this.sizeMap = new HashMap<>();
        // 1. 所有的结点在刚开始的时候都是自成集合
        for (int i = 0; i < list.size(); i++) {
            V value = list.get(i);
            Element<V> element = new Element<>(value);
            this.elementMap.put(value, element);
            this.fatherMap.put(element, element);
            this.sizeMap.put(element, 1);
        }
    }

    /**
     * <p>1. 找结点所属的最大的父结点</p>
     * <p>2. 找到之后, 就将这个结点的父结点直接置为最大的父结点, 避免之后再来遍历查询</p>
     * @param element 元素
     * @return 最大父结点
     */
    private Element<V> findFather(Element<V> element){
        // 0. 栈的目的是为了之后将查询结点的父结点设置为最大结点
        LinkedList<Element<V>> stack = new LinkedList<>();
        while (element != this.fatherMap.get(element)){
            // 2.1 父结点压入栈中
            stack.push(element);
            // 2.2 更新父结点
            element = this.fatherMap.get(element);
        }
        // 3. 重新设置父结点
        while (!stack.isEmpty()) this.fatherMap.put(stack.pop(), element);

        return element;
    }

    /**
     * 查询两个集合的根结点是否属于同一个集合
     * @param first 第一个结点
     * @param second 第二个结点
     * @return 是否属于同一个集合
     */
    public boolean contains(V first, V second){
        // 0. 首先两个结点必须是在并查集中的, 否则直接返回 false
        if (this.elementMap.containsKey(first) && this.elementMap.containsKey(second)){
            // 1. 找到两个结点所属集合的根结点, 然后比较两个根结点是否相同
            // 1.1 如果不相同, 那么就证明两个结点不在同一个集合中
            // 1.2 如果相同, 那么就证明两个结点在同一个集合中
            return findFather(this.elementMap.get(first)) == findFather(this.elementMap.get(second));
        }
        return false;
    }

    /**
     * 合并两个集合
     * @param first 第一个集合
     * @param second 第二个集合
     */
    public void union(V first, V second){
        if (this.elementMap.containsKey(first) && this.elementMap.containsKey(second)){
            // 0. 找到两个结点所属集合的根结点
            // 注: 这里父结点是不存在空值的情况, 因为初始的时候结点父结点就是自己
            Element<V> firstFather = this.findFather(this.elementMap.get(first));
            Element<V> secondFather = this.findFather(this.elementMap.get(second));
            // 1. 如果两个根结点不相同, 那么就证明这两个不是一个集合
            if (firstFather != secondFather){
                // 注: 这种写法的好处在于避免使用 if-else 写两套不同的逻辑
                Element<V> big = this.sizeMap.get(firstFather) > this.sizeMap.get(secondFather) ? firstFather: secondFather;
                Element<V> small = firstFather == big ? secondFather: firstFather;
                // 1.1 更改小集合的父结点
                this.fatherMap.put(small, big);
                // 1.2 更改新集合的大小
                this.sizeMap.put(big, this.sizeMap.get(firstFather) + this.sizeMap.get(secondFather));
                // 1.3 移除小集合
                this.sizeMap.remove(small);
            }
        }
    }

    /**
     * <p>并查集处理岛屿问题的思路</p>
     * <p>1. 核心还是需要将巨大的矩阵拆分小块的矩阵, 然后对每个小块的矩阵进行感染算法</p>
     * <p>2. 但是拆分之后很有可能破坏了矩阵或者说图的连通性, 导致最后的岛屿数量高于实际值</p>
     * <p>3. 所以并查集就可以解决这个问题, 我们只需要统计每个小块的边界信息</p>
     * <p>4. 统计的边界信息实际上是这个边界元素是由哪个结点感染的, 说白了就是根结点是谁</p>
     * <p>5. 然后开始比较每个岛屿之间的边界信息, 查看两个边界元素是否属于同一个根结点</p>
     * <p>5.1. 如果不属于同一个结点, 那么就合并, 并且将岛屿数量 - 1</p>
     * <p>5.2. 如果属于同一个根结点, 那么就不合并, 继续遍历</p>
     * <p>总结: 上述就是对岛屿问题采用并行算法的思路, 不过实际上普通的岛屿问题也是可以采用并查集完成的</p>
     */
    public void executeIslandProblem(){

    }
}
