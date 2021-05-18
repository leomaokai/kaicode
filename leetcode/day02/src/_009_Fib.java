/**
 * 斐波那契数列 ( 0 1 1 2 3 ...)
 */
public class _009_Fib {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(calculate(40));
//        System.out.println(dpCalculate(40));
//        System.out.println(calculate3(40));
        System.out.println(System.currentTimeMillis() - start);
    }


    // 暴力递归 O(2^n)
    public static int calculate(int num) {
        if (num == 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }

        return calculate(num - 1) + calculate(num - 2);
    }

    // 动态规划 O(n)
    public static int dpCalculate(int num) {
        int[] dp = new int[num + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= num; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[num];
    }

    public static int calculate3(int num) {
        int res = 0;
        int first = 0;
        int second = 1;
        for (int i = 2; i <= num; i++) {
            res = first + second;
            first = second;
            second = res;
        }
        return res;
    }
}
