/**
 * 最长连续递增序列
 * 给定一个未经排序的整数数组,找到最长且连续递增的子序列,并返回该序列的长度
 * 贪心算法
 */
public class _015_LongestConIncSeq {

    public static void main(String[] args) {

        System.out.println(solution(new int[]{1, 2, 3, 2, 3, 4, 5, 6, 7, 8, 3, 4, 5, 6, 7}));
    }

    public static int solution(int[] nums) {
        int start = 0;
        int max = nums.length == 0 ? 0 : 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] <= nums[i - 1]) {
                start = i;
            }
            max = Math.max(max, i - start + 1);
        }
        return max;
    }
}
