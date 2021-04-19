import java.util.*;

public class Solution08 {

    /**
     * 19. 删除链表的倒数第 N 个结点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 普通方法
        // int size=0;
        // ListNode p1=head;
        // while(p1!=null){
        //     size++;
        //     p1=p1.next;
        // }
        // if(size==n){
        //     return head.next;
        // }
        // int index=size-n;
        // int i=0;
        // ListNode p2=head;
        // while(p2!=null){
        //     i++;
        //     if(i==index){
        //         p2.next=p2.next.next;
        //         break;
        //     }
        //     p2=p2.next;
        // }
        // return head;

        // 栈
        // ListNode temp=head;
        // LinkedList<ListNode> stack=new LinkedList<>();
        // while(temp!=null){
        //     stack.push(temp);
        //     temp=temp.next;
        // }
        // while(n>0){
        //     stack.pop();
        //     n--;
        // }
        // if(stack.size()!=0){
        //     ListNode top=stack.peek();
        //     top.next=top.next.next;
        //     return head;
        // }
        // return head.next;

        // 快慢指针
        ListNode fast = head;
        ListNode slow = head;
        boolean flag = true;
        while (n > 0) {
            n--;
            fast = fast.next;
        }
        if (fast != null) {
            flag = false;
            fast = fast.next;
            while (fast != null) {
                fast = fast.next;
                slow = slow.next;
            }
            slow.next = slow.next.next;
        }
        if (slow == head && flag) {
            return head.next;
        }
        return head;
    }

    /**
     * 22. 括号生成
     */
    List<String> res = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        getAll("", n, n);
        return res;
    }

    private void getAll(String str, int left, int right) {
        // 剩余的左括号一定要比右括号少
        if (left == 0 && right == 0) {
            res.add(str);
            return;
        }
        // 剩余的左右括号数相同时,只能使用左括号
        if (left == right) {
            getAll(str + "(", left - 1, right);
        }
        // 剩余的左括号叫少时,左右括号都可以使用
        if (left < right) {
            if (left > 0) {
                getAll(str + "(", left - 1, right);
            }
            getAll(str + ")", left, right - 1);
        }
    }

    /**
     * 31. 下一个排列
     */
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // 后面序列为降序,交换为升序
    private void reverse(int[] nums, int i) {
        int left = i;
        int right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    /**
     * 33. 搜索旋转排序数组
     */
    public int search(int[] nums, int target) {
        int len = nums.length;
        if (len == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int left = 0;
        int right = len - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            // 左半边有序
            if (nums[0] <= nums[mid]) {
                if (nums[0] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[len - 1]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * 二分法找小于target的最大值下表和大于target的最小值下表
     */
    public int[] searchRange(int[] nums, int target) {
        int leftIndex = getIndex(nums, target, true);
        int rightIndex = getIndex(nums, target, false) - 1;
        if (leftIndex <= rightIndex && rightIndex < nums.length && nums[leftIndex] == target && nums[rightIndex] == target) {
            return new int[]{leftIndex, rightIndex};
        }
        return new int[]{-1, -1};
    }

    // flag为true表示寻找小于target的最大值
    private int getIndex(int[] nums, int target, boolean flag) {
        int right = nums.length - 1;
        int left = 0;
        int ret = nums.length;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (target < nums[mid] || (flag && target <= nums[mid])) {
                right = mid - 1;
                ret = mid;
            } else {
                left = mid + 1;
            }
        }
        return ret;
    }

    /**
     * 39. 组合总和
     * 回溯,可重复
     */
    // 结果集
    private List<List<Integer>> resList = new ArrayList<>();

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 路径
        List<Integer> path = new ArrayList<>();
        // 排序
        Arrays.sort(candidates);
        // 深度优先遍历回溯
        backtrack(path, candidates, target, 0, 0);
        return resList;
    }

    private void backtrack(List<Integer> path, int[] candidates, int target, int sum, int begin) {
        // 退出递归条件
        if (sum == target) {
            resList.add(new ArrayList<>(path));
            return;
        }
        for (int i = begin; i < candidates.length; i++) {
            int temp = candidates[i] + sum;
            if (temp <= target) {
                path.add(candidates[i]);
                backtrack(path, candidates, target, temp, i);
                path.remove(path.size() - 1);
            } else {
                break;
            }
        }
    }

    /**
     * 46. 全排列
     * 回溯算法
     */
    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }
        // 记录路径
        Deque<Integer> path = new ArrayDeque<>();
        // 记录该数字是否被使用
        boolean[] used = new boolean[len];
        // 深度优先遍历
        dfs(nums, len, 0, path, used, res);
        return res;
    }

    private void dfs(int[] nums, int len, int depth, Deque<Integer> path, boolean[] used, List<List<Integer>> res) {
        // 退出递归的条件
        if (depth == len) {
            // 将路径添加到结果集,要使用拷贝,不能直接添加引用
            res.add(new ArrayList<>(path));
            return;
        }
        // 分支,len个分支
        for (int i = 0; i < len; i++) {
            // 该数字被使用过了,直接跳过
            if (used[i]) {
                continue;
            }
            // 添加路径
            path.addLast(nums[i]);
            // 标记使用
            used[i] = true;
            // 递归
            dfs(nums, len, depth + 1, path, used, res);
            // 递归返回后要回溯
            path.removeLast();
            used[i] = false;
        }
    }

    /**
     * 48. 旋转图像
     */
    public void rotate(int[][] matrix) {
        // 使用辅助数组
        // int n=matrix.length;
        // int [][] matrix_new=new int[n][n];
        // for(int i=0;i<n;++i){
        //     for(int j=0;j<n;++j){
        //         matrix_new[j][n-i-1]=matrix[i][j];
        //     }
        // }
        // for(int i=0;i<n;++i){
        //     for(int j=0;j<n;++j){
        //         matrix[i][j]=matrix_new[i][j];
        //     }
        // }

        // 原地旋转
        int n = matrix.length;
        for (int i = 0; i < n / 2; ++i) {
            for (int j = 0; j < (n + 1) / 2; ++j) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = temp;
            }
        }
    }


}
