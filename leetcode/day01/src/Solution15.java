import java.util.ArrayList;
import java.util.List;

public class Solution15 {

    /**
     * 29. 两数相除
     * 位运算
     */
    public int divide(int dividend, int divisor) {
        if (dividend == 0) {
            return 0;
        }
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        boolean negative;
        negative = (dividend ^ divisor) < 0; // 异或计算符号是否相异
        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);
        int res = 0;
        for (int i = 31; i >= 0; i--) {
            // 寻找一个 2^i 使 dividend/2^i >= divisor
            if ((absDividend >> i) >= absDivisor) {
                // 结果加上 2^i
                res += 1 << i;
                // dividend 减去 2^i 个 divisor
                absDividend -= absDivisor << i;
            }
        }
        return negative ? -res : res;
    }

    /**
     * 36. 有效的数独
     */
    public boolean isValidSudoku(char[][] board) {
        int[][] row = new int[9][9];
        int[][] col = new int[9][9];
        int[][] sub = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int num = board[i][j] - '1';
                    if (row[i][num] == 1) {
                        return false;
                    } else {
                        row[i][num] = 1;
                    }
                    if (col[j][num] == 1) {
                        return false;
                    } else {
                        col[j][num] = 1;
                    }
                    // 子数组的索引计算方法
                    int subIndex = (i / 3) * 3 + j / 3;
                    if (sub[subIndex][num] == 1) {
                        return false;
                    } else {
                        sub[subIndex][num] = 1;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 50. Pow(x, n)
     * O(logn) 二进制迭代
     */
    public double myPow(double x, int n) {
        // 递归
        // return n>0? quickPow(x,n):1/quickPow(x,n);

        // 二进制迭代
        long longN = n;
        if (x == 0) {
            return 0;
        }
        if (longN < 0) {
            longN *= -1;
            x = 1 / x;
        }
        double res = 1;
        double weigth = x;
        while (longN != 0) {
            if ((longN & 1) == 1) {
                res *= weigth;
            }
            weigth *= weigth;
            longN >>= 1;
        }
        return res;
    }

    // private double quickPow(double x,int n){
    //     if(n==0){
    //         return 1.0;
    //     }
    //     double y = quickPow(x,n/2);
    //     return n%2==0? y*y:y*y*x;
    // }

    /**
     * 54. 螺旋矩阵
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        List<Integer> res = new ArrayList<>();
        int left = 0;
        int right = colLen - 1;
        int top = 0;
        int bottom = rowLen - 1;
        int total = rowLen * colLen;
        while (total > 0) {
            for (int i = left; i <= right && total > 0; i++) {
                res.add(matrix[top][i]);
                total--;
            }
            top++;
            for (int i = top; i <= bottom && total > 0; i++) {
                res.add(matrix[i][right]);
                total--;
            }
            right--;
            for (int i = right; i >= left && total > 0; i--) {
                res.add(matrix[bottom][i]);
                total--;
            }
            bottom--;
            for (int i = bottom; i >= top && total > 0; i--) {
                res.add(matrix[i][left]);
                total--;
            }
            left++;
        }
        return res;
    }

    /**
     * 73. 矩阵置零
     */
    public void setZeroes(int[][] matrix) {
        int rowLen = matrix.length;
        int colLen = matrix[0].length;

        boolean rowFlag = false;
        boolean colFlag = false;
        for (int i = 0; i < rowLen; i++) {
            if (matrix[i][0] == 0) {
                colFlag = true;
                break;
            }
        }
        for (int i = 0; i < colLen; i++) {
            if (matrix[0][i] == 0) {
                rowFlag = true;
                break;
            }
        }
        for (int i = 1; i < rowLen; i++) {
            for (int j = 1; j < colLen; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < rowLen; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 0; j < colLen; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (int i = 1; i < colLen; i++) {
            if (matrix[0][i] == 0) {
                for (int j = 0; j < rowLen; j++) {
                    matrix[j][i] = 0;
                }
            }
        }
        if (colFlag) {
            for (int i = 0; i < rowLen; i++) {
                matrix[i][0] = 0;
            }
        }
        if (rowFlag) {
            for (int i = 0; i < colLen; i++) {
                matrix[0][i] = 0;
            }
        }
    }


}
