public class Solution05 {

    /**
     * 121.买股票的最佳时机
     */
    public int maxProfit1(int[] prices) {
        int len = prices.length;
        if (len == 1) {
            return 0;
        }
        int curMin = prices[0];
        int ret = 0;
        for (int i = 1; i < len; ++i) {
            curMin = Math.min(curMin, prices[i]);
            ret = Math.max(ret, prices[i] - curMin);
        }
        return ret;
    }

    /**
     * 122. 买卖股票的最佳时机 II
     */
    public int maxProfit2(int[] prices) {
        // 贪心
        // int len=prices.length;
        // int ret=0;
        // for(int i=1;i<len;++i){
        //     ret+=Math.max(0,prices[i]-prices[i-1]);
        // }
        // return ret;

        // 动态规划
        int len = prices.length;
        // 当前未购股票时的持有利润
        int dp0 = 0;
        // 当前购买了股票的持有利润
        int dp1 = -prices[0];
        for (int i = 1; i < len; ++i) {
            int newDp0 = Math.max(dp0, dp1 + prices[i]);
            int newDp1 = Math.max(dp1, dp0 - prices[i]);
            dp0 = newDp0;
            dp1 = newDp1;
        }
        return dp0;
    }

    /**
     * 125. 验证回文串
     */
    public boolean isPalindrome(String s) {
        // int len=s.length();
        // StringBuilder sb=new StringBuilder(len);
        // char [] chars=s.toCharArray();
        // for(int i=0;i<len;++i){
        //     char ch=chars[i];
        //     if(Character.isLetterOrDigit(ch)){
        //         sb.append(Character.toLowerCase(ch));
        //     }
        // }
        // StringBuilder sb2=new StringBuilder(sb).reverse();
        // return sb.toString().equals(sb2.toString());

        // 双指针
        int len = s.length();
        int left = 0;
        int right = len - 1;
        char[] chars = s.toCharArray();
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(chars[left])) {
                ++left;
            }
            while (left < right && !Character.isLetterOrDigit(chars[right])) {
                --right;
            }
            if (left < right) {
                if (Character.toLowerCase(chars[left]) != Character.toLowerCase(chars[right])) {
                    return false;
                }
                ++left;
                --right;
            }
        }
        return true;
    }

    /**
     * 136. 只出现一次的数字
     */
    public int singleNumber(int[] nums) {
        // set
        // HashSet<Integer> ret = new HashSet<>();
        // for(int i = 0; i < nums.length; i++){
        //     if(!ret.add(nums[i])){
        //         ret.remove(nums[i]);
        //     }
        // }
        // return ret.iterator().next();

        // 异或
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }

    /**
     * 141. 环形链表
     */
    public boolean hasCycle(ListNode head) {
        // set
        // Set<ListNode> set=new HashSet<>();
        // while(head!=null){
        //     if(!set.add(head)){
        //         return true;
        //     }
        //     head=head.next;
        // }
        // return false;

        // 双指针
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * 160. 相交链表
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode A = headA, B = headB;
        while (A != B) {
            A = A != null ? A.next : headB;
            B = B != null ? B.next : headA;
        }
        return A;
    }

}
