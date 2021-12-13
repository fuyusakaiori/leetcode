package chapter07;

import java.util.HashMap;
import java.util.Random;

/**
 * <h2>设计 RandomPool 结构</h2>
 * <p>1. 将键值对加入到该结构中, 确保不重复</p>
 * <p>2. 将键值对从该结构中删除</p>
 * <p>3. 从该结构中获得简直对, 并且是等概率的</p>
 * <p>注: 这里就存在一个问题, 如果删除了一个简直对之后, 随机函数就不是等概率的了</p>
 */
public class RandomPool<K>
{
    // 维护哈希表中的键值对数量, 方便随机返回时使用
    private int size;
    // 核心: 借助两个哈希表实现
    private  HashMap<K, Integer> keyIndexMap;
    private HashMap<Integer, K> indexKeyMap;

    /**
     * 向该结构中插入键值
     * @param key 插入的键值
     */
    private void insert(K key){
        // 0. 不允许添加重复键值
        if (!keyIndexMap.containsKey(key)){
            keyIndexMap.put(key, this.size);
            indexKeyMap.put(this.size, key);
            this.size++;
        }
    }

    /**
     * <p>从该结构中删除键值</p>
     * <p>为了解决随机返回出现的问题, 我们需要让最后一个键值去填充被删除的键值的位置</p>
     * @param key 需要删除的键值
     */
    private void delete(K key){
        // 0. 键值必须是存在的, 不存在的就没有必要删除了
        if (keyIndexMap.containsKey(key)){
            K last = indexKeyMap.get(this.size - 1);
            int delete = keyIndexMap.get(key);
            indexKeyMap.put(delete, last);
            keyIndexMap.put(last, delete);
            indexKeyMap.remove(this.size - 1);
            keyIndexMap.remove(key);
            this.size--;
        }
    }

    /**
     * <p>随机返回一个键值</p>
     * <p>1. 这里存在一个问题, 如果进行了删除操作, 那么长度就会减少</p>
     * <p>2. 这样最后一个键值就没有办法返回了</p>
     * @return 键值
     */
    private K getRandom(){
        // 0. 如果哈希表中没有元素就返回空
        // 1. 如果存在元素就随机返回
        return this.size == 0 ? null: indexKeyMap.get(new Random().nextInt(this.size));
    }
}
