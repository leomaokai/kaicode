import java.util.Arrays;

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
     *
     */
}
