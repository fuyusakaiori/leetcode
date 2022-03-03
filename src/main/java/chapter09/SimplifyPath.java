package chapter09;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <h2>简化路径</h2>
 * <p>本题核心: 栈结构</p>
 * <p>注: 想不到...</p>
 */
public class SimplifyPath
{
    /**
     * <p>1. .. 表示返回上级路径, 普通文件名表示进入下级路径 => 非常类似于出栈和入栈的操作</p>
     * <p>1.1 这是最为关键的思路, 如果只是普通循环之类的, 那么返回上级目录这个行为是非常难以处理的</p>
     * <p>1.2 通常需要采用 StringBuilder 来删除之前添加的文件名, 使用索引是非常容易越界的</p>
     * <p>2. . 表示当前目录, 这种就什么都不用做就行了</p>
     * <p>3. 文件名之间都是采用分割符隔开的, 所以我们可以从一开始就是用 split 函数分割</p>
     * <p>3.1 由于多个分隔符可能连在一起, 所以分割的结果可能产生 "" 这种结果, 不需要处理就行</p>
     * <p>3.2 为什么需要分割, 因为如果采用遍历的方式很难判断 . 和 .. 的区别, 使用索引判断很容易出现越界的问题</p>
     */
    public static void main(String[] args)
    {
        System.out.println(simplifyPath("/home"));
    }

    /**
     * <p>栈结构简化路径</p>
     * @param path 原始路径
     * @return 简化路径
     */
    private static String simplifyPath(String path){
        // 0. 分割原始路径
        String[] files = path.split("/");
        // 1. 准备栈结构: 因为分割得到的字符串里面有 "", 这个也是不能够入栈的
        List<String> list = new LinkedList<>();
        // 2. 开始遍历
        for (int i = 0; i < files.length; i++) {
            // 2.1 如果遇见 .., 直接出栈
            if (!list.isEmpty() && "..".equals(files[i]))
                list.remove(list.size() - 1);
            // 2.2 如果是 "" 和 ., 就什么也不做, 反过来就是普通文件名了
            else if (!" ..".contains(files[i]))
                list.add(files[i]);
        }
        // 3. 开始连接文件名
        StringBuilder sb = new StringBuilder();
        // 4. 连接文件名的时候又不能够按照出栈的顺序, 因为是反向的, 所以实际上使用集合也是可以做到的
        for (String str : list) {
            sb.append("/").append(str);
        }

        return sb.length() == 0 ? "/" : sb.toString();
    }
}
