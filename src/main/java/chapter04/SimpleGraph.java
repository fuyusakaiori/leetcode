package chapter04;

import java.util.List;
import java.util.Map;

/**
 * <h2>图的建立方式</h2>
 * <h3>1. 邻接表</h3>
 * <h3>2. 邻接矩阵</h3>
 * <h3>3. 简化方式</h3>
 */
public class SimpleGraph {


    // 邻接表法: 每个结点之后都接链表, 链表存储所有相邻的结点
    private Map<GraphNode, List<GraphNode>> graph;

    // 邻接矩阵: 每次访问边的的时候, 都需要找到两个结点对应的索引, 然后用两个索引访问边的值
    private Map<GraphNode, Integer> nodeIndexMap;
    // 记录边的权重值
    private int[][] edges;

    /**
     * <h3>邻接矩阵深度遍历</h3>
     */
    private void dfsGraphMatrix(){

    }

    /**
     * <h3>邻接矩阵层序遍历</h3>
     */
    private void bfsGraphMatrix(){

    }

    /**
     * <h3>邻接链表深度遍历</h3>
     */
    private void dfsGraphLinkedList(){

    }

    /**
     * <h3>邻接链表层序遍历</h3>
     */
    private void bfsGraphLinkedList(){

    }


    private static class GraphNode {
    }
}
