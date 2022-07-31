package chapter04;

import java.util.*;

/**
 * <h2>图的存储方式</h2>
 * <h3>1. 邻接矩阵: 二维数组</h3>
 * <h3>2. 邻接表: 这个存储方式有两种实现方式 (1) 数组实现 (2) 类实现</h3>
 * <h3>3. 模板实现</h3>
 * <h3>注: 左神设计的图论模板其实就是基于类来实现的, 我也会写在这里</h3>
 * <h3>注: 图论模板尽可能要覆盖以下两个元素</h3>
 * <h3>3.1 出度入度</h3>
 * <h3>3.2 边权重, 边的起点和终点</h3>
 */
public class Graph {

    /**
     * <h3>思路: 邻接矩阵实现</h3>
     * <h3>注: 实际使用的时候可以不用封装在类中, 看个人习惯</h3>
     */
    private static class Graph1{
        // 1. 二维数组存储图, 既可以表示有向图也可以表示无向图, 同时可以记录权重
        private final int[][] graph;
        // 2. 一维数组存储所有结点的入度或者出度
        private final int[] indegree;
        private final int[] outdegree;

        public Graph1(int nodes) {
            this.graph = new int[nodes][nodes];
            this.indegree = new int[nodes];
            this.outdegree = new int[nodes];
        }

    }


    private static class Graph2{

    }

    /**
     * <h3>思路: 类实现邻接表</h3>
     * <h3>注: 这种实现方式好处在于容易理解, 三叶提到的链式前向星存图方式不是那么好理解</h3>
     */
    private static class Graph3{
        /**
         * <h3>图的结点</h3>
         * <h3>注: 这里面的所有元素都是根据需要来调整的, 不是所有元素都是必须的</h3>
         */
        private static class GraphNode{
            // 1. 结点的值
            private final int value;
            // 2. 结点的出度和入度
            private int indegree, outdegree;
            // 3. 结点的相邻结点
            private final List<GraphNode> neighbors;
            // 4. 结点的直接关联的边
            private final List<GraphEdge> edges;

            public GraphNode(int value) {
                this.value = value;
                this.neighbors = new LinkedList<>();
                this.edges = new LinkedList<>();
            }
        }

        /**
         * <h3>图的边</h3>
         * <h3>注: 无向图显然是不需要边这个对象的, 有向图这个对象才有意义</h3>
         * <h3>注: 在有些情况下是按照邻接点去遍历, 有些情况是按照相邻边去遍历</h3>
         */
        private static class GraphEdge{
            // 1. 边的权重, 非带权图就没有必要有这个了
            private final int weight;
            // 2. 边的起始点和结束点
            private final int start, end;

            public GraphEdge(int weight, int start, int end) {
                this.weight = weight;
                this.start = start;
                this.end = end;
            }
        }

        // 1. 图的表现形式
        private final Map<Integer, GraphNode> graph;


        public Graph3(int[][] matrix) {
            this.graph = getGraph(matrix);
        }

        /**
         * <h3>转换成图的方法</h3>
         * <h3>注: 只要能够熟练使用模板编写算法, 那么图论题目就变成如何转换图了</h3>
         */
        private Map<Integer, GraphNode> getGraph(int[][] matrix) {
            return null;
        }
    }
}
