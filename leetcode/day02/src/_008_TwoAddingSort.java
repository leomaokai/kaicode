import java.util.Arrays;

/**
 * 两数之和2
 * 给定一个升序整数数组 nums,从数组中找出两个数满足相加之和等于目标数 target
 * 答案唯一,不能重复使用相同的元素
 * 返回两数下标
 * 二分,双指针
 */
public class _008_TwoAddingSort {

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2, 3, 4, 5, 6};
        System.out.println(Arrays.toString(twoSearch(nums, 10)));
        System.out.println(Arrays.toString(twoPoint(nums, 11)));
    }

    // 二分法 nlogn
    public static int[] twoSearch(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int low = i;
            int high = nums.length - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (nums[mid] == target - nums[i]) {
                    return new int[]{i, mid};
                } else if (nums[mid] > target - nums[i]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }
        return new int[0];
    }

    // 双指针 n
    public static int[] twoPoint(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int temp = nums[left] + nums[right];
            if (temp == target) {
                return new int[]{left, right};
            } else if (temp < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[0];
    }

}
