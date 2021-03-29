public class Solution06 {

    /**
     * 167. 两数之和 II - 输入有序数组
     */
    public int[] twoSum(int[] numbers, int target) {
        // 二分查找
        // int [] ret=new int[2];
        // for(int i=0;i<numbers.length;i++){
        //     int temp=target-numbers[i];
        //     int left=i+1;
        //     int right=numbers.length-1;
        //     ret[0]=i+1;
        //     while(left<=right){
        //         int mid=left+(right-left)/2;
        //         if(numbers[mid]<temp){
        //             left=mid+1;
        //         }else if(numbers[mid]>temp){
        //             right=mid-1;
        //         }else{
        //             ret[1]=mid+1;
        //             return ret;
        //         }
        //     }
        // }
        // return ret;

        // 双指针
        int p1 = 0;
        int p2 = numbers.length - 1;
        while (p1 < p2) {
            int ret = numbers[p1] + numbers[p2];
            if (ret == target) {
                return new int[]{p1 + 1, p2 + 1};
            } else if (ret < target) {
                p1++;
            } else {
                p2--;
            }
        }
        return new int[]{-1, 1};
    }

    /**
     * 168. Excel表列名称
     */
    public String convertToTitle(int columnNumber) {
        StringBuilder sb = new StringBuilder();
        while (columnNumber != 0) {
            columnNumber--;
            int j = columnNumber % 26;
            columnNumber /= 26;
            sb.append((char) ('A' + j));
        }
        return sb.reverse().toString();
    }

    /**
     * 169. 多数元素
     */
    public int majorityElement(int[] nums) {
        // 排序
        // Arrays.sort(nums);
        // return nums[nums.length/2];

        // 摩尔投票法
        int count = 0;
        int ret = 0;
        ;
        for (int num : nums) {
            if (count == 0) {
                ret = num;
            }
            count += (ret == num) ? 1 : -1;
        }
        return ret;
    }

    /**
     * 171. Excel表列序号
     */
    public int titleToNumber(String columnTitle) {
        int ret = 0;
        for (int i = 0; i < columnTitle.length(); ++i) {
            int num = columnTitle.charAt(i) - 'A' + 1;
            ret = ret * 26 + num;
        }
        return ret;
    }

    /**
     * 172. 阶乘后的零
     */
    public int trailingZeroes(int n) {

        // O(n)
        // int zeroCount=0;
        // for(int i=5;i<=n;i+=5){
        //     int current=i;
        //     while(current%5==0){
        //         zeroCount++;
        //         current/=5;
        //     }
        // }
        // return zeroCount;

        // O(lng(n))
        int zeroCount = 0;
        while (n > 0) {
            n /= 5;
            zeroCount += n;
        }
        return zeroCount;
    }

    /**
     * 206. 反转链表
     */
    public ListNode reverseList(ListNode head) {
        // 迭代
        // ListNode prev=null;
        // ListNode curr=head;
        // while(curr!=null){
        //     ListNode next = curr.next;
        //     curr.next=prev;
        //     prev=curr;
        //     curr=next;
        // }
        // return prev;

        // 递归
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * 226. 翻转二叉树
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }
}
