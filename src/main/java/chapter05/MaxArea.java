package chapter05;

import java.util.*;

public class MaxArea
{
    public static void main(String[] args)
    {
        maxArea(new int[]{1,4,3,2,1,2});
        HashSet<Character> characters = new HashSet<>();
        ArrayList<Object> list = new ArrayList<>();
    }

    private static class Node{
        private int index;
        private int height;
        public Node(int index, int height){
            this.index = index;
            this.height = height;
        }
    }

    private static class NodeComparator implements Comparator<Node>
    {
        public int compare(Node first, Node second) {
            return second.height - first.height;
        }
    }

    public static int maxArea(int[] height) {
        int max = 0;
        PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
        for(int i = 0;i < height.length;i++){
            heap.offer(new Node(i, height[i]));
        }
        Node root = heap.poll();
        while(!heap.isEmpty()){
            Node node = heap.poll();
            max = Math.max(Math.min(node.height, root.height) * Math.abs(root.index - node.index), max);
        }
        return max;
    }


}
