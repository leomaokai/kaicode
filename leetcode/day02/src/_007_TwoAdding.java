import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 * 给定一个整数数组,从数组中找出两个数满足相加之和等于目标数 target
 * 假设每个输入只对应唯一的答案,且不可以重复使用相同的元素
 * 返回两数下标
 * map 标记
 */
public class _007_TwoAdding {

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2, 3, 4, 5, 6};
        System.out.println(Arrays.toString(bfSolution(nums, 10)));
        System.out.println(Arrays.toString(hashMapSolution(nums, 10)));
    }


    // 暴力算法
    public static int[] bfSolution(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    // 使用 map
    public static int[] hashMapSolution(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }
}
