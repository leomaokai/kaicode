import java.util.Arrays;

/**
 * 合并两个有序数组
 * 两个有序整数数组 nums1 和 nums2, 将 nums2 合并到 nums1 中
 * 使 nums1 成为一个有序数组
 * 初始化 nums1 和 nums2 的元素数量分别为 m 和 n
 * 假设 nums1 的空间大小等于 m + n, 这样有足够的空间保存 nums2 的元素
 * 双指针
 */
public class _012_MergeOrderArray {

    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 3, 5, 7, 9, 0, 0, 0, 0};
        int[] nums2 = new int[]{2, 4, 6, 8};
//        System.out.println(Arrays.toString(merge1(nums1, 5, nums2, 4)));
//        System.out.println(Arrays.toString(merge2(nums1, 5, nums2, 4)));
        System.out.println(Arrays.toString(merge3(nums1, 5, nums2, 4)));

    }

    // java api  拷贝+排序
    public static int[] merge1(int[] nums1, int m, int[] nums2, int n) {
        // nums2 从 0 开始拷贝到 nums1 m位置开始拷贝n个元素
        System.arraycopy(nums2, 0, nums1, m, n);
        Arrays.sort(nums1);

        return nums1;
    }

    // 双指针 借用数组
    public static int[] merge2(int[] nums1, int m, int[] nums2, int n) {
        int[] tempArrays = new int[m];
        System.arraycopy(nums1, 0, tempArrays, 0, m);
        int p1 = 0, p2 = 0, i = 0;
        while (p1 < m && p2 < n) {
            nums1[i++] = tempArrays[p1] < nums2[p2] ?
                    tempArrays[p1++] : nums2[p2++];
        }
        while (p1 < m) {
            nums1[i++] = tempArrays[p1++];
        }
        while (p2 < n) {
            nums1[i++] = nums2[p2++];
        }
        return nums1;
    }

    // 优化 从后往前遍历,不再使用额外数组
    public static int[] merge3(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p = m + n - 1;
        while (p1 >= 0 && p2 >= 0) {
            nums1[p--] = nums1[p1] > nums2[p2] ? nums1[p1--] : nums2[p2--];
        }
        while (p1 >= 0) {
            nums1[p--] = nums1[p1--];
        }
        while (p2 >= 0) {
            nums1[p--] = nums2[p2--];
        }
        return nums1;
    }
}
