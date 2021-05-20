import java.util.Arrays;

/**
 * 三角形的最大周长
 * 给定一些正数组成的数组 arr, 返回由其中三个长度组成的,面积不为零的三角形最大周长
 * 如果不能形成三角形,返回 0
 * 贪心算法
 */
public class _017_MaxLengthTriangle {
    public static void main(String[] args) {
        System.out.println(solution(new int[]{3, 6, 2, 3}));
    }

    public static int solution(int[] nums) {
        Arrays.sort(nums);
        for (int i = nums.length - 1; i >= 2; i--) {
            if (nums[i - 1] + nums[i - 2] > nums[i]) {
                return nums[i - 1] + nums[i - 2] + nums[i];
            }
        }
        return 0;
    }
}
