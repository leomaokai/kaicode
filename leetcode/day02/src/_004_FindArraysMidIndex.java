import java.util.Arrays;

/**
 * 寻找数组的中心下标
 * 给定一个整数数组 nums,返回数组的中心下标
 * 中心下标的左侧所有元素相加等于右侧所有元素相加
 * 不存在返回-1,多个返回靠近左边的
 */
public class _004_FindArraysMidIndex {

    public static void main(String[] args) {
        System.out.println(pivotIndex(new int[]{1, 7, 3, 6, 5, 6}));
    }

    public static int pivotIndex(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        int total = 0;
        for (int i = 0; i < nums.length; i++) {
            total += nums[i];
            if (total == sum) {
                return i;
            }
            sum -= nums[i];
        }
        return -1;
    }
}
