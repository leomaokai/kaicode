import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Solution14 {

    /**
     * 621. 任务调度器
     * 贪心策略
     */
    public int leastInterval(char[] tasks, int n) {
        // // 哈希表记录任务名和执行次数
        // Map<Character,Integer> freq=new HashMap<Character,Integer>();
        // for(char ch:tasks){
        //     freq.put(ch,freq.getOrDefault(ch,0)+1);
        // }
        // int m=freq.size();
        // // 任务下一次执行的时间
        // List<Integer> nextValid = new ArrayList<>();
        // // 记录剩余执行次数
        // List<Integer> rest = new ArrayList<>();
        // for(Map.Entry<Character,Integer> entry:freq.entrySet()){
        //     nextValid.add(1);
        //     rest.add(entry.getValue());
        // }
        // // time 当前时间
        // int time=0;
        // for(int i=0;i<tasks.length;i++){
        //     ++time;
        //     int minNextValid = Integer.MAX_VALUE;
        //     // 得到下一次执行任务的时间 minNextValid
        //     for(int j=0;j<m;++j){
        //         if(rest.get(j)!=0){
        //             minNextValid=Math.min(minNextValid,nextValid.get(j));
        //         }
        //     }
        //     time = Math.max(time,minNextValid);
        //     int best = -1;
        //     // 得到剩余次数最多的任务
        //     // 优先执行剩余次数最多的任务
        //     for(int j=0;j<m;j++){
        //         if(rest.get(j)!=0 && nextValid.get(j)<=time){
        //             if(best==-1 || rest.get(j)>rest.get(best)){
        //                 best=j;
        //             }
        //         }
        //     }
        //     // 该任务下一次执行的时间
        //     nextValid.set(best,time+n+1);
        //     // 该任务剩余执行次数
        //     rest.set(best,rest.get(best)-1);
        // }
        // return time;

        // 贪心
        // Map<Character, Integer> freq = new HashMap<Character, Integer>();
        // // 最多的执行次数
        // int maxExec = 0;
        // for (char ch : tasks) {
        //     int exec = freq.getOrDefault(ch, 0) + 1;
        //     freq.put(ch, exec);
        //     // 得到最多的执行次数
        //     maxExec = Math.max(maxExec, exec);
        // }

        // // 具有最多执行次数的任务数量
        // int maxCount = 0;
        // for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
        //     int value = entry.getValue();
        //     if (value == maxExec) {
        //         ++maxCount;
        //     }
        // }
        // return Math.max((maxExec - 1) * (n + 1) + maxCount, tasks.length);

        // 贪心
        int[] buckets = new int[26];
        // 得到每个任务的次数
        for (int i = 0; i < tasks.length; i++) {
            buckets[tasks[i] - 'A']++;
        }
        // 对任务次数排序
        Arrays.sort(buckets);
        // 得到最多的次数
        int maxTimes = buckets[25];
        // 得到与最多任务次数一样的任务数
        int maxCount = 1;
        for (int i = 25; i >= 1; i--) {
            if (buckets[i] == buckets[i - 1])
                maxCount++;
            else
                break;
        }
        // 得到最短时间公式
        int res = (maxTimes - 1) * (n + 1) + maxCount;
        return Math.max(res, tasks.length);
    }

    /**
     * 647. 回文子串
     * 中心扩展/动态规划
     */
    public int countSubstrings(String s) {
        // int ans=0;
        // char[] chars=s.toCharArray();
        // int len=chars.length;
        // // 中心扩散
        // // 回文串分为奇数和偶数
        // // 每一个字符遍历两次
        // // 第一次为奇数,第二次偶数
        // for(int i=0;i<2*len-1;i++){
        //     // 中心左起始位置
        //     int left=i/2;
        //     // 中心右起始位置
        //     int right=i/2+i%2;
        //     while(left>=0 && right<len && chars[left]==chars[right]){
        //         left--;
        //         right++;
        //         ans++;
        //     }
        // }
        // return ans;

        // 动态规划
        int ans = 0;
        int len = s.length();
        // dp[i][j] 表示[i,j]的字符是否为回文子串
        boolean[][] dp = new boolean[len][len];
        char[] chars = s.toCharArray();
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                if (chars[i] == chars[j] && (j - i <= 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    ans++;
                }
            }
        }
        Deque<Integer> stack = new LinkedList<>();
        stack.push(1);
        stack.push(2);

        return ans;
    }

    /**
     * 739. 每日温度
     * 单调栈
     */
    public int[] dailyTemperatures(int[] T) {
        // 单调栈
        int len = T.length;
        int[] res = new int[len];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < len; i++) {
            while (!stack.isEmpty() && T[i] > T[stack.peek()]) {
                int topIndex = stack.pop();
                res[topIndex] = i - topIndex;
            }
            stack.push(i);
        }
        return res;
    }
}
