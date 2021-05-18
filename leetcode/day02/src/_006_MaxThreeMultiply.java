import java.util.Arrays;

/**
 * 三个数的最大乘积
 * 在整型数组 nums,在数组中找出由三个数字组成的最大乘积,不考虑越界
 * 线性扫描
 */
public class _006_MaxThreeMultiply {

    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 2, 3, 4, 5, 6};
        int[] nums2 = new int[]{-1, -2, -3, -4, 5, 6};
        System.out.println(getMaxBySort(nums1));
        System.out.println(getMaxMin(nums1));
        System.out.println(getMaxBySort(nums2));
        System.out.println(getMaxMin(nums2));
    }

    // 数组中都是正数或负数,找三个最大的数相乘
    // 有正有负,找绝对值最大的两个负数

    // 只有两种情况
    // 1.找三个最大的数
    // 2.找两个绝对值最大的负数与一个最大的正数
    // 比较两种情况的结果即可
    public static int getMaxBySort(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        return Math.max(nums[0] * nums[1] * nums[n - 1],
                nums[n - 1] * nums[n - 2] * nums[n - 3]);
    }

    // 线性扫描
    // 核心为找 5 个数
    public static int getMaxMin(int[] nums) {
        // 定义两个最小值
        int min1 = Integer.MAX_VALUE; // 最小
        int min2 = Integer.MAX_VALUE; // 第二小
        // 定义三个最大值
        int max1 = Integer.MIN_VALUE; // 最大
        int max2 = Integer.MIN_VALUE;
        int max3 = Integer.MIN_VALUE;

        for (int x : nums) {
            if (x < min1) {
                min2 = min1;
                min1 = x;
            } else if (x < min2) {
                min2 = x;
            }
            if (x > max1) {
                max3 = max2;
                max2 = max1;
                max1 = x;
            } else if (x > max2) {
                max3 = max2;
                max2 = x;
            } else if (x > max3) {
                max3 = x;
            }
        }
        return Math.max(min1 * min2 * max1, max1 * max2 * max3);
    }
}
