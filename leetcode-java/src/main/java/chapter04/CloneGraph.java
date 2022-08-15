package chapter04;

import java.util.*;

/**
 * <h3>克隆图</h3>
 */
public class CloneGraph {

    private static Map<Integer, Node> visited = new HashMap<>();

    /**
     * <h3>深度搜索</h3>
     * @param originNode
     * @return
     */
    private static Node cloneGraph1(Node originNode){
        // 1. 如果结点为空, 那么直接返回
        if(originNode == null)
            return null;
        if (visited.containsKey(originNode.value))
            return visited.get(originNode.value);
        // 2. 克隆结点
        Node copyNode = new Node(originNode.value);
        visited.put(copyNode.value, copyNode);
        // 3. 深度遍历
        for (Node neighbor : originNode.neighbors) {
            copyNode.neighbors.add(cloneGraph1(neighbor));
        }
        return copyNode;
    }

    /**
     * <h3>宽度优先</h3>
     */
    private static Node cloneGraph2(Node originNode){
        // 1. 如果结点为空, 直接返回
        if (originNode == null)
            return null;
        // 2. 初始化变量
        Map<Integer, Node> visited = new HashMap<>();
        Queue<Node> queue = new LinkedList<>(
                Collections.singletonList(originNode));
        visited.put(originNode.value, new Node(originNode.value));
        // 3. 遍历队列
        while (!queue.isEmpty()){
            // 4. 克隆结点: 不要直接使用入参结点, 会导致最后起始节点没有保存
            Node currentNode = queue.poll(), copyNode = visited.get(currentNode.value);
            for (Node neighbor : currentNode.neighbors) {
                // 5. 如果结点没有被访问, 那么就入队并且添加到哈希表
                if (!visited.containsKey(neighbor.value)){
                    queue.offer(neighbor);
                    visited.put(neighbor.value, new Node(neighbor.value));
                }
                // 6. 克隆邻居
                copyNode.neighbors.add(visited.get(neighbor.value));
            }
        }
        return visited.get(originNode.value);
    }



    private static class Node{
        private final int value;
        private final List<Node> neighbors;

        public Node(int value) {
            this.value = value;
            this.neighbors = new LinkedList<>();
        }
    }

}
