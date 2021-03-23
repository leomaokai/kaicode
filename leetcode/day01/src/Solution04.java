import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution04 {

    /**
     * 83. 删除排序链表中的重复元素
     */
    public ListNode deleteDuplicates(ListNode head) {
        // if(head==null)
        //     return null;
        // ListNode p1=null;
        // ListNode p2=null;
        // p1=head;
        // p2=head.next;
        // while(p1!=null && p2!=null){
        //     if(p1.val!=p2.val){
        //         p1=p1.next;
        //         p1.val=p2.val;
        //     }
        //     p2=p2.next;
        // }
        // p1.next=null;
        // return head;

        ListNode p = head;
        while (p != null && p.next != null) {
            if (p.val == p.next.val) {
                p.next = p.next.next;
            } else {
                p = p.next;
            }
        }
        return head;
    }

    /**
     * 88. 合并两个有序数组
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 将 nums2中的元素从下标0开始,复制到nums1
        // nums1从下标m开始复制n个
        // System.arraycopy(nums2,0,nums1,m,n);
        // Arrays.sort(nums1);

        // 双指针,从后往前
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;

        while ((p1 >= 0) && (p2 >= 0)) {
            nums1[p--] = (nums1[p1] < nums2[p2]) ? nums2[p2--] : nums1[p1--];
        }
        // 将nums2中的剩余元素(一定小)复制到nums1中
        System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
    }

    /**
     * 100. 相同的树
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // if((p==null && q!=null) || (q==null && p!=null)){
        //     return false;
        // }
        // if(p==null && q==null){
        //     return true;
        // }
        // if(p.val!=q.val){
        //     return false;
        // }
        // if(!isSameTree(p.left,q.left)){
        //     return false;
        // }
        // if(!isSameTree(p.right,q.right)){
        //     return false;
        // }
        // return true;

        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        } else if (p.val != q.val) {
            return false;
        } else {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }

    /**
     * 101. 对称二叉树
     */
    public boolean isSymmetric(TreeNode root) {
        return check(root, root);
    }

    public boolean check(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        return p.val == q.val && check(p.left, q.right) && check(p.right, q.left);
    }

    /**
     * 104. 二叉树的最大深度
     */
    public int maxDepth(TreeNode root) {
        // if(root==null){
        //     return 0;
        // }else{
        //     int leftHeight=maxDepth(root.left);
        //     int rightHeight=maxDepth(root.right);
        //     return Math.max(leftHeight,rightHeight)+1;
        // }

        return root == null ? 0 : Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * 108. 将有序数组转换为二叉搜索树
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedTree(nums, 0, nums.length - 1);
    }

    public TreeNode sortedTree(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        // 总是选择中间位置左边的数字作为根节点
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedTree(nums, left, mid - 1);
        root.right = sortedTree(nums, mid + 1, right);
        return root;
    }

    /**
     * 110. 平衡二叉树
     */
    public boolean isBalanced(TreeNode root) {

        // 自顶向下递归
        // if(root==null){
        //     return true;
        // }else{
        //     return Math.abs(height(root.left)-height(root.right)) <=1 && isBalanced(root.left) && isBalanced(root.right);
        // }

        // 自底向上递归
        return balanced(root) != -1;
    }

    public int height(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return Math.max(height(root.left), height(root.right)) + 1;
        }
    }

    public int balanced(TreeNode root) {
        if (root == null) return 0;
        int leftHeight, rightHeight;
        if ((leftHeight = balanced(root.left)) == -1
                || (rightHeight = balanced(root.right)) == -1
                || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * 111. 二叉树的最小深度
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right != null) {
            return minDepth(root.right) + 1;
        }
        if (root.right == null && root.left != null) {
            return minDepth(root.left) + 1;
        }
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;

        // if(root==null){
        //     return 0;
        // }
        // int leftHeight=minDepth(root.left);
        // int rightHeight=minDepth(root.right);
        // return (leftHeight==0 || rightHeight==0)?leftHeight+rightHeight+1 : Math.min(leftHeight,rightHeight)+1;
    }

    /**
     * 112. 路径总和
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null)
            return false;
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    /**
     * 118. 杨辉三角
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ret = new ArrayList<>();
        if (numRows < 1) {
            return ret;
        }
        List<Integer> list = Arrays.asList(1);
        ret.add(list);
        if (numRows == 1) {
            return ret;
        }
        for (int i = 1; i < numRows; ++i) {
            List<Integer> listRows = new ArrayList<>();
            // 每一行的第一数为1
            listRows.add(1);
            List<Integer> preList = ret.get(i - 1);
            for (int j = 1; j < i; ++j) {
                // 中间的数用规律求
                int temp = preList.get(j - 1) + preList.get(j);
                listRows.add(temp);
            }
            // 每一行最后一个数也是1
            listRows.add(1);
            ret.add(listRows);
        }
        return ret;
    }

    /**
     * 119. 杨辉三角 II
     */
    public List<Integer> getRow(int rowIndex) {
        // List<Integer> ret=new ArrayList<>();
        // ret.add(1);
        // if(rowIndex==0){
        //     return ret;
        // }
        // ret.add(1);
        // if(rowIndex==1){
        //     return ret;
        // }
        // for(int j=2;j<=rowIndex;++j){
        //     List<Integer> curRet=new ArrayList<>();
        //     curRet.add(1);
        //     for(int i=1;i<j;++i){
        //         curRet.add(ret.get(i-1)+ret.get(i));
        //     }
        //     curRet.add(1);
        //     ret=curRet;
        // }
        // return ret;

        // 只用一个List
        List<Integer> row = new ArrayList<>();
        row.add(1);
        for (int i = 1; i <= rowIndex; ++i) {
            row.add(0);
            for (int j = i; j > 0; --j) {
                // 在第j个位置设置row.get(j)+row.get(j-1)
                row.set(j, row.get(j) + row.get(j - 1));
            }
        }
        return row;
    }

}
