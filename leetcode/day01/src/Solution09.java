import java.util.*;

public class Solution09 {

    /**
     * 49. 字母异位词分组
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, ArrayList<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] ch = s.toCharArray();
            // 排序字符
            Arrays.sort(ch);
            // 将排好序的字符作为key
            String key = String.valueOf(ch);
            if (!map.containsKey(key))
                map.put(key, new ArrayList<>());
            map.get(key).add(s);
        }
        return new ArrayList(map.values());
    }

    /**
     * 55. 跳跃游戏
     */
    public boolean canJump(int[] nums) {
        int len = nums.length;
        int max = 0;
        for (int i = 0; i < len; ++i) {
            if (max >= i) {
                // 可以到达的最远距离
                max = Math.max(max, i + nums[i]);
                if (max >= len - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 56. 合并区间
     */
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][2];
        }
        // 排序,按数组中的首个元素排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            public int compare(int[] interval1, int[] interval2) {
                return interval1[0] - interval2[0];
            }
        });
        List<int[]> merged = new ArrayList<>();
        for (int i = 0; i < intervals.length; ++i) {
            int start = intervals[i][0];
            int end = intervals[i][1];
            if (merged.size() == 0 || merged.get(merged.size() - 1)[1] < start) {
                merged.add(new int[]{start, end});
            } else {
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], end);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }

    /**
     * 62. 不同路径
     */
    public int uniquePaths(int m, int n) {
        int[][] bp = new int[m][n];
        for (int i = 0; i < m; ++i) {
            bp[i][0] = 1;
        }
        for (int i = 0; i < n; ++i) {
            bp[0][i] = 1;
        }

        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                bp[i][j] = bp[i - 1][j] + bp[i][j - 1];
            }
        }
        return bp[m - 1][n - 1];
    }

    /**
     * 64. 最小路径和
     */
    public int minPathSum(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        for (int i = 1; i < row; i++) {
            grid[i][0] += grid[i - 1][0];
        }
        for (int i = 1; i < col; i++) {
            grid[0][i] += grid[0][i - 1];
        }
        for (int i = 1; i < row; ++i) {
            for (int j = 1; j < col; ++j) {
                int min = Math.min(grid[i - 1][j], grid[i][j - 1]);
                grid[i][j] += min;
            }
        }
        return grid[row - 1][col - 1];
    }

    /**
     * 75. 颜色分类
     */
    public void sortColors(int[] nums) {
        // 三路排序
        // int len=nums.length;
        // int zeroIndex=0;
        // int twoIndex=len-1;
        // int i=0;
        // int temp;
        // while(i<=twoIndex){
        //     if(nums[i]==2){
        //         temp=nums[i];
        //         nums[i]=nums[twoIndex];
        //         nums[twoIndex]=temp;
        //         twoIndex--;
        //     }else if(nums[i]==0){
        //         temp=nums[i];
        //         nums[i]=nums[zeroIndex];
        //         nums[zeroIndex]=temp;
        //         i++;
        //         zeroIndex++;
        //     }else{
        //         i++;
        //     }
        // }

        int len = nums.length;
        int[] color = new int[3];
        for (int i = 0; i < len; ++i) {
            color[nums[i]]++;
        }
        for (int i = 0; i < len; ++i) {
            nums[i] = color[0]-- > 0 ? 0 : (color[1]-- > 0) ? 1 : 2;
        }
    }

    /**
     * 78. 子集
     */
    private List<List<Integer>> ret = new ArrayList<>();

    public List<List<Integer>> subsets(int[] nums) {
        int len = nums.length;
        List<Integer> tempList = new ArrayList<>();
        backtrack(nums, 0, len, tempList);
        return ret;
    }

    private void backtrack(int[] nums, int depth, int len, List<Integer> tempList) {
        ret.add(new ArrayList<>(tempList));
        for (int i = depth; i < len; ++i) {
            tempList.add(nums[i]);
            backtrack(nums, i + 1, len, tempList);
            tempList.remove(tempList.size() - 1);
        }
    }

    /**
     * 79. 单词搜索
     * 深度优先遍历+回溯
     */
    public boolean exist(char[][] board, String word) {
        int height = board.length;
        int width = board[0].length;
        boolean[][] visited = new boolean[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean flag = check(board, visited, i, j, word, 0);
                if (flag) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean check(char[][] board, boolean[][] visited, int i, int j, String s, int k) {
        if (board[i][j] != s.charAt(k)) {
            // 不匹配直接返回false
            return false;
        } else if (k == s.length() - 1) {
            // 当匹配的长度与单词长度相同时,说明匹配成功,直接返回
            return true;
        }
        // 标记访问
        visited[i][j] = true;
        // 四个方向
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean result = false;
        for (int[] dir : directions) {
            int newi = i + dir[0];
            int newj = j + dir[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length) {
                if (!visited[newi][newj]) {
                    boolean flag = check(board, visited, newi, newj, s, k + 1);
                    if (flag) {
                        result = true;
                        // 匹配成功,break
                        break;
                    }
                }
            }
        }
        // 状态重置
        visited[i][j] = false;
        return result;
    }

    /**
     * 94. 二叉树的中序遍历
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        // 递归
        // List<Integer> ret=new ArrayList<>();
        // nodeValue(root,ret);
        // return ret;

        // 栈
        List<Integer> ret = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                ret.add(node.val);
                node = node.right;
            }
        }
        return ret;
    }

    private void nodeValue(TreeNode node, List<Integer> ret) {
        if (node == null) {
            return;
        }
        nodeValue(node.left, ret);
        ret.add(node.val);
        nodeValue(node.right, ret);
    }

    /**
     * 96. 不同的二叉搜索树
     * 节点个数拥有不同二叉搜索树的个数是确定的
     */
    public int numTrees(int n) {
        // 动态规划
        int[] bp = new int[n + 1];
        bp[0] = 1;
        bp[1] = 1;
        // i 为根
        for (int i = 2; i <= n; ++i) {
            // j-1为左子树
            // 左子树节点的个数从 0 到 i-1 个
            // i-j为右子树
            // 右子树节点的个数从 i-j 到 0 个
            for (int j = 1; j <= i; ++j) {
                bp[i] += bp[j - 1] * bp[i - j];
            }
        }
        return bp[n];
    }

    /**
     * 98. 验证二叉搜索树
     */
    public boolean isValidBST(TreeNode root) {
        // 中序遍历,搜索树中序遍历有序
        // Deque<TreeNode> stack=new LinkedList<>();
        // TreeNode node=root;
        // double min=-Double.MAX_VALUE;
        // while(!stack.isEmpty() || node!=null){
        //     if(node!=null){
        //         stack.push(node);
        //         node=node.left;
        //     }else{
        //         node = stack.pop();
        //         if(node.val<=min){
        //             return false;
        //         }
        //         min=node.val;
        //         node=node.right;
        //     }
        // }
        // return true;

        // 递归
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBST(TreeNode node, long lower, long upper) {
        if (node == null) {
            return true;
        }
        if (node.val <= lower || node.val >= upper) {
            return false;
        }
        return isValidBST(node.left, lower, node.val) && isValidBST(node.right, node.val, upper);
    }

    /**
     * 102. 二叉树的层序遍历
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            // 记录当前层的节点个数
            int currentLevelSize = queue.size();
            for (int i = 1; i <= currentLevelSize; ++i) {
                TreeNode tempNode = queue.poll();
                level.add(tempNode.val);
                if (tempNode.left != null) {
                    queue.add(tempNode.left);
                }
                if (tempNode.right != null) {
                    queue.add(tempNode.right);
                }
            }
            ret.add(level);
        }
        return ret;
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int preLen = preorder.length;
        int inLen = inorder.length;
        if (preLen != inLen) {
            return null;
        }
        // 哈希表存中序遍历
        Map<Integer, Integer> map = new HashMap<>(inLen);
        for (int i = 0; i < inLen; ++i) {
            map.put(inorder[i], i);
        }

        return buildTree(preorder, 0, preLen - 1, map, 0, inLen - 1);
    }

    private TreeNode buildTree(int[] preorder, int preLeft, int preRight, Map<Integer, Integer> map, int inLeft, int inRight) {
        if (preLeft > preRight || inLeft > inRight) {
            return null;
        }
        int rootVal = preorder[preLeft];
        // 在前序中得到根节点
        TreeNode root = new TreeNode(rootVal);
        // 在中序中得到根节点的下标
        int pIndex = map.get(rootVal);
        // 递归构建左子树
        root.left = buildTree(preorder, preLeft + 1, pIndex - inLeft + preLeft, map, inLeft, pIndex - 1);
        // 递归构建右子树
        root.right = buildTree(preorder, pIndex - inLeft + preLeft + 1, preRight, map, pIndex + 1, inRight);
        return root;
    }

    /**
     * 114. 二叉树展开为链表
     */
    public void flatten(TreeNode root) {
        // 先序遍历,将结果存入数组
        // List<TreeNode> list=new ArrayList<>();
        // preorderTraversal(root,list);
        // // 遍历数组,构建树
        // for(int i=1;i<list.size();++i){
        //     TreeNode prev=list.get(i-1);
        //     TreeNode curr=list.get(i);
        //     prev.left=null;
        //     prev.right=curr;
        // }

        // 边遍历边展开
        // if(root==null){
        //     return;
        // }
        // Deque<TreeNode> stack=new LinkedList<TreeNode>();
        // stack.push(root);
        // TreeNode prev=null;
        // while(!stack.isEmpty()){
        //     TreeNode curr=stack.pop();
        //     if(prev!=null){
        //         prev.left=null;
        //         prev.right=curr;
        //     }
        //     if(curr.right!=null){
        //         stack.push(curr.right);
        //     }
        //     if(curr.left!=null){
        //         stack.push(curr.left);
        //     }
        //     prev=curr;
        // }

        // 寻找前驱节点,秒啊
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) {
                // 保存当前左子树
                TreeNode next = curr.left;
                TreeNode predecessor = next;
                while (predecessor.right != null) {
                    predecessor = predecessor.right;
                }
                // 左子树的左子树连接当前节点的右子树
                predecessor.right = curr.right;
                curr.left = null;
                curr.right = next;
            }
            curr = curr.right;
        }
    }
    // private void preorderTraversal(TreeNode root,List<TreeNode> list){
    //     if(root!=null){
    //         list.add(root);
    //         preorderTraversal(root.left,list);
    //         preorderTraversal(root.right,list);
    //     }
    // }

    /**
     * 139. 单词拆分
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        // 动态规划
        Set<String> wordSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        // 拆分 0-i 的字符串,查看wordSet中是否存在
        for (int i = 1; i <= s.length(); ++i) {
            for (int j = 0; j < i; ++j) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

}
