/**
 * 删除有序数组中的重复元素
 * 有序数组 nums ,原地删除重复元素,返回删除后数组的新长度
 * 快慢指针
 */
public class _003_DeleteArraysRepeatNum {

    public static void main(String[] args) {
        System.out.println(doublePtr(new int[]{0, 1, 2, 3, 3, 3, 4, 5}));
    }

    // 快慢指针
    public static int doublePtr(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int i = 0; // 慢指针
        for (int j = 1; j < nums.length; j++) {
            // j 快指针
            if (nums[i] != nums[j]) {
                // 当 nums[i] 与 nums[j] 不同时
                // 将 j 位置的数赋值给 i 位置
                // 并一起向后移动
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }
}
