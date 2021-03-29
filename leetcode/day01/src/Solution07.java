import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution07 {

    /**
     * 234. 回文链表
     */
    public boolean isPalindrome(ListNode head) {
        // StringBuilder sb=new StringBuilder();
        // while(head!=null){
        //     sb.append(head.val);
        //     head=head.next;
        // }
        // String str=sb.toString();
        // if((sb.reverse().toString()).equals(str)){
        //     return true;
        // }
        // return false;

        // 数组+双指针
        // List<Integer> arr=new ArrayList<Integer>();
        // // 将链表的值复制到数组中
        // ListNode currentNode=head;
        // while(currentNode!=null){
        //     arr.add(currentNode.val);
        //     currentNode=currentNode.next;
        // }
        // // 使用双指针判断是否是回文
        // int front=0;
        // int back=arr.size()-1;
        // while(front<back){
        //     if(!arr.get(front).equals(arr.get(back))){
        //         return false;
        //     }
        //     front++;
        //     back--;
        // }
        // return true;

        // 快慢指针将链表分为前后两个部分
        if (head == null) {
            return true;
        }
        // 找到前半部分的链表的尾节点
        ListNode fast = head;
        ListNode firstHalfEnd = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            firstHalfEnd = firstHalfEnd.next;
        }
        // 反转后半部分
        ListNode prev = null;
        ListNode curr = firstHalfEnd.next;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        // 判断是否是回文
        ListNode p1 = head;
        ListNode p2 = prev;
        while (p2 != null) {
            if (p1.val != p2.val) {
                return false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        return true;
    }

    /**
     * 283. 移动零
     */
    public void moveZeroes(int[] nums) {
        // 从后向前遍历
        // int len=nums.length;
        // int endIndex=len-1;
        // for(int i=len-1;i>=0;i--){
        //     if(nums[i]==0){
        //         for(int j=i;j<endIndex;j++){
        //             nums[j]=nums[j+1];
        //             nums[j+1]=0;
        //         }
        //         endIndex--;
        //     }
        // }

        // 双指针
        int len = nums.length;
        int right = 0;
        int left = 0;
        while (right < len) {
            if (nums[right] != 0) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
            }
            right++;
        }
    }

    /**
     * 448. 找到所有数组中消失的数字
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ret = new ArrayList<>();
        int n = nums.length;
        for (int num : nums) {
            int index = (num - 1) % n;
            nums[index] += n;
        }
        for (int i = 1; i <= n; ++i) {
            if (nums[i - 1] <= n) {
                ret.add(i);
            }
        }
        return ret;
    }

    /**
     * 461. 汉明距离
     */
    public int hammingDistance(int x, int y) {
        int xor = x ^ y;
        int ret = 0;
        while (xor != 0) {
            if (xor % 2 == 1)
                ret++;
            xor = xor >> 1;
        }
        return ret;
    }

    /**
     * 543. 二叉树的直径
     */
    int max;

    public int diameterOfBinaryTree(TreeNode root) {
        max = 0;
        getDepth(root);
        return max;
    }

    public int getDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = getDepth(node.left);
        int rightDepth = getDepth(node.right);
        max = Math.max(max, leftDepth + rightDepth);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 617. 合并二叉树
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        TreeNode ret = new TreeNode(root1.val + root2.val);
        ret.left = mergeTrees(root1.left, root2.left);
        ret.right = mergeTrees(root1.right, root2.right);
        return ret;
    }

    /**
     * 10. 正则表达式匹配
     */
    public boolean isMatch(String s, String p) {
        int len1 = s.length();
        int len2 = p.length();
        // 多开辟一行 [0][0] 是默认 true
        boolean[][] bp = new boolean[len1 + 1][len2 + 1];
        bp[0][0] = true;
        for (int i = 0; i <= len1; ++i) {
            for (int j = 1; j <= len2; ++j) {
                // p 字符串的 j-1 是'*'
                if (p.charAt(j - 1) == '*') {
                    // 直接等于 当 *和它前一个字符不存在时的结果
                    bp[i][j] = bp[i][j - 2];
                    // 如果和前一个字符相同,则结果等于i-1时的结果
                    // 看简答视频
                    if (matches(s, p, i, j - 1)) {
                        bp[i][j] = bp[i][j] || bp[i - 1][j];
                    }
                } else {
                    if (matches(s, p, i, j)) {
                        bp[i][j] = bp[i - 1][j - 1];
                    }
                }
            }
        }
        return bp[len1][len2];
    }

    public boolean matches(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }

    /**
     * 11. 盛最多水的容器
     */
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int max = 0;
        while (left < right) {
            int height1 = height[left];
            int height2 = height[right];
            if (height1 < height2) {
                max = Math.max(max, height1 * (right - left));
                left++;
            } else {
                max = Math.max(max, height2 * (right - left));
                right--;
            }
        }
        return max;
    }

    /**
     * 12. 整数转罗马数字
     */
    int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public String intToRoman(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length && num >= 0; i++) {
            while (values[i] <= num) {
                num -= values[i];
                sb.append(symbols[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 15. 三数之和
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> retList = new ArrayList<>();
        if (nums == null || nums.length <= 2) {
            return retList;
        }
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; ++i) {
            // 第一个大于0直接不成立
            if (nums[i] > 0)
                break;
            // 去重操作
            if (i > 0 && nums[i] == nums[i - 1])
                continue;
            int target = -nums[i];
            // 从 i+1 开始判断
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                if (nums[left] + nums[right] == target) {
                    retList.add(new ArrayList<>(Arrays.asList(nums[i], nums[left], nums[right])));
                    left++;
                    right--;
                    // 去重
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (nums[left] + nums[right] < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return retList;
    }

    /**
     * 17. 电话号码的字母组合
     */
    // 数字到号码的映射
    private String[] map = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    // 路径
    private StringBuilder sb = new StringBuilder();
    // 结果集
    private List<String> res = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0)
            return res;
        backtrack(digits, 0);
        return res;
    }

    // 回溯函数
    private void backtrack(String digits, int index) {
        if (sb.length() == digits.length()) {
            res.add(sb.toString());
            return;
        }
        String val = map[digits.charAt(index) - '2'];
        for (char ch : val.toCharArray()) {
            sb.append(ch);
            backtrack(digits, index + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
