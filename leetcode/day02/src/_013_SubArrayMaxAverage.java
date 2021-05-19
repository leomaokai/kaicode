/**
 * 子数组最大平均数
 * 给一个整数数组,找出平均数最大且长度为k的下标连续的子数组,并输出该最大平均数
 * 滑动窗口
 */
public class _013_SubArrayMaxAverage {

    public static void main(String[] args) {
        System.out.println(solution(new int[]{1, 12, -5, -6, 50, 3}, 4));
    }

    // 滑动窗口
    public static double solution(int[] nums, int k) {
        int sum = 0;
        int n = nums.length;
        // 先统计第一个窗口
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        int res = sum;
        // 窗口移动
        for (int i = k; i < n; i++) {
            sum = sum - nums[i - k] + nums[i];
            res = Math.max(res, sum);
        }
        return 1.0 * res / k;
    }
}
