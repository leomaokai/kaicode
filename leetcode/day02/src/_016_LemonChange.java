/**
 * 柠檬水找零
 * 每一杯柠檬水 5 美元,顾客购买,一次买一杯
 * 每位顾客只买一杯柠檬水,然后付 5 美元, 10 美元或 20 美元
 * 必须给每个顾客正确找零
 * 一开始手头没有零钱
 * 如果能给每位顾客正确找零,返回 true,否则 false
 * 贪心算法(局部最优)
 */
public class _016_LemonChange {

    public static void main(String[] args) {
        System.out.println(solution(new int[]{5, 10, 5, 10}));
        System.out.println(solution(new int[]{5, 5, 20}));
        System.out.println(solution(new int[]{5, 5, 5, 5, 10, 20}));
    }


    // 5 -> 直接收下
    // 10 -> 找 5
    // 20 -> 找 10+5 或 5+5+5 但是要优先 10+5 ,因为 5 用得越少越好
    public static boolean solution(int[] nums) {
        int fiveCount = 0; // 记录 5 的数量
        int tenCount = 0; // 记录 10 的数量
        for (int num : nums) {
            if (num == 5) {
                fiveCount++;
            } else if (num == 10) {
                if (fiveCount == 0) {
                    return false;
                }
                fiveCount--;
                tenCount++;
            } else {
                // 优先使用 10+5
                if (tenCount > 0 && fiveCount > 0) {
                    tenCount--;
                    fiveCount--;
                } else if (fiveCount >= 3) {
                    fiveCount -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
