package chapter02;

/**
 * <h2>设计哈希集合</h2>
 * <h3>1. 数组实现</h3>
 * <h3>2. 链表实现</h3>
 * <h3>3. BitMap 实现</h3>
 * <h3>注: 位图可以使用更小的空间存储大量的数, 这里假设存储的最大值为 10^6</h3>
 */
public class DesignHashSet
{
    /**
     * <h3>数组实现</h3>
     */
    private static class ArraySet{
        // 注: 必须比这个值大
        private static final int SIZE = 1000001;
        private final boolean[] map = new boolean[SIZE];
        public void add(int value){
            map[value] = true;
        }

        public void remove(int value){
            map[value] = false;
        }

        public boolean contains(int value){
            return map[value];
        }
    }

    /**
     * <h3>链表实现</h3>
     */
    private static class LinkedListSet{
        // 注: 根据需要自行设定
        private static final int SIZE = 1024;
        private static class Node{
            private int value;
            private Node next;
            public Node(int value) {
                this.value = value;
            }
        }
        private final Node[] nodes = new Node[SIZE];

        public void add(int value){
            int index = getIndex(value);
            Node node = nodes[index];
            if (node != null){
                while (node != null){
                    if (node.value == value)
                        return;
                    node = node.next;
                }
            }

            Node newNode = new Node(value);
            newNode.next = nodes[index];
            nodes[index] = newNode;
        }

        public void remove(int value){

        }

        public boolean contains(int value){
            int index = getIndex(value);
            Node node = nodes[index];
            while (node != null){
                if (node.value == value)
                    return true;
                node = node.next;
            }
            return false;
        }

        private int getIndex(int value){
            int hash = Integer.hashCode(value);
            hash ^= (hash >>> 16);
            return hash % SIZE;
        }
    }

    /**
     * <h3>位图实现</h3>
     */
    private static class BitMapSet{
        // 注: 可以比这个值小
        private static final int SIZE = 400000;
        private final int[] map = new int[SIZE];
        public void add(int value){
            int bucketIndex = value / 32;
            int bitIndex = value % 32;
            setValue(bucketIndex, bitIndex, true);
        }

        public void remove(int value){
            int bucketIndex = value / 32;
            int bitIndex = value % 32;
            setValue(bucketIndex, bitIndex, false);
        }

        public boolean contains(int value){
            int bucketIndex = value / 32;
            int bitIndex = value % 32;
            return getValue(bucketIndex, bitIndex);
        }


        private void setValue(int bucketIndex, int bitIndex, boolean flag){
            int number = map[bucketIndex];
            map[bucketIndex] = flag ? number | (1 << bitIndex) : number & ~(1 << bitIndex);
        }

        private boolean getValue(int bucketIndex, int bitIndex){
            return ((map[bucketIndex] >> bitIndex) & 1) == 1;
        }
    }

}
