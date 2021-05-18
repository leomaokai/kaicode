/**
 * 排列硬币
 * 总共有 n 枚硬币,将它们摆成一个阶梯形状,第 k 行就必须正好有 k 枚硬币
 * 求出可形成完整阶梯行的总行数
 */
public class _010_ArrangeCoin {

    public static void main(String[] args) {
        System.out.println(arrangeCoins1(10000));
        System.out.println(arrangeCoins2(10000));
        System.out.println(arrangeCoins3(10000));
    }

    // 暴力
    public static int arrangeCoins1(int n) {
        for (int i = 1; i <= n; i++) {
            n -= i;
            if (n <= i) {
                return i;
            }
        }
        return 0;
    }

    // 二分查找
    public static int arrangeCoins2(int n) {
        int low = 0;
        int high = n;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            //到 mid 行所需的硬币,等差求和
            int cost = ((mid + 1) * mid) / 2;
            if (cost == n) {
                return mid;
            } else if (cost < n) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return high;
    }

    // 牛顿迭代
    // x 行所需硬币时的等差求和公式 : x^2 + x = 2*n
    // 求 x
    public static int arrangeCoins3(int n) {
        if (n == 0) {
            return 0;
        }
        return (int) sqrt(n, n);
    }

    private static double sqrt(double x, int n) {
        double res = (x + (2 * n - x) / x) / 2;
        if (res == x) {
            return x;
        } else {
            return sqrt(res, n);
        }
    }
}
