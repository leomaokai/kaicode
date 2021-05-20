import java.util.*;

/**
 * 二叉树遍历
 */
public class _018_BinaryTree {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        TreeNode node7 = new TreeNode(7, null, null);
        TreeNode node6 = new TreeNode(6, null, null);
        TreeNode node5 = new TreeNode(5, node6, node7);
        TreeNode node4 = new TreeNode(4, null, null);
        TreeNode node3 = new TreeNode(3, null, null);
        TreeNode node2 = new TreeNode(2, node4, node5);
        TreeNode node1 = new TreeNode(1, node2, node3);
//        preorderRecursion(node1);
//        inorderRecursion(node1);
//        postorderRecursion(node1);
//        // 层序遍历递归
//        List<Integer> list = new ArrayList<>();
//        levelOrderRecursion(node1,1,list);
//        System.out.println(list);
//        preorderIteration(node1);
//        inorderIteration(node1);
//        postorderIteration(node1);
//        levelOrderIteration(node1);
    }

    // 先序遍历递归
    public static void preorderRecursion(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.println(root.val);
        preorderRecursion(root.left);
        preorderRecursion(root.right);
    }

    // 中序遍历递归
    public static void inorderRecursion(TreeNode root) {
        if (root == null) {
            return;
        }
        inorderRecursion(root.left);
        System.out.println(root.val);
        inorderRecursion(root.right);
    }

    // 后序遍历递归
    public static void postorderRecursion(TreeNode root) {
        if (root == null) {
            return;
        }
        postorderRecursion(root.left);
        postorderRecursion(root.right);
        System.out.println(root.val);
    }

    // 层序遍历递归(使用 list 记录递归位置)
    public static void levelOrderRecursion(TreeNode root, int index, List<Integer> list) {
        if (root == null) {
            return;
        }
        int length = list.size();
        if (length <= index) {
            for (int i = 0; i <= index - length; i++) {
                list.add(length + i, null);
            }
        }
        list.set(index, root.val);
        levelOrderRecursion(root.left, 2 * index, list);
        levelOrderRecursion(root.right, 2 * index + 1, list);
    }

    // 先序遍历迭代
    public static void preorderIteration(TreeNode root) {
        if (root != null) {
            Deque<TreeNode> stack = new LinkedList<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode head = stack.pop();
                System.out.println(head.val);
                // 右边先入栈
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
    }

    // 中序遍历迭代
    public static void inorderIteration(TreeNode root) {
        if (root == null) {
            return;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null) {
            // 节点存在,向左将节点入栈
            // 否则,弹栈,遍历,入栈右节点,重复
            if (root != null) {
                stack.push(root);
                root = root.left;
            } else {
                root = stack.pop();
                System.out.println(root.val);
                root = root.right;
            }
        }
    }

    // 后序遍历迭代
    public static void postorderIteration(TreeNode root) {
        if (root == null) {
            return;
        }
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode prev = null;
        while (!stack.isEmpty() || root != null) {
            // 先找到最左边的节点
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // 判断最左边节点的右边是否有值或是否已经处理
            if (root.right == null || root.right == prev) {
                // 右边已经处理过了,打印当前的值
                System.out.println(root.val);
                // prev 记录是否处理
                prev = root;
                // root 置为 null,从栈中取出栈顶再次判断
                root = null;
            } else {
                // 右边未处理,将当前节点再次入栈
                stack.push(root);
                // 处理当前节点的右边节点
                root = root.right;
            }
        }
    }

    // 层序遍历迭代
    public static void levelOrderIteration(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode head = queue.poll();
            System.out.println(head.val);
            if (head.left != null) {
                queue.offer(head.left);
            }
            if(head.right != null) {
                queue.offer(head.right);
            }
        }
    }

}
