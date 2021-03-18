public class Solution03 {

    /**
     * 35. 搜索插入位置
     */
    public int searchInsert(int[] nums, int target) {
        int len = nums.length;
        int left = 0;
        int rigth = len - 1;
        while (left <= rigth) {
            int mid = (rigth + left) / 2;
            if (target <= nums[mid]) {
                rigth = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * 38. 外观数列
     */

    public String countAndSay(int n) {
        // 循环
//        StringBuilder res = new StringBuilder();
//        res.append(1);
//        for (int i = 1; i < n; ++i) {
//            StringBuilder s = new StringBuilder();
//            int start = 0;
//            for (int j = 1; j < res.length(); j++) {
//                // 当第 j 个字符与开始下标不同时,拼接
//                if (res.charAt(j) != res.charAt(start)) {
//                    // 拼接 j-start 个 res.charAt(start)
//                    s.append(j - start).append(res.charAt(start));
//                    // 下一次拼接的开始下标
//                    start = j;
//                }
//            }
//            // 拼接最后一个数字
//            s.append(res.length() - start).append(res.charAt(start));
//            res = s;
//        }
//        return res.toString();

        // 递归
        // 递归终止条件
        if (n == 1) {
            return "1";
        }
        // 获取上一层的字符串
        String s = countAndSay(n - 1);
        StringBuilder res = new StringBuilder();
        int start = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != s.charAt(start)) {
                res.append(i - start).append(s.charAt(start));
                start = i;
            }
        }
        res.append(s.length() - start).append(s.charAt(start));
        return res.toString();
    }

    /**
     * 53. 最大子序和
     */
    public int maxSubArray(int[] nums) {
        // int pre=0;
        // int ret=nums[0];
        // for(int x:nums){
        //     pre=Math.max(pre+x,x);
        //     ret=Math.max(ret,pre);
        // }
        // return ret;

        int len = nums.length;
        int ret = nums[0];
        for (int i = 1; i < len; ++i) {
            if (nums[i - 1] > 0) {
                nums[i] = nums[i] + nums[i - 1];
            }
            ret = Math.max(ret, nums[i]);
        }
        return ret;
    }

    /**
     * 58. 最后一个单词的长度
     */
    public int lengthOfLastWord(String s) {
        if (s.equals(" ")) {
            return 0;
        }
        int len = s.length();
        char[] chars = s.toCharArray();
        int ret = 0;
        boolean flag = false;
        for (int i = len - 1; i >= 0; i--) {
            if (chars[i] != ' ') {
                flag = true;
                ret++;
            } else {
                if (flag)
                    return ret;
            }
        }
        return ret;
    }

    /**
     * 66. 加一
     */
    public int[] plusOne(int[] digits) {
        int len = digits.length;
        for (int i = len - 1; i >= 0; i--) {
            digits[i]++;
            // 模为0说明发生了进位,否则直接返回
            digits[i] = digits[i] % 10;
            if (digits[i] != 0)
                return digits;
        }
        // 运行到这说明首位为1,其余为0, 99->100
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }

    /**
     * 67. 二进制求和
     */
    public String addBinary(String a, String b) {
        // return Integer.toBinaryString(
        //     Integer.parseInt(a,2)+Integer.parseInt(b,2)
        // );

        // int i=a.length()-1;
        // int j=b.length()-1;
        // StringBuilder ret=new StringBuilder();
        // int carry=0;
        // while(i>=0 && j>=0){
        //     int sum=carry;
        //     sum+=a.charAt(i--)-'0';
        //     sum+=b.charAt(j--)-'0';
        //     // 是否进位 2,3 进位
        //     carry=sum/2;
        //     ret.append(sum%2);
        // }
        // while(i>=0){
        //     int sum=a.charAt(i--)-'0'+carry;
        //     carry=sum/2;
        //     ret.append(sum%2);
        // }
        // while(j>=0){
        //     int sum=b.charAt(j--)-'0'+carry;
        //     carry=sum/2;
        //     ret.append(sum%2);
        // }
        // if(carry==1){
        //     ret.append(1);
        // }
        // // 反转
        // return ret.reverse().toString();

        StringBuilder ret = new StringBuilder();
        int alen = a.length();
        int blen = b.length();
        char[] as = a.toCharArray();
        char[] bs = b.toCharArray();
        int len = Math.max(alen, blen);
        int carry = 0;
        for (int i = 0; i < len; i++) {
            carry += i < alen ? (as[alen - 1 - i] - '0') : 0;
            carry += i < blen ? (bs[blen - 1 - i] - '0') : 0;
            ret.append(carry % 2);
            carry /= 2;
        }
        if (carry == 1) {
            ret.append(1);
        }
        return ret.reverse().toString();
    }

    /**
     * 69. x 的平方根
     */
    public int mySqrt(int x) {
        // boolean flag=true;
        // long count=0;
        // while(flag){
        //     if((count*count<=x) && ((count+1)*(count+1)>x)){
        //         return (int)count==count? (int)count:Integer.MAX_VALUE;
        //     }
        //     count++;
        // }
        // return 0;

        // 二分法
        // int left=0;
        // int right=x;
        // while(left<=right){
        //     int mid=left+(right-left)/2;
        //     if((long)mid*mid <= x){
        //        left=mid+1;
        //     }else{
        //         right=mid-1;
        //     }
        // }
        // return left-1;

        // 牛顿迭代法
        long n = x;
        while (n * n > x) {
            n = (n + x / n) / 2;
        }
        return (int) n;
    }

    /**
     * 70. 爬楼梯
     */
    public int climbStairs(int n) {
        // 普通动态规划 O(n) O(n)
        // if(n==1){
        //     return 1;
        // }
        // int [] ret=new int[n+1];
        // ret[1]=1;
        // ret[2]=2;
        // int i=3;
        // while(i<=n){
        //     ret[i]=ret[i-1]+ret[i-2];
        //     i++;
        // }
        // return ret[n];

        // 滑动数组
        int p = 0, q = 0, ret = 1;
        for (int i = 1; i <= n; ++i) {
            p = q;
            q = ret;
            ret = p + q;
        }
        return ret;
    }

}
