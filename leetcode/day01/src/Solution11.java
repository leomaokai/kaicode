import java.util.*;

public class Solution11 {

    /**
     * 240. 搜索二维矩阵 II
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        // 二分查找,每一行每一列进行二分
        // int len=Math.min(matrix.length,matrix[0].length);
        // for(int i=0;i<len;++i){
        //     boolean rowFound=searchMatrix(matrix,target,i,true);
        //     boolean colFound=searchMatrix(matrix,target,i,false);
        //     if(rowFound || colFound){
        //         return true;
        //     }
        // }
        // return false;

        // 将矩阵看作二叉搜索树,左下角为根
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        int i = rowLen - 1;
        int j = 0;
        while (i >= 0 && j < colLen) {
            if (target > matrix[i][j]) {
                // 当前值小于目标值,向左查找
                j++;
            } else if (target < matrix[i][j]) {
                // 当前值大于目标值,向上查找
                i--;
            } else {
                return true;
            }
        }
        return false;
    }

    // private boolean searchMatrix(int[][] matrix,int target,int start,boolean flag){
    //     int left=start;
    //     int right=flag?matrix[0].length-1:matrix.length-1;
    //     while(left<=right){
    //         int mid=(left+right)/2;
    //         if(flag){
    //             if(matrix[start][mid]<target){
    //                 left=mid+1;
    //             }else if(matrix[start][mid]>target){
    //                 right=mid-1;
    //             }else{
    //                 return true;
    //             }
    //         }else{
    //             if(matrix[mid][start]<target){
    //                 left=mid+1;
    //             }else if(matrix[mid][start]>target){
    //                 right=mid-1;
    //             }else{
    //                 return true;
    //             }
    //         }
    //     }
    //     return false;
    // }

    /**
     * 279. 完全平方数
     * 动态规划
     */
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            dp[i] = i;
            for (int j = 1; i - j * j >= 0; j++) {
                dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
            }
        }
        return dp[n];
    }

    /**
     * 287. 寻找重复数
     */
    public int findDuplicate(int[] nums) {
        // 二分查找
        // 数组中小于等于下标的数如果大于下标,
        // 说明小于等于下标的数中有重复
        // int len=nums.length;
        // int left=0;
        // int right=len-1;
        // int ret=0;
        // while(left<=right){
        //     int mid=(left+right)/2;
        //     int cnt=0;
        //     for(int i=0;i<len;++i){
        //         cnt += nums[i]<=mid? 1:0;
        //     }
        //     if(cnt<=mid){
        //         left=mid+1;
        //     }else{
        //         right=mid-1;
        //         ret=mid;
        //     }
        // }
        // return ret;

        // 快慢指针,类似于链表环,值相当于指针
        int slow = nums[0];
        int fast = nums[0];
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        int ptr = nums[0];
        while (ptr != slow) {
            ptr = nums[ptr];
            slow = nums[slow];
        }
        return ptr;
    }

    /**
     * 300. 最长递增子序列
     */
    public int lengthOfLIS(int[] nums) {
        // 动态规划
        // int len=nums.length;
        // int[] bp=new int[len];
        // int ret=0;
        // for(int i=0;i<len;++i){
        //     bp[i]=1;
        //     for(int j=0;j<i;++j){
        //         if(nums[i]>nums[j]){
        //             bp[i]=Math.max(bp[i],bp[j]+1);
        //         }
        //     }
        //     ret=Math.max(ret,bp[i]);
        // }
        // return ret;

        // 贪心+二分查找
        // tails存放最长序列
        // tails[i]越小,之后遇到比它大的数几率越大
        int[] tails = new int[nums.length];
        int res = 0;
        for (int num : nums) {
            int i = 0;
            int j = res;
            // 对tails数组进行二分查找
            while (i < j) {
                int mid = (i + j) / 2;
                if (tails[mid] < num) {
                    i = mid + 1;
                } else {
                    j = mid;
                }
            }
            // 替换,num值比之前的tails[i]小
            tails[i] = num;
            if (res == j) {
                res++;
            }
        }
        return res;
    }

    /**
     * 309. 最佳买卖股票时机含冷冻期
     */
    public int maxProfit(int[] prices) {
        int len = prices.length;
        // 动态规划 99.47 39
        // dp[i][0]:持有股票时的最大收益
        // dp[i][1]:不持有股票,处于冷冻期的最大收益
        // dp[i][2]:不持有股票,不处于冷冻期的最大收益
        // int[][]dp=new int[len][3];
        // dp[0][0]=-prices[0];
        // for(int i=1;i<len;++i){
        //     dp[i][0]=Math.max(dp[i-1][0],dp[i-1][2]-prices[i]);
        //     dp[i][1]=dp[i-1][0]+prices[i];
        //     dp[i][2]=Math.max(dp[i-1][1],dp[i-1][2]);
        // }
        // return Math.max(dp[len-1][1],dp[len-1][2]);

        // 滑动数组 99.47 98.67
        int dp0 = -prices[0];
        int dp1 = 0;
        int dp2 = 0;
        for (int i = 1; i < len; ++i) {
            int newDp0 = Math.max(dp0, dp2 - prices[i]);
            int newDp1 = dp0 + prices[i];
            int newDp2 = Math.max(dp1, dp2);
            dp0 = newDp0;
            dp1 = newDp1;
            dp2 = newDp2;
        }
        return Math.max(dp1, dp2);
    }

    /**
     * 322. 零钱兑换
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int i = 1; i <= amount; ++i) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * 337. 打家劫舍 III
     */
    public int rob(TreeNode root) {
        // Map<TreeNode,Integer> resMap=new HashMap<>();
        // return dfs(root,resMap);

        int[] res = dfs(root);
        return Math.max(res[0], res[1]);
    }

    // private int dfs(TreeNode root,Map<TreeNode,Integer> resMap){
    //     if(root==null){
    //         return 0;
    //     }
    //     if(resMap.containsKey(root)){
    //         return resMap.get(root);
    //     }
    //     int money=root.val;
    //     if(root.left!=null){
    //         money+=(dfs(root.left.left,resMap)+dfs(root.left.right,resMap));
    //     }
    //     if(root.right!=null){
    //         money+=(dfs(root.right.left,resMap)+dfs(root.right.right,resMap));
    //     }
    //     int res=Math.max(money,dfs(root.left,resMap)+dfs(root.right,resMap));
    //     resMap.put(root,res);
    //     return res;
    // }

    private int[] dfs(TreeNode root) {
        int[] ret = new int[2];
        if (root == null) {
            return ret;
        }
        int[] left = dfs(root.left);
        int[] right = dfs(root.right);
        ret[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        ret[1] = left[0] + right[0] + root.val;
        return ret;
    }

    /**
     * 338. 比特位计数
     */
    public int[] countBits(int num) {
        // 8.79 84.81
        // int[] res=new int[num+1];
        // for(int i=0;i<=num;++i){
        //     int count=0;
        //     int temp=i;
        //     while(temp>0){
        //         if(temp%2!=0){
        //             count++;
        //         }
        //         temp>>=1;
        //     }
        //     res[i]=count;
        // }
        // return res;

        // 动态规划 64.41 85.61
        // int[] bits=new int[num+1];
        // int highBit=0;
        // for(int i=1;i<=num;++i){
        //     if((i&(i-1))==0){
        //         // 说明 i 是2的次幂
        //         highBit=i;
        //     }
        //     bits[i]=bits[i-highBit]+1;
        // }
        // return bits;

        // 动态规划 99.94 35.43
        int[] bits = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            // i 是偶数,则 bits[i]=bits[i/2]
            // i 是奇数,则 bits[i]=bits[i/2]+1
            bits[i] = bits[i >> 1] + (i & 1);
        }
        return bits;
    }

    /**
     * 347. 前 K 个高频元素
     * 哈希表和堆
     */
    public int[] topKFrequent(int[] nums, int k) {
        // 使用哈希表存数组,k为值,value为出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        // 小顶堆,int[] 第一个元素为数组值,第二个元素为出现的次数
        Queue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] m, int[] n) {
                return m[1] - n[1];
            }
        });
        // 遍历哈希表
        // 大小为 k 的小顶堆,大于堆顶的数则插入
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();
            if (queue.size() == k) {
                // 出现次数比堆顶多
                if (queue.peek()[1] < count) {
                    queue.poll();
                    queue.offer(new int[]{num, count});
                }
            } else {
                queue.offer(new int[]{num, count});
            }
        }
        // 存放结果
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = queue.poll()[0];
        }
        return res;
    }

    /**
     * 394. 字符串解码
     */
    public String decodeString(String s) {
        StringBuilder res = new StringBuilder();
        int multi = 0;
        // 数字栈
        Deque<Integer> stack_multi = new LinkedList<>();
        // 字符串栈
        Deque<String> stack_res = new LinkedList<>();
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c == '[') {
                // 遇到左括号,入栈
                stack_multi.push(multi);
                stack_res.push(res.toString());
                // 置空
                multi = 0;
                res = new StringBuilder();
            } else if (c == ']') {
                // 遇到右括号,出栈,做拼接
                StringBuilder temp = new StringBuilder();
                // 得到栈顶的数字
                int cur_multi = stack_multi.pop();
                // 拼接字符串
                for (int i = 0; i < cur_multi; ++i) {
                    temp.append(res);
                }
                res = new StringBuilder(stack_res.pop() + temp);
            } else if (c >= '0' && c <= '9') {
                // 处理 数字 妙啊.... c+"" 是将char转为String
                multi = multi * 10 + Integer.parseInt(c + "");
            } else {
                res.append(c);
            }
        }
        return res.toString();
    }

}
