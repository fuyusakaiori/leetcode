package chapter01;

public class SearchInsert
{
    /*
    1. 递归不是特别熟练
    2. 还有其他的解法, 内存占用实在太高了
     */
    public static void main(String[] args)
    {
        int[] array = {1, 3, 5, 6};
        System.out.println(searchInsert2(array, 7));
    }

    // 二分查找实现: 递归的版本
    public static int searchInsert1(int[] nums, int target) {
        return search(nums, 0, nums.length - 1, target);
    }

    public static int search(int[] nums, int left, int right, int target){

        int mid = (left + right) / 2;
        if(nums[mid] == target)
            return mid;

        if(left == right){
            if(nums[mid] > target)
                return mid;
            if(nums[mid] < target)
                return mid + 1;
        }

        if(nums[mid] > target && mid - 1 >= left){
            return search(nums, left, mid - 1, target);
        }

        if(nums[mid] < target && mid + 1 <= right){
            return search(nums, mid + 1, right, target);
        }

        return nums[mid] > target ? mid : mid + 1;

    }

    // 二分查找: 循环的版本, 内存占用依然很高
    public static int searchInsert2(int[] nums, int target)
    {
        // 递归占用的内存太大, 尝试使用循环解决
        int left = 0;
        int right = nums.length - 1;
        int mid = (left + right) / 2;
        while (true){
            // 判断元素是否和目标值相等
            if (nums[mid] == target)
                return mid;
            // 如果元素比目标值大, 那么右指针就需要向左侧移动, 前提确保右指针不能够小于左指针
            // 左指针和右指针是可以重叠的, 检查最后一个元素是否和目标值相等
            else if (nums[mid] > target && mid - 1 >= left)
                mid = (left + (right = mid - 1)) / 2;
            // 如果元素比目标值小, 那么左指针就需要向右侧移动, 前提也需要确保左指针不能够小于右指针
            else if (nums[mid] < target && mid + 1 <= right)
                mid = ((left = mid + 1) + right) / 2;
            else
                break;

        }

        return nums[mid] > target ? mid : mid + 1;
    }

    // 二叉排序树: 不考虑
    public static int searchInsert3(int[] nums, int target)
    {
        AVLTree avlTree = new AVLTree();
        for (int i = 0;i < nums.length;i++)
        {
            avlTree.addNode(new Node(i, nums[i]));
        }

        return avlTree.search(target);
    }

}

class AVLTree
{
    private Node root;

    public void addNode(Node node)
    {
        if (root == null)
        {
            root = node;
        }
        else
        {
            root.addNode(node);
        }
    }

    public int search(int target)
    {
        if (root == null)
        {
            return 0;
        }
        return root.search(target);
    }
}

class Node
{
    private int no;
    private int value;
    private Node left;
    private Node right;

    public Node(int no, int value)
    {
        this.no = no;
        this.value = value;
    }

    public int getNo()
    {
        return no;
    }

    public int getValue()
    {
        return value;
    }

    public Node getLeft()
    {
        return left;
    }

    public Node getRight()
    {
        return right;
    }

    // 获取树的高度
    private int getHeight()
    {
        return Math.max(left != null ? left.getHeight() : 0 ,right != null ? right.getHeight() : 0) + 1;
    }

    // 获取左子树的高度
    private int getLeftHeight()
    {
        return left != null ? left.getHeight() : 0;
    }

    private int getRightHeight()
    {
        return right != null ? right.getHeight() : 0;
    }

    // 实现单旋转
    private void leftRotate()
    {
        Node newNode = new Node(this.no, this.value);
        newNode.left = this.left;
        newNode.right = this.right.left;
        this.no = this.right.no;
        this.value = this.right.value;
        this.right = this.right.right;
        this.left = newNode;
    }

    // 添加结点 --> 二叉排序树的方式
    public void addNode(Node node)
    {
        // 如果当前的结点大于新结点的值，那么将新结点添加到左子树去
        if (this.value > node.value)
        {
            if (this.left == null)
            {
                this.left = node;
            }
            else
            {
                // 如果父结点的左子结点不为空，继续递归判断
                this.left.addNode(node);
            }

        }
        else
        {
            if (this.right == null)
            {
                this.right = node;
            }
            else
            {
                this.right.addNode(node);
            }
        }

        // 判断是否需要单旋转
        if (this.getRightHeight() - this.getLeftHeight() > 1)
        {
            leftRotate();
        }
    }

    public int search(int target)
    {
        if (this.value > target)
        {
            if (this.left != null)
            {
                return left.search(target);
            }
        }
        else if (this.value < target)
        {
            if (this.right != null)
            {
                return right.search(target);
            }
        }
        else
        {
            return this.no;
        }

        return target < this.value ? Math.max(this.no - 1, this.no): this.no + 1;
    }

    @Override
    public String toString()
    {
        return "Node{" +
                       "no=" + no +
                       ", value=" + value +
                       '}';
    }
}
