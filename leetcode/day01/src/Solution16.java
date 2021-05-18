import java.util.*;

public class Solution16 {

    /**
     * 91. 解码方法
     */
    public int numDecodings(String s) {
        // 类似于青蛙条台阶,可以跳1级,也可以跳2级
        int len = s.length();
        int[] dp = new int[len + 1];
        dp[0] = 1;
        for (int i = 1; i <= len; i++) {
            if (s.charAt(i - 1) != '0') {
                dp[i] += dp[i - 1];
            }
            if (i > 1 && s.charAt(i - 2) != '0') {
                if (s.charAt(i - 2) == '1' || ((s.charAt(i - 2) == '2') && (s.charAt(i - 1) - '0' < 7))) {
                    dp[i] += dp[i - 2];
                }
            }
        }
        return dp[len];
    }

    /**
     * 103. 二叉树的锯齿形层序遍历
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            if (res.size() % 2 == 1) {
                Collections.reverse(list);
            }
            res.add(list);
        }
        return res;
    }

    /**
     * 130. 被围绕的区域
     */
    public void solve(char[][] board) {
        int rowLen = board.length;
        int colLen = board[0].length;
        for (int i = 0; i < rowLen; i++) {
            dfs(board, i, 0);
            dfs(board, i, colLen - 1);
        }
        for (int i = 1; i < colLen - 1; i++) {
            dfs(board, 0, i);
            dfs(board, rowLen - 1, i);
        }
        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {
                if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs(char[][] board, int x, int y) {
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length || board[x][y] != 'O') {
            return;
        }
        board[x][y] = 'A';
        dfs(board, x + 1, y);
        dfs(board, x - 1, y);
        dfs(board, x, y + 1);
        dfs(board, x, y - 1);
    }

    /**
     * 131. 分割回文串
     * 回溯 + 动态规划
     */

    boolean[][] dp;
    List<List<String>> res = new ArrayList<>();
    List<String> ans = new ArrayList<>();
    int len;

    public List<List<String>> partition(String s) {
        len = s.length();
        dp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            Arrays.fill(dp[i], true);
        }
        // 通过动态规划判断 i~j 是否为回文,从后往前判断
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i + 1; j < len; j++) {
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1];
            }
        }
        // 回溯
        dfs(s, 0);
        return res;
    }

    // i 为开始回溯的下标
    private void dfs(String s, int i) {
        if (i == len) {
            res.add(new ArrayList<String>(ans));
            return;
        }
        for (int j = i; j < len; j++) {
            if (dp[i][j]) {
                ans.add(s.substring(i, j + 1));
                dfs(s, j + 1);
                ans.remove(ans.size() - 1);
            }
        }
    }

    /**
     * 134. 加油站
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int len = gas.length;
        int totalTank = 0;
        int currTank = 0;
        int start = 0;
        for (int i = 0; i < len; i++) {
            // 总和必须大于等于 0, 否则无法完成绕行
            totalTank += gas[i] - cost[i];
            // 当前汽油若小于 0, 从下一站重新开始
            currTank += gas[i] - cost[i];
            if (currTank < 0) {
                start = i + 1;
                currTank = 0;
            }
        }
        return totalTank >= 0 ? start : -1;
    }


}
