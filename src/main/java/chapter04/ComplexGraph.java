package chapter04;

import utils.TreeNode;

import java.util.*;

/**
 * <h2>复杂的图</h2>
 */
public class ComplexGraph {

    public class GraphNode {
        // 结点的值: 如果结点没有值, 或者值不重要, 那么也可以没有这个属性
        private int value;
        // 结点的入度
        private int inDegree;
        // 结点的出度
        private int outDegree;
        // 直接相连的结点
        private List<GraphNode> nextNodes;
        // 相连的边
        private List<GraphEdge> nextEdges;

        public GraphNode(int value) {
            this.value = value;
            this.nextNodes = new LinkedList<>();
            this.nextEdges = new LinkedList<>();
        }
    }

    public class GraphEdge {
        // 边的权重
        private int weight;
        // 边的起点
        private GraphNode start;
        // 边的终点
        private GraphNode end;

        public GraphEdge(int weight, GraphNode start, GraphNode end) {
            this.weight = weight;
            this.start = start;
            this.end = end;
        }
    }

    // 结点的值（可能是整数, 也可能是字符串等等类型）和自己对应的结点的关系
    private Map<Integer, GraphNode> nodes;

    private Set<GraphEdge> edges;

    public ComplexGraph() {
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
    }

    public ComplexGraph(int[][] matrix){
        this();
        createGraphByMatrix(matrix);
    }

    /**
     * <h3>二维矩阵转图</h3>
     * <h3>注: 这里的二维矩阵表示的是: matrix[0] 边的起点, matrix[1] 边的终点, matrix[2] 边的权重</h3>
     */
    private void createGraphByMatrix(int[][] matrix){
        for (int index = 0;index < matrix.length;index++){
            int startValue = matrix[index][0];
            int endValue = matrix[index][1];
            int weight = matrix[index][2];
            // 如果图中不存在起点, 那么就需要创建结点并且添加进去
            if (!this.nodes.containsKey(startValue))
                nodes.put(startValue, new GraphNode(startValue));
            // 如果图中不存在终点, 那么也需要创建相应的结点并且添加进去
            if (!this.nodes.containsKey(endValue))
                nodes.put(endValue, new GraphNode(endValue));
            // 获取结点, 建立边
            GraphNode startNode = nodes.get(startValue);
            GraphNode endNode = nodes.get(endValue);
            GraphEdge edge = new GraphEdge(weight, startNode, endNode);
            // 设置起点中的集合: 类似于邻接表法
            startNode.nextNodes.add(endNode);
            startNode.nextEdges.add(edge);
            // 边集合也要添加
            this.edges.add(edge);
        }
    }


    /**
     * <h3>树转图</h3>
     */
    public ComplexGraph(TreeNode root){
        createGraphByTree(root);
    }

    private void createGraphByTree(TreeNode root){

    }

    /**
     * <h3>广度遍历</h3>
     * <h3>注: 从图的任何一个点开始遍历都是可以的</h3>
     */
    public void bfs(GraphNode node){
        if (node == null) return;
        // 实现遍历的队列
        Queue<GraphNode> queue = new LinkedList<>();
        // 确保不会重复的哈希表
        Set<GraphNode> set = new HashSet<>();

        queue.offer(node);
        set.add(node);

        while (!queue.isEmpty()){
            node = queue.poll();
            // 处理行为
            System.out.println(node.value);
            // 开始处理其余相邻的结点
            for (GraphNode nextNode : node.nextNodes) {
                if (!set.contains(nextNode)){
                    queue.offer(nextNode);
                    set.add(nextNode);
                }
            }
        }
    }

    /**
     * <h3>深度遍历: 迭代</h3>
     */
    public void dfs(GraphNode node){
        if (node == null) return;
        LinkedList<GraphNode> stack = new LinkedList<>();
        Set<GraphNode> set = new HashSet<>();
        stack.push(node);
        set.add(node);
        System.out.println(node.value);
        while (!stack.isEmpty()){
            node = stack.pop();
            for (GraphNode nextNode : node.nextNodes) {
                if (!set.contains(nextNode)){
                    stack.push(node);
                    stack.push(nextNode);
                    set.add(nextNode);
                    // 处理行为
                    System.out.println(nextNode.value);
                    break;
                }
            }
        }
    }

    /**
     * <h3>深度遍历: 递归</h3>
     * <h3>注: 要么使用全局变量记录已经遍历的, 要么将变量设置到参数中传递</h3>
     */
    public void dfs(GraphNode node, Set<GraphNode> set){
        // 处理
        System.out.println(node.value);

        for (GraphNode nextNode : node.nextNodes) {
            if (!set.contains(nextNode)){
                set.add(nextNode);
                dfs(nextNode, set);
            }
        }

    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
                {1, 2, 1}, {1, 3, 1}, {1, 4, 1},
                {2, 3, 1,},{3, 4, 1}, {3, 5, 1},
                {5, 4, 1}};
        ComplexGraph graph = new ComplexGraph(matrix);

        graph.bfs(graph.nodes.get(1));
    }
}
