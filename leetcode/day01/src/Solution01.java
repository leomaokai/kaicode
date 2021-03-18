import java.util.ArrayList;
import java.util.List;

public class Solution01 {

    /**
     * 0004
     */
    public double findMediaSortedArrays(int[] nums1, int[] nums2) {

        // 设置第一个数组为短的数组
        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        int m = nums1.length;
        int n = nums2.length;

        // 分割线左边的所有元素需要满足的个数 m+(n-m+1)/2
        int totalLeft = m + (n - m + 1) / 2;  // (m+n+1)/2

        // i 为nums1分割线左边数的下标,此时nums1左边恰好 i 个数,j同理
        // 在nums1 的区间 [0,m] 里查找恰当的分割线
        // 使得 nums1[i-1] <= nums2[j] && nums2[j-1] <= nums1[i]

        // left 和 right 为二分法查找的范围
        int left = 0;
        int right = m;

        while (left < right) {
//            int i = left + (right - left + 1) / 2;
//            int j = totalLeft - i;
//            if (nums1[i - 1] > nums2[j]) {
//                // i 大了,向左缩小区间 [left, i + 1]
//                right = i - 1;
//            } else {
//                // 下一轮搜索区间 [i, right]
//                left = i;
//            }
            int i = left + (right - left) / 2;
            int j = totalLeft - i;
            if (nums2[j - 1] > nums1[i]) {
                // i 小了 [i + 1, right]
                left = i + 1;
            } else {
                // 下一轮搜索区间 [left, i]
                right = i;
            }
        }

        int i = left;
        int j = totalLeft - i;
        // 取得分割线左右两边的值,并对特殊情况进行分析
        int nums1LeftMax = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
        int nums1RightMin = i == m ? Integer.MAX_VALUE : nums1[i];
        int nums2LeftMax = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
        int nums2RightMin = j == n ? Integer.MAX_VALUE : nums2[j];

        if (((m + n) % 2) == 1) {
            return Math.max(nums1LeftMax, nums2LeftMax);
        } else {
            return (double) (Math.max(nums1LeftMax, nums2LeftMax) + Math.min(nums1RightMin, nums2RightMin)) / 2;
        }

    }

    /**
     * 0005
     */
    public String longestPalindrome(String s) {
        // 暴力枚举
//        int length = s.length();
//        if (length < 2) {
//            return s;
//        }
//        int begin = 0;
//        int maxLen = 1;
//        char[] chars = s.toCharArray();
//        for (int i = 0; i < length - 1; ++i) {
//            for (int j = i + 1; j < length; ++j) {
//                if (j - i + 1 > maxLen && isPalindromic(chars, i, j)) {
//                    maxLen = j - 1 + 1;
//                    begin = i;
//                }
//            }
//        }
//        return s.substring(begin, begin + maxLen);

        // 动态规划
//        int len = s.length();
//        if (len < 2) {
//            return s;
//        }
//        char[] chars = s.toCharArray();
//        int begin = 0;
//        int maxLen = 1;
//        // dp[i][j] 表示 s[i...j] 是回文
//        boolean[][] dp = new boolean[len][len];
//        // 对角线一定是true
//        for (int i = 0; i < len; i++) {
//            dp[i][i] = true;
//        }
//        for (int j = 1; j < len; j++) {
//            for (int i = 0; i < j; i++) {
//                if (chars[i] != chars[j]) {
//                    dp[i][j] = false;
//                } else {
//                    if (j - i < 3) {
//                        dp[i][j] = true;
//                    } else {
//                        dp[i][j] = dp[i + 1][j - 1];
//                    }
//                }
//                if (dp[i][j] && j - i + 1 > maxLen) {
//                    maxLen = j - i + 1;
//                    begin = i;
//                }
//            }
//        }
//        return s.substring(begin, begin + maxLen);

        // 中心扩散  将每一个字符作为回文的中心进行判断
        int len = s.length();
        if (len < 2) {
            return s;
        }
        char[] chars = s.toCharArray();
        int begin = 0;
        int maxLen = 1;
        for (int i = 0; i < len - 1; i++) {
            int oddLen = expandAroundCenter(chars, i, i);
            int evenLen = expandAroundCenter(chars, i, i + 1);

            int curMaxLen = Math.max(oddLen, maxLen);
            if (curMaxLen > maxLen) {
                // 起始下标与中心字符下标和回文长度有关
                begin = i - (maxLen - 1) / 2;
            }
        }
        return s.substring(begin, maxLen + begin);

    }

    // 中心扩散判断是否是回文
    private int expandAroundCenter(char[] chars, int left, int right) {
        // 当 left = right 时,回文中心是一个字符,回文串长度是奇数
        // 当 right = left+1 时,回文中心两个字符,回文长度是偶数
        int len = chars.length;
        int i = left;
        int j = right;
        while (i >= 0 && j < len) {
            if (chars[i] == chars[j]) {
                i--;
                j++;
            } else {
                break;
            }
        }
        // 跳出 while 循环时,此时两个字符不同,回文长度为 j-i+1-2
        return j - i - 1;
    }

    // 暴力枚举判断是否是回文
    private boolean isPalindromic(char[] chars, int i, int j) {
        while (i < j) {
            if (chars[i] != chars[j]) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    /**
     * 0006
     */
    public String convertZ(String s, int numRows) {
        int len = s.length();
        if (len < 2 || numRows == 1) {
            return s;
        }
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(len, numRows); i++) {
            rows.add(new StringBuilder());
        }
        boolean flag = false;
        int row = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < len; ++i) {
            if (row == numRows - 1) {
                flag = false;
            } else if (row == 0) {
                flag = true;
            }
            rows.get(row).append(chars[i]);
            row += flag ? 1 : -1;
        }
        StringBuilder ret = new StringBuilder();
        for (StringBuilder sb : rows) {
            ret.append(sb);
        }
        return ret.toString();
    }

    /**
     * 0007
     */
    public int reverse7(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            // 关键是判断是否溢出
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;

//        long n = 0;
//        while(x != 0) {
//            n = n*10 + x%10;
//            x = x/10;
//        }
//        return (int)n==n? (int)n:0;
    }

    /**
     * 0008
     */
    public int myAtoi(String s) {
        // 去掉首尾空格
        s = s.trim();
        int len = s.length();
        if (len == 0)
            return 0;
        char[] chars = s.toCharArray();
        // 判断第一个字符是否为数字或是+-,否则返回0
        if (!Character.isDigit(chars[0]) && chars[0] != '-' && chars[0] != '+')
            return 0;
        int ret = 0;
        // 第一个字符是'-' flag 为true
        boolean flag = chars[0] == '-';
        // 第一个字符是数字的话,下标从0开始,否则从1开始
        int i = Character.isDigit(chars[0]) ? 0 : 1;
        while (i < len && Character.isDigit(chars[i])) {
            // 如果是负数,比较 MAX_VALUE / 10 + 8
            // 正数,比较 MAX_VALUE / 10 + 7
            // -2147483648 2147483647
            if (ret > Integer.MAX_VALUE / 10 || (ret == Integer.MAX_VALUE / 10 && (chars[i] - '0') > (flag ? 8 : 7))) {
                return flag ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            ret = ret * 10 + (chars[i++] - '0');
        }
        return flag ? -ret : ret;
    }

    /**
     * 0009
     */
    public boolean isPalindrome(int x) {
        // 直接算
        // if(x<0){
        //     return false;
        // }
        // int rem=0;
        // int len=0;
        // List<Integer> list=new ArrayList<>();
        // while(x>0){
        //     rem=x%10;
        //     list.add(rem);
        //     len++;
        //     x/=10;
        // }
        // for(int i=0;i<len;++i){
        //     if(list.get(i)!=list.get(len-i-1)){
        //         return false;
        //     }
        // }
        // return true;

        // 转字符串
        if (x < 0) {
            return false;
        }
        String s = String.valueOf(x);
        int len = s.length();
        char[] chars = s.toCharArray();
        for (int i = 0; i < len; ++i) {
            if (chars[i] != chars[len - i - 1]) {
                return false;
            }
        }
        return true;
        // if(x<0){
        //     return false;
        // }
        // String s=String.valueOf(x);
        // int len=s.length();
        // for(int i=0 ;i<len;++i){
        //     if(s.charAt(i)!=s.charAt(len-i-1)){
        //         return false;
        //     }
        // }
        // return true;
    }

    /**
     * 13. 罗马数字转整数
     */
    public int romanToInt(String s) {
        char[] chars = s.toCharArray();
        List<Integer> list = new ArrayList<>();
        int ret = 0;
        for (char c : chars) {
            switch (c) {
                case 'I':
                    list.add(1);
                    break;
                case 'V':
                    list.add(5);
                    break;
                case 'X':
                    list.add(10);
                    break;
                case 'L':
                    list.add(50);
                    break;
                case 'C':
                    list.add(100);
                    break;
                case 'D':
                    list.add(500);
                    break;
                case 'M':
                    list.add(1000);
                    break;
            }
        }
        for (int i = 0; i < list.size() - 1; ++i) {
            if (list.get(i) < list.get(i + 1)) {
                ret -= list.get(i);
            } else {
                ret += list.get(i);
            }
        }
        return ret += list.get(list.size() - 1);
    }
}
