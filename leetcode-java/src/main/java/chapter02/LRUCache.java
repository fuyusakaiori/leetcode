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
public class LRUCache<K, V> {

    /**
     * <h3>双向链表结点</h3>
     * <h3>注: 记录 {@code key} 的目的是为了之后移除 {@code cache} 中的 {@code key}</h3>
     * @param <K> key
     * @param <V> value
     */
    private static class Node<K, V>{
        private final K key;
        private V value;
        private Node<K, V> next;
        private Node<K, V> previous;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class DoubleLinkedList<K, V>{
        private Node<K, V> head;
        private Node<K, V> tail;

        /**
         * <h3>向双向链表中添加结点</h3>
         */
        private void add(Node<K, V> node){
            if (node == null) return;
            if (this.head == null){
                this.head = node;
            }else{
                this.tail.next = node;
                node.previous = tail;
            }
            this.tail = node;
        }

        /**
         * <h3>淘汰旧的结点</h3>
         */
        private Node<K, V> remove(){
            if (this.head == null) return null;
            Node<K, V> node = this.head;
            // 1. 如果只有唯一的元素, 那么就直接头尾结点置为空
            if (head == tail){
                this.head = null;
                this.tail = null;
            }else{
                // 2. 如果存在多个元素, 那么只需要把头结点更新就行
                this.head = node.next;
                this.head.previous = null;
                node.next = null;
            }
            return node;
        }

        /**
         * <h3>更新结点</h3>
         */
        private void move(Node<K, V> node){
            if (node == null || node == this.tail) return;
            if (this.head == node){
                this.head = this.head.next;
                this.head.previous = null;
            }else{
                node.previous.next = node.next;
                node.next.previous = node.previous;
            }
            this.tail.next = node;
            node.previous = this.tail;
            node.next = null;
            this.tail = node;
        }
    }


    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final DoubleLinkedList<K, V> linkedList;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.linkedList = new DoubleLinkedList<>();
    }

    public void put(K key, V value){
        if (this.cache.containsKey(key)){
            Node<K, V> node = this.cache.get(key);
            node.value = value;
            linkedList.move(node);
        }else{
            Node<K, V> node = new Node<>(key, value);
            this.cache.put(key, node);
            this.linkedList.add(node);
            if (this.cache.size() > this.capacity)
                this.remove();
        }
    }

    public V get(K key){
        if (this.cache.containsKey(key)){
            Node<K, V> node = this.cache.get(key);
            this.linkedList.move(node);
            return node.value;
        }
        return null;
    }

    public void remove(){
        Node<K, V> remove = this.linkedList.remove();
        if (remove != null)
            this.cache.remove(remove.key);
    }
}
