import java.util.*;

public class Solution13 {

    /**
     * 437. 路径总和 III
     * 前缀和
     * <p>
     * 两节点间的路径和 = 两节点的前缀和之差
     * <p>
     * 节点1的前缀和为: 1
     * <p>
     * 节点3的前缀和为: 1 + 2 + 3 = 6
     * <p>
     * prefix(3) - prefix(1) == 5
     * <p>
     * 所以 节点1 到 节点3 之间有一条符合要求的路径( 2 --> 3 )
     */
    public int pathSum(TreeNode root, int sum) {
        // key 为前缀和,value 为该前缀和出现的次数
        Map<Integer, Integer> prefixSumCount = new HashMap<>();

        prefixSumCount.put(0, 1);
        return recursionPathSum(root, prefixSumCount, sum, 0);
    }

    private int recursionPathSum(TreeNode root, Map<Integer, Integer> prefixSumCount, int target, int currSum) {
        if (root == null) {
            return 0;
        }

        int res = 0;
        // 此时前缀和为currSum
        currSum += root.val;
        // target 相当于两节点的路径和,currSum 为节点的前缀和
        // target + tempSum = currSum
        // tempSum = currSum - target
        // 得到 tempSum 作为前缀和的路径个数
        // 因为要得到 target 路径和的个数
        // 所以当前前缀和减去路径和就相当于得到作为起始点的个数
        res += prefixSumCount.getOrDefault(currSum - target, 0);
        // 将 currSum 作为前缀和的路径个数+1
        prefixSumCount.put(currSum, prefixSumCount.getOrDefault(currSum, 0) + 1);
        // 递归左子树
        res += recursionPathSum(root.left, prefixSumCount, target, currSum);
        // 递归右子树
        res += recursionPathSum(root.right, prefixSumCount, target, currSum);
        // 状态恢复
        prefixSumCount.put(currSum, prefixSumCount.get(currSum) - 1);
        return res;
    }

    /**
     * 438. 找到字符串中所有字母异位词
     * 滑动窗口
     */
    public List<Integer> findAnagrams(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
        List<Integer> ans = new ArrayList<>();
        if (sLen < pLen) {
            return ans;
        }
        int[] sNum = new int[26];
        int[] pNum = new int[26];
        // 初始化窗口
        for (int i = 0; i < pLen; ++i) {
            pNum[p.charAt(i) - 'a']++;
            sNum[s.charAt(i) - 'a']++;
        }
        if (Arrays.equals(sNum, pNum)) {
            ans.add(0);
        }
        // 窗口滑动
        for (int i = pLen; i < sLen; ++i) {
            sNum[s.charAt(i - pLen) - 'a']--;
            sNum[s.charAt(i) - 'a']++;
            if (Arrays.equals(sNum, pNum)) {
                // 起始位置
                ans.add(i - pLen + 1);
            }
        }
        return ans;
    }

    /**
     * 494. 目标和
     * 01背包问题
     */
    public int findTargetSumWays(int[] nums, int target) {
//        int sum = 0;
//        int len = nums.length;
//        for (int i = 0; i < len; i++) {
//            sum += nums[i];
//        }
//        if (sum < Math.abs(target)) {
//            return 0;
//        }
//
//        int[][] dp = new int[len][sum * 2 + 1];
//        // 初始化
//        if (nums[0] == 0) {
//            dp[0][sum] = 2;
//        } else {
//            dp[0][sum - nums[0]] = 1;
//            dp[0][sum + nums[0]] = 1;
//        }
//
//        for (int i = 1; i < len; i++) {
//            for (int j = 0; j < sum * 2 + 1; j++) {
//                int l = (j - nums[i]) >= 0 ? j - nums[i] : 0;
//                int r = (j + nums[i]) < sum * 2 + 1 ? j + nums[i] : 0;
//                dp[i][j] = dp[i - 1][l] + dp[i - 1][r];
//            }
//        }
//        return dp[len - 1][sum + target];

        int[][] dp = new int[nums.length][2001];
        dp[0][nums[0] + 1000] = 1;
        dp[0][-nums[0] + 1000] += 1;
        for (int i = 1; i < nums.length; i++) {
            for (int sum = -1000; sum <= 1000; sum++) {
                if (dp[i - 1][sum + 1000] > 0) {
                    dp[i][sum + nums[i] + 1000] += dp[i - 1][sum + 1000];
                    dp[i][sum - nums[i] + 1000] += dp[i - 1][sum + 1000];
                }
            }
        }
        return target > 1000 ? 0 : dp[nums.length - 1][target + 1000];
    }

    /**
     * 538. 把二叉搜索树转换为累加树
     * 反中序遍历
     * 优化(morris遍历)
     */
    int sum = 0;

    public TreeNode convertBST(TreeNode root) {
        if (root != null) {
            convertBST(root.right);
            sum += root.val;
            root.val = sum;
            convertBST(root.left);
        }
        return root;
    }

    /**
     * 560. 和为K的子数组
     * 前缀和+哈希表
     * pre[j]-pre[i]=k
     * pre[i]=pre[j]-k
     */
    public int subarraySum(int[] nums, int k) {
        // int len=nums.length;
        // int count=0;
        // int[] preSum=new int[len+1];
        // preSum[0]=0;
        // for(int i=0;i<len;++i){
        //     preSum[i+1]=preSum[i]+nums[i];
        // }

        // for(int i=0;i<len;++i){
        //     for(int j=i;j<len;++j){
        //         if(preSum[j+1]-preSum[i]==k){
        //             count++;
        //         }
        //     }
        // }
        // return count;

        int count = 0;
        int pre = 0;
        // 哈希表记录该前缀和的个数
        Map<Integer, Integer> map = new HashMap<>();
        // 前缀和为0,个数为1
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            pre += nums[i];
            if (map.containsKey(pre - k)) {
                count += map.get(pre - k);
            }
            map.put(pre, map.getOrDefault(pre, 0) + 1);
        }
        return count;
    }

    /**
     * 23. 合并K个升序链表
     * 两两合并/堆/分治
     */
    // 小顶堆 time:O(nlogk) 空间:O(k)
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode head = new ListNode(0);
        ListNode cur = head;
        int len = lists.length;
        // 小顶堆
        Queue<ListNode> queue = new PriorityQueue<>((ListNode node1, ListNode node2) -> {
            return node1.val - node2.val;
        });
        // int k = lists.length;
        // 构建 k 大小的小顶堆
        for (ListNode list : lists) {
            if (list == null)
                continue;
            queue.offer(list);
        }
        while (!queue.isEmpty()) {
            ListNode node = queue.poll();
            cur.next = node;
            // 该结点的next 不为空,则入堆
            if (node.next != null) {
                queue.offer(node.next);
            }
            cur = cur.next;
        }
        return head.next;
    }

    // 分治 O(nlogk)
    public ListNode mergeKLists2(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        int len = lists.length;
        int step = 1;
        while (step < len) {
            int nextStep = step << 1;
            for (int i = 0; i + step < lists.length; i += nextStep) {
                lists[i] = mergeTwoLists(lists[i], lists[i + step]);
            }
            step = nextStep;
        }
        return lists[0];
    }

    private ListNode head = new ListNode(0);

    private ListNode mergeTwoLists(ListNode k1, ListNode k2) {
        if (k1 == null && k2 == null) {
            return null;
        }
        head.next = null;
        ListNode cur = head;
        while (k1 != null && k2 != null) {
            if (k1.val < k2.val) {
                cur.next = k1;
                k1 = k1.next;
            } else {
                cur.next = k2;
                k2 = k2.next;
            }
            cur = cur.next;
        }
        cur.next = k1 == null ? k2 : k1;
        return head.next;
    }

    /**
     * 581. 最短无序连续子数组
     */
    public int findUnsortedSubarray(int[] nums) {
        // int[] tempNums=nums.clone();
        // Arrays.sort(tempNums);
        // int len=nums.length;
        // int left=0;
        // int right=len-1;
        // while(left<len && nums[left]==tempNums[left]){
        //     left++;
        // }
        // while(right>=0 && nums[right]==tempNums[right]){
        //     right--;
        // }
        // return right-left<0?0:right-left+1;

        // 将数组分为左中右三段
        // 左段有序,右段有序,中段无序
        // 中段的最小值大于左段的最大值
        // 中段的最大值小于右段的最小值
        int len = nums.length;
        int min = nums[len - 1];
        int max = nums[0];
        int left = 0;
        int right = -1;

        for (int i = 0; i < len; i++) {
            // 从左到右维持最大值,寻找右边界right
            if (nums[i] < max) {
                right = i;
            } else {
                max = nums[i];
            }

            // 从右到左维持最小值,寻找左边界left
            if (nums[len - i - 1] > min) {
                left = len - i - 1;
            } else {
                min = nums[len - i - 1];
            }
        }

        return right - left + 1;
    }

}
