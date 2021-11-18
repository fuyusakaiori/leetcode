package chapter02;

import utils.ListNode;
import utils.RandomUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 排序链表
public class SortList
{
    public static void main(String[] args)
    {
        RandomUtil random = new RandomUtil(10, 50);
        ListNode head = random.randomList();
        List<ListNode> nodes = new ArrayList<>();
        while (head != null){
            nodes.add(head);
            head = head.next;
        }
        nodes.forEach((node)->{
            System.out.print(node.value + "\t");
        });
        Collections.sort(nodes, new Compare());
        Collections.sort(nodes, (o1, o2) -> o1.value - o2.value);
        nodes.forEach((node)->{
            System.out.print(node.value + "\t");
        });

    }

}

class Compare implements Comparator<ListNode>{

    @Override
    public int compare(ListNode o1, ListNode o2)
    {
        return o1.value - o2.value;
    }
}
