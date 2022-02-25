package chapter02;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>设计 LRU 缓存</h2>
 * <h3>设计思路；</h3>
 * <h3>1. 满足查询时间为 O(1) => 使用哈希表完成</h3>
 * <h3>2. 满足最近最少使用 => 用链表维护结点的插入顺序, 并根据使用频率调整结点的顺序</h3>
 * <h3>3. 头结点作为最不常使用的结点, 尾结点作为最常使用的结点</h3>
 * <h3>4. 每次插入的新结点都是最常使用的, 每次移除结点都是最不常使用的</h3>
 */
public class LRUCache<K, V>
{
    /**
     * <h3>双向链表的结点</h3>
     * @param <K>
     * @param <V>
     */
    private static class Node<K, V>{
        private K key;
        private V value;
        private Node<K, V> previous;
        private Node<K, V> next;
        public Node(V value) {
            this.value = value;
        }
    }

    private static class DoubleLinkedList<K, V>{
        private Node<K, V> head;
        private Node<K, V> tail;

        /**
         * <h3>插入结点</h3>
         */
        private void add(Node<K, V> node){
            if (node == null) return;;
            if (head == null){
                head = node;
            }else{
                tail.next = node;
                node.previous = tail;
            }
            // 更新尾结点
            tail = node;
        }

        /**
         * <h3>删除最不常使用的结点</h3>
         * @return 删除的结点
         */
        private Node<K, V> remove(){
            if (head == null) return null;
            Node<K, V> node = head;
            if (head == tail){
                head = null;
                tail = null;
            }else{
                head = node.next;
                node.next = null;
                node.previous = null;
            }
            return node;
        }

        /**
         * <h3>设置最常用结点</h3>
         */
        private void move(Node<K, V> node){
            if (node == null || node == tail) return;
            if (head == node){
                head = node.next;
                head.previous = null;
            }else{
                node.previous.next = node.next;
                node.next.previous = node.previous;
            }
            // 更新尾结点
            tail.next = node;
            // 记得断开当前结点的
            node.next = null;
            node.previous = tail;
            tail = node;
        }
    }

    // 注: 这里准备两个哈希表的原因: 删除的时候是通过 Value 删除键值对的, 但是哈希表只能够通过 Key 去删除
    // 解决方案一: 建立哈希表保存 Value-Key 的关系
    // 解决方案二: 链表结点中保存 Key 值
    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final DoubleLinkedList<K, V> linkedList;

    public LRUCache(int capacity)
    {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.linkedList = new DoubleLinkedList<>();
    }

    public V get(K key){
        if (cache.containsKey(key)){
            Node<K, V> node = cache.get(key);
            linkedList.move(node);
            return node.value;
        }
        return null;
    }

    public void set(K key, V value){
        if (cache.containsKey(key)){
            Node<K, V> node = cache.get(key);
            node.value = value;
            linkedList.move(node);
        }else{
            Node<K, V> node = new Node<>(value);
            node.key = key;
            cache.put(key, node);
            linkedList.add(node);
            if (cache.size() > capacity)
                remove();
        }
    }

    public void remove(){
        Node<K, V> node = linkedList.remove();
        if (node != null)
            cache.remove(node.key);
    }
}
