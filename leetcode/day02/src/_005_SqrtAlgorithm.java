/**
 * x的平方根
 * 在不适用sqrt(x)函数的情况下,得到 x 的平方根的整数部分
 * 二分,牛顿迭代
 */
public class _005_SqrtAlgorithm {

    public static void main(String[] args) {
        System.out.println(binarySearch(6));
        System.out.println(bf(6));
        System.out.println(newton(6));
    }


    // 暴力
    public static int bf(int x) {
        int res = -1;
        for (int i = 0; i < x; i++) {
            if (i * i <= x && (i + 1) * (i + 1) > x) {
                res = i;
            }
        }
        return res;
    }


    // 二分查找
    public static int binarySearch(int x) {
        int index = -1;
        int left = 0;
        int right = x;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mid * mid <= x) {
                left = mid + 1;
                index = mid;
            } else if (mid * mid > x) {
                right = mid - 1;
            }
        }
        return index;
    }

    // 牛顿迭代 求 n 的平方根 x
    // 因为 x * x = n 则 x = n / x;
    // x 和 n / x 相加求平均更加接近最终的结果 (x + n/x) / 2
    public static int newton(int n) {
        if (n == 0) {
            return 0;
        }
        return (int) recursionSqrt(n, n);
    }

    private static double recursionSqrt(double i, int n) {
        double res = (i + n / i) / 2;
        if (res == i) {
            return i;
        } else {
            return recursionSqrt(res, n);
        }
    }
}
