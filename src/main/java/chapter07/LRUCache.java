package chapter07;


import utils.ListNode;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * <h2>设计 LRU 缓存</h2>
 * <p>1. 可以直接使用 LinkedHashMap 实现</p>
 * <p>2. 实际实现通常都是使用双向链表 + 哈希表实现</p>
 * <p>2.1 这里双向链表是手写的, 但是哈希表是直接使用的自带的, 很奇怪, 要手写, 难道不是一起手写?</p>
 * <p>2.2 刚才还考虑过要不要使用 LinkedList 作为双向链表</p>
 */
public class LRUCache<K, V>
{
    /**
     * 双向链表的结点
     * @param <V>
     */
    private static class Node<V>{
        private Node<V> previous;
        private Node<V> next;
        private V value;

        public Node(V value) {
            this.value = value;
        }
    }

    /**
     * <p>双向链表: 维护访问频率, 也就是根据访问频率调度结点</p>
     * @param <V>
     */
    private static class DoubleLinkedList<V>{
        // 双向链表头结点
        private Node<V> head;
        // 双向链表尾结点
        private Node<V> tail;

        /**
         * <p>1. 向双向链表中添加结点</p>
         * <p>2. 并且将新结点设置为尾结点, 也就是最常使用的结点</p>
         * @param node 新添加的结点
         */
        private void add(Node<V> node){
            if (node == null) return;
            if (this.head == null){
                this.head = node;
                this.tail = node;
            }else{
                // 1. 尾结点的下个结点更新
                this.tail.next = node;
                // 2. 尾结点的上个结点更新
                node.previous = this.tail;
                // 3. 更新尾结点
                tail = node;
            }
        }

        /**
         * <p>将最近访问的结点设置为尾结点, 也就是最常访问的结点</p>
         * @param node 新增结点
         */
        private void move(Node<V> node){
            if (node == null)return;
            // 0. 如果是头结点
            if (this.head == node){
                // 1. 头结点显然需要更新
                this.head = node.next;
            }else{
                // 2. 如果不是头结点
                node.previous.next = node.next;
                node.next.previous = node.previous;
            }
            // 3. 将结点移动到尾结点去
            this.tail.next = node;
            node.previous = this.tail;
            this.tail = node;
            node.next = null;
        }

        /**
         * <p>移除最不常用的结点, 也就是头结点</p>
         */
        private Node<V> remove(){
            // 0. 双向链表为空
            if (this.head == null)return null;
            Node<V> node = this.head;
            // 1. 仅有一个结点
            if (this.head == this.tail){
                this.head = null;
                this.tail = null;
                return node;
            }else {
                this.head = node.next;
                node.next = null;
                node.previous = null;
                return node;
            }

        }
    }

    private final int capacity;
    private final HashMap<Node<V>, K> nodeKeyMap;
    private final HashMap<K, Node<V>> keyNodeMap;
    private final DoubleLinkedList<V> doubleLinkedList;

    public LRUCache(int capacity)
    {
        if (capacity < 1) throw new RuntimeException("容量不能够小于 1!");

        this.capacity = capacity;
        this.nodeKeyMap = new HashMap<>();
        this.keyNodeMap = new HashMap<>();
        this.doubleLinkedList = new DoubleLinkedList<>();
    }

    /**
     * 根据 Key 返回 Value 值
     */
    public V get(K key){
        if (this.keyNodeMap.containsKey(key)){
            Node<V> node = this.keyNodeMap.get(key);
            // 更新频率
            this.doubleLinkedList.move(node);
            return node.value;
        }
        return null;
    }

    /**
     * 设置键值对
     */
    public void set(K key, V value){
        // 0. 如果有重复的键值对就覆盖
        if (this.keyNodeMap.containsKey(key)){
            Node<V> node = this.keyNodeMap.get(key);
            node.value = value;
            // 1. 更新频率
            this.doubleLinkedList.move(node);
        }
        // 2. 如果没有重复的键值对就新增
        else{
            Node<V> node = new Node<>(value);
            this.keyNodeMap.put(key, node);
            this.nodeKeyMap.put(node, key);
            this.doubleLinkedList.add(node);
            // 3. 先添加进去之后再看是否需要移除
            if (this.keyNodeMap.size() == this.capacity + 1){
                // 4. 移除
                this.remove();
            }
        }
    }

    private void remove(){
        Node<V> node = this.doubleLinkedList.remove();
        K key = this.nodeKeyMap.get(node);
        this.keyNodeMap.remove(key);
        this.nodeKeyMap.remove(node);

    }

}
