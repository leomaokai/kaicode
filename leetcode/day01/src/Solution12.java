import java.util.*;

public class Solution12 {

    /**
     * 990
     * <br>
     * "并查集"用于判断一对元素是否相连,它们的关系是动态添加的,这一类问题叫做"动态连通性"问题
     * <br>
     * 主要支持"合并"和"查询是否在同一个集合"操作
     * <br>
     * 底层结构是"数组"或者"哈希表",用户表示"结点"指向的"父结点",初始化时指向自己
     * <br>
     * "合并"就是把一个集合的根结点指向另一个集合的根结点,只要根结点一样,就表示在同一个集合里
     * <br>
     * 应用: 最小生成树:kruskal算法
     */
    public boolean equationsPossible(String[] equations) {
        UnionFind unionFind = new UnionFind(26);
        for (String equation : equations) {
            char[] charArray = equation.toCharArray();
            if (charArray[1] == '=') {
                int index1 = charArray[0] - 'a';
                int index2 = charArray[3] - 'a';
                unionFind.union(index1, index2);
            }
        }

        for (String equation : equations) {
            char[] charArray = equation.toCharArray();
            if (charArray[1] == '!') {
                int index1 = charArray[0] - 'a';
                int index2 = charArray[3] - 'a';
                if (unionFind.isConnected(index1, index2)) {
                    return false;
                }
            }
        }
        return true;
    }

    // 并查集
    private class UnionFind {
        private int[] parent;

        public UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            while (x != parent[x]) {
                parent[x] = parent[parent[x]];
                x = parent[x];
            }
            return x;
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            // 两个结点合并,将 x 指向 y
            parent[rootX] = rootY;
        }

        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }
    }

    /**
     * 399. 除法求值
     * 并查集
     */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        int equationsSize = equations.size();
        UnionFind2 unionFind2 = new UnionFind2(2 * equationsSize);
        // 1.预处理,将变量的值与 id 进行映射,使得并查集底层使用数组实现,方便编码
        Map<String, Integer> hasMap = new HashMap<>(2 * equationsSize);
        // 变量 id
        int id = 0;
        for (int i = 0; i < equationsSize; i++) {
            List<String> equation = equations.get(i);
            String var1 = equation.get(0);
            String var2 = equation.get(1);
            if (!hasMap.containsKey(var1)) {
                hasMap.put(var1, id);
                id++;
            }
            if (!hasMap.containsKey(var2)) {
                hasMap.put(var2, id);
                id++;
            }
            // 合并变量1和变量2,以及其权值
            unionFind2.union(hasMap.get(var1), hasMap.get(var2), values[i]);
        }
        // 2.做查询
        int queriesSize = queries.size();
        double[] res = new double[queriesSize];
        for (int i = 0; i < queriesSize; i++) {
            String var1 = queries.get(i).get(0);
            String var2 = queries.get(i).get(1);
            Integer id1 = hasMap.get(var1);
            Integer id2 = hasMap.get(var2);
            if (id1 == null || id2 == null) {
                res[i] = -1.0d;
            } else {
                res[i] = unionFind2.isConnected(id1, id2);
            }
        }
        return res;
    }

    private class UnionFind2 {
        private int[] parent;
        // 结点指向父结点的权值
        private double[] weight;

        public UnionFind2(int n) {
            parent = new int[n];
            weight = new double[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                weight[i] = 1.0d;
            }
        }

        public void union(int x, int y, double value) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) {
                return;
            }

            parent[rootX] = rootY;
            weight[rootX] = weight[y] * value / weight[x];
        }

        // 路径压缩
        public int find(int x) {
            if (x != parent[x]) {
                int origin = parent[x];
                // 递归找根
                parent[x] = find(parent[x]);
                weight[x] *= weight[origin];
            }
            return parent[x];
        }

        public double isConnected(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY) {
                return weight[x] / weight[y];
            } else {
                return -1.0d;
            }
        }
    }

    /**
     * 406. 根据身高重建队列
     */
    public int[][] reconstructQueue(int[][] people) {
        // 从低到高
        // 按hi升序,按ki降序
        // 若h1=h2,k1>k2,则把h1排前面,相当于还是按身高升序,h1稍微小于h2
        // Arrays.sort(people,new Comparator<int[]>(){
        //     public int compare(int[] person1,int[] person2){
        //         if(person1[0]!=person2[0]){
        //             return person1[0]-person2[0];
        //         }else{
        //             return person2[1]-person1[1];
        //         }
        //     }
        // });
        // int len=people.length;
        // int[][] ans=new int[len][];
        // for(int[] person:people){
        //     // 得到该人在队列的位置
        //     int spaces=person[1]+1;
        //     for(int i=0;i<len;++i){
        //         // 某一位置为空
        //         if(ans[i]==null){
        //             --spaces;
        //             if(spaces==0){
        //                 ans[i]=person;
        //                 break;
        //             }
        //         }
        //     }
        // }
        // return ans;

        // 从高到低
        // hi 降序 , ki 升序
        Arrays.sort(people, new Comparator<int[]>() {
            public int compare(int[] person1, int[] person2) {
                if (person1[0] != person2[0]) {
                    return person2[0] - person1[0];
                } else {
                    return person1[1] - person2[1];
                }
            }
        });
        List<int[]> ans = new ArrayList<int[]>();
        for (int[] person : people) {
            // 将person插入person[1]的位置
            ans.add(person[1], person);
        }
        return ans.toArray(new int[ans.size()][]);
    }

    /**
     * 416. 分割等和子集
     * <p>
     * 01背包问题
     * <p>
     * 在 M 件物品取出若干件放在体积为 W 的背包里,每件物品只有一件,
     * <p>
     * 它们有各自的体积和价值,问如何选择使得背包能装下的物品的价值最多.
     * <p>
     * 一个一个物品去尝试,一点一点扩大考虑能够容纳的容积的大小,
     * <p>
     * 整个过程就像是在填写一张二位表格.
     */
    public boolean canPartition(int[] nums) {
//        int len = nums.length;
//        int sum = 0;
//        for (int num : nums) {
//            sum += num;
//        }
//        if ((sum & 1) == 1) {
//            return false;
//        }
//
//        int target = sum / 2;
//        // dp[i][j] 表示 i 能否填满 j 的容量
//        // 在 0~i 中选出一些数,使这些数之和恰好等于 j
//        boolean[][] dp = new boolean[len][target + 1];
//        if (nums[0] <= target) {
//            dp[0][nums[0]] = true;
//        }
//
//        for (int i = 1; i < len; i++) {
//            for (int j = 0; j <= target; j++) {
//                dp[i][j] = dp[i - 1][j];
//                if (nums[i] == j) {
//                    dp[i][j] = true;
//                    continue;
//                }
//                if (nums[i] < j) {
//                    // 不考虑 nums[i] 或 考虑 nums[i]
//                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
//                }
//            }
//            if (dp[i][target]) {
//                return true;
//            }
//        }
//        return dp[len - 1][target];

        // 01背包优化
        int len = nums.length;
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if ((sum & 1) == 1) {
            return false;
        }
        int target = sum / 2;
        // 某行的数据只与它的上一行有关,所以可以用一维数组保存
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        if (nums[0] <= target) {
            dp[nums[0]] = true;
        }
        for (int i = 1; i < len; i++) {
            // 逆序循环, dp[j] 要先于 dp[j - nums[i]] 更新
            // dp[j] 要使用旧值更新
            for (int j = target; j >= 0 && nums[i] <= j; j--) {
                dp[j] = dp[j] || dp[j - nums[i]];
            }
            if (dp[target]) {
                return true;
            }
        }
        return dp[target];
    }

}
