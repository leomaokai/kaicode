import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树的最小深度
 * 给定一个二叉树,找出其最小深度
 * 最小深度是从根节点到最近叶子的最短路径上的节点数量
 * 深度优先,广度优先
 */
public class _014_TreeMinDepth {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        int deep;

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        TreeNode node7 = new TreeNode(7, null, null);
        TreeNode node6 = new TreeNode(6, node7, null);
        TreeNode node5 = new TreeNode(5, null, null);
        TreeNode node4 = new TreeNode(4, null, null);
        TreeNode node3 = new TreeNode(3, node6, null);
        TreeNode node2 = new TreeNode(2, node4, node5);
        TreeNode node1 = new TreeNode(1, node2, node3);
        System.out.println(minDepthDFS(node1));
        System.out.println(minDepthBFS(node1));
    }

    // dfs
    public static int minDepthDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = minDepthDFS(root.left);
        int rightDepth = minDepthDFS(root.right);
        return (leftDepth == 0 || rightDepth == 0) ?
                leftDepth + rightDepth + 1 :
                Math.min(leftDepth + 1, rightDepth + 1);
    }

    // bfs
    // 广度优先得到的第一个叶子就是最小深度
    public static int minDepthBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        root.deep = 1;
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.left == null && node.right == null) {
                return node.deep;
            }
            if (node.left != null) {
                node.left.deep = node.deep + 1;
                queue.offer(node.left);
            }
            if (node.right != null) {
                node.right.deep = node.deep + 1;
                queue.offer(node.right);
            }
        }
        return 0;
    }
}
