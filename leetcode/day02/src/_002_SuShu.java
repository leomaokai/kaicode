/**
 * 统计 n 以内的素数的个数
 * 只能被 1 和自身整除的自然数, 0,1 除外
 * 埃筛法
 */
public class _002_SuShu {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        System.out.println(bf(10000));
        System.out.println(eratosthenes(10000));
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    // 暴力算法
    public static int bf(int n) {
        int count = 0;
        for (int i = 2; i < n; i++) {
            count += isPrime(i) ? 1 : 0;
        }
        return count;
    }

    private static boolean isPrime(int x) {
        // 不必要遍历整个 x ( i < x )
        // 只需要到 根号x  ( i * i <= x )
        for (int i = 2; i * i <= x; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    // 埃筛法
    // 非素数(合数)  12 = 2 * 6
    // 2为素数,3为素数,则 2*3 为合数
    public static int eratosthenes(int n) {
        int count = 0;
        boolean[] isNoPrime = new boolean[n]; // 默认为false为素数
        for (int i = 2; i < n; i++) {
            if (!isNoPrime[i]) {
                count++;
//                for (int j = 2 * i; j < n; j += i) {
//                    isNoPrime[j] = true;
//                }
                for (int j = i * i; j < n; j += i) {
                    // j 为合数的标记位 (从 i*i 开始标记,前面已经标记过了)
                    isNoPrime[j] = true;
                }
            }
        }
        return count;
    }

}
