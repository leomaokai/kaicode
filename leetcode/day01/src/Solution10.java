import java.util.*;

public class Solution10 {

    /**
     * 142. 环形链表 II
     */
    public ListNode detectCycle(ListNode head) {
        // Set<ListNode> set=new HashSet<>();
        // while(head!=null){
        //     if(set.contains(head)){
        //         return head;
        //     }
        //     set.add(head);
        //     head=head.next;
        // }
        // return null;

        // 快慢指针
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }
            // 存在环
            if (fast == slow) {
                // 再创建一个指针
                ListNode p = head;
                // 当p与slow重叠时,即是回溯点
                while (p != slow) {
                    p = p.next;
                    slow = slow.next;
                }
                return p;
            }
        }
        return null;
    }

    /**
     * 146. LRU 缓存机制
     */
    class LRUCache {

        // 双向列表
        // 头节点表示最近访问的
        class DLinkedNode {
            int key;
            int value;
            DLinkedNode prev;
            DLinkedNode next;

            public DLinkedNode() {
            }

            public DLinkedNode(int _key, int _value) {
                key = _key;
                value = _value;
            }
        }

        // 哈希表
        private Map<Integer, DLinkedNode> cache = new HashMap<>();
        // 当前大小和容量
        private int size;
        private int capacity;
        // 头尾指针,方便插入和删除操作
        private DLinkedNode head, tail;

        public LRUCache(int capacity) {
            this.size = 0;
            this.capacity = capacity;
            head = new DLinkedNode();
            tail = new DLinkedNode();
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            // 通过哈希表定位
            DLinkedNode node = cache.get(key);
            if (node == null) {
                return -1;
            }
            // 将其移到头部
            moveToHead(node);
            return node.value;
        }

        public void put(int key, int value) {
            // 先通过哈希表定位,存在直接返回,将其移到头部
            DLinkedNode node = cache.get(key);
            if (node == null) {
                // 创建节点
                DLinkedNode newNode = new DLinkedNode(key, value);
                // 添加到哈希表
                cache.put(key, newNode);
                // 添加到头部
                addToHead(newNode);
                ++size;
                // 超出容量则删除尾部和哈希表
                if (size > capacity) {
                    DLinkedNode tail = removeTail();
                    cache.remove(tail.key);
                    --size;
                }
            } else {
                // 更新值
                node.value = value;
                moveToHead(node);
            }
        }

        // 双向列表的操作
        // 将节点添加到头节点
        private void addToHead(DLinkedNode node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        // 删除节点
        private void removeNode(DLinkedNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        // 将节点移到头节点
        private void moveToHead(DLinkedNode node) {
            // 先删除该节点
            removeNode(node);
            // 将节点添加到头节点
            addToHead(node);
        }

        // 超出容量删除尾节点
        // 返回该引用为了在哈希表中删除
        private DLinkedNode removeTail() {
            DLinkedNode res = tail.prev;
            removeNode(res);
            return res;
        }

    }

    /**
     * 148. 排序链表
     */
    public ListNode sortList(ListNode head) {
        // 自顶向下的归并排序
        return sortList(head, null);
    }

    private ListNode sortList(ListNode head, ListNode tail) {
        if (head == null) {
            return head;
        }
        if (head.next == tail) {
            head.next = null;
            return head;
        }
        // 快慢指针
        ListNode slow = head, fast = head;
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }
        ListNode mid = slow;
        ListNode list1 = sortList(head, mid);
        ListNode list2 = sortList(mid, tail);
        ListNode sorted = merge(list1, list2);
        return sorted;
    }

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode newHead = new ListNode();
        ListNode temp = newHead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                temp.next = l1;
                l1 = l1.next;
            } else {
                temp.next = l2;
                l2 = l2.next;
            }
            temp = temp.next;
        }
        temp.next = l1 == null ? l2 : l1;
        return newHead.next;
    }

    /**
     * 152. 乘积最大子数组
     */
    public int maxProduct(int[] nums) {
        int len = nums.length;
        // 维护一个最大值和最小值
        // 正数用最大值相乘
        // 负数用最小值相乘
        // 所以当前的最大值为这个数乘最小值或乘最大值或当前数
        int maxF = nums[0], minF = nums[0], ret = nums[0];
        for (int i = 1; i < len; ++i) {
            int mx = maxF, mn = minF;
            maxF = Math.max(mx * nums[i], Math.max(nums[i], mn * nums[i]));
            minF = Math.min(mn * nums[i], Math.min(nums[i], mx * nums[i]));
            ret = Math.max(ret, maxF);
        }
        return ret;
    }

    /**
     * 198. 打家劫舍
     */
    public int rob(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }
        if (len == 1) {
            return nums[0];
        }
        int first = nums[0];
        int second = Math.max(nums[0], nums[1]);
        for (int i = 2; i < len; ++i) {
            int s = second;
            second = Math.max(first + nums[i], second);
            first = s;
        }
        return second;
    }

    /**
     * 200. 岛屿数量
     * 深度优先搜索
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int row = grid.length;
        int col = grid[0].length;
        int ret = 0;
        // 对每一个 1 节点进行dfs
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    ret++;
                    dfs(grid, i, j, row, col);
                }
            }
        }
        return ret;
    }

    private void dfs(char[][] grid, int i, int j, int row, int col) {
        // 越界或当前为0则直接返回
        if (i < 0 || j < 0 || i >= row || j >= col || grid[i][j] == '0') {
            return;
        }
        // 遍历过当前节点则将其标为0
        grid[i][j] = '0';
        dfs(grid, i - 1, j, row, col);
        dfs(grid, i + 1, j, row, col);
        dfs(grid, i, j - 1, row, col);
        dfs(grid, i, j + 1, row, col);
    }

    /**
     * 207. 课程表
     * 图的遍历,入度,出度
     */
    // 邻接表
    // List<List<Integer>> edges;
    // int [] visited;
    // boolean valid=true;
    // public boolean canFinish(int numCourses, int[][] prerequisites) {
    //     edges=new ArrayList<>();
    //     for(int i=0;i<numCourses;++i){
    //         edges.add(new ArrayList<>());
    //     }
    //     visited=new int[numCourses];
    //     for(int[] info : prerequisites){
    //         // 存在出度表示该门课程为先修课程
    //         edges.get(info[1]).add(info[0]);
    //     }
    //     for(int i=0;i<numCourses && valid;++i){
    //         if(visited[i]==0){
    //             dfs(i);
    //         }
    //     }
    //     return valid;
    // }
    // // 深度优先遍历,存在出度说明是先修课程
    // private void dfs(int courseIndex){
    //     // 标记访问
    //     visited[courseIndex]=1;
    //     for(int v:edges.get(courseIndex)){
    //         if(visited[v]==0){
    //             dfs(v);
    //             if(!valid){
    //                 return;
    //             }
    //         }else if(visited[v]==1){
    //             // 出现重复访问,说明存在环,无法完成课程
    //             valid=false;
    //             return;
    //         }
    //     }
    //     // courseIndex 节点出度为0
    //     visited[courseIndex]=2;
    // }

    // 广度优先遍历,需要一个数组记录入度,入度为0说明是先修课程
    List<List<Integer>> edges;
    int[] indeg;

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        edges = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            edges.add(new ArrayList<>());
        }
        // 记录入度
        indeg = new int[numCourses];
        for (int[] info : prerequisites) {
            // 存邻接表
            edges.get(info[1]).add(info[0]);
            // 入度加1
            indeg[info[0]]++;
        }

        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < numCourses; ++i) {
            // 将入度为0的课程入队
            if (indeg[i] == 0) {
                queue.add(i);
            }
        }
        // 记录学习的课程数
        int studied = 0;
        while (!queue.isEmpty()) {
            // 出队表示修完该课程
            int u = queue.poll();
            studied++;
            // 将该节点作为入度的节点入度减一
            for (int v : edges.get(u)) {
                --indeg[v];
                // 入度减为0说明可以学习该课程了
                if (indeg[v] == 0) {
                    queue.add(v);
                }
            }
        }
        return studied == numCourses;
    }

    /**
     * 208. 实现 Trie (前缀树)
     */
    class Trie {

        Trie[] child = new Trie[26];
        boolean isEnd = false;

        /**
         * Initialize your data structure here.
         */
        public Trie() {

        }

        /**
         * Inserts a word into the trie.
         */
        public void insert(String word) {
            Trie t = find(word, true);
            t.isEnd = true;
        }

        /**
         * Returns if the word is in the trie.
         */
        public boolean search(String word) {
            Trie t = find(word, false);
            return t != null && t.isEnd;
        }

        /**
         * Returns if there is any word in the trie that starts with the given prefix.
         */
        public boolean startsWith(String prefix) {
            Trie t = find(prefix, false);
            return t != null;
        }

        private Trie find(String word, boolean insertMode) {
            Trie t = this;
            for (int i = 0; i < word.length(); ++i) {
                int index = word.charAt(i) - 'a';
                if (t.child[index] == null) {
                    if (insertMode) {
                        t.child[index] = new Trie();
                    } else {
                        return null;
                    }
                }
                t = t.child[index];
            }
            return t;
        }
    }

    /**
     * 前缀树模板
     */
    class TrieMode {
        // 标记是否是某个单词结尾
        private boolean is_string = false;

        private TrieMode next[] = new TrieMode[26];

        public TrieMode() {
        }

        //插入单词
        public void insert(String word) {
            TrieMode root = this;
            char w[] = word.toCharArray();
            for (int i = 0; i < w.length; ++i) {
                if (root.next[w[i] - 'a'] == null)
                    root.next[w[i] - 'a'] = new TrieMode();
                root = root.next[w[i] - 'a'];
            }
            root.is_string = true;
        }

        //查找单词
        public boolean search(String word) {
            TrieMode root = this;
            char w[] = word.toCharArray();
            for (int i = 0; i < w.length; ++i) {
                if (root.next[w[i] - 'a'] == null)
                    return false;
                root = root.next[w[i] - 'a'];
            }
            return root.is_string;
        }

        //查找前缀
        public boolean startsWith(String prefix) {
            TrieMode root = this;
            char p[] = prefix.toCharArray();
            for (int i = 0; i < p.length; ++i) {
                if (root.next[p[i] - 'a'] == null)
                    return false;
                root = root.next[p[i] - 'a'];
            }
            return true;
        }
    }

    /**
     * 215. 数组中的第K个最大元素
     */
    public int findKthLargest(int[] nums, int k) {
        // int len=nums.length;
        // if(len==0){
        //     return 0;
        // }
        // Arrays.sort(nums);
        // for(int i=len-1;i>=0;i--){
        //     if(k==(len-i)){
        //         return nums[i];
        //     }
        // }
        // return 0;

        // 堆排序
        // PriorityQueue<Integer> heap=new PriorityQueue<>((n1,n2)->n1-n2);
        // for(int n:nums){
        //     heap.add(n);
        //     if(heap.size()>k){
        //         heap.poll();
        //     }
        // }
        // return heap.poll();

        // 快速选择排序
        this.nums = nums;
        int len = nums.length;
        // 第k大的数即排好序后下表为 len-k
        return quickSelect(0, len - 1, len - k);
    }

    int[] nums;

    private void swap(int a, int b) {
        int temp = this.nums[a];
        this.nums[a] = this.nums[b];
        this.nums[b] = temp;
    }

    private int partition(int left, int right, int piovt_index) {
        // 每次将基准位置的值与起始值交换
        swap(left, piovt_index);
        piovt_index = left;
        int piovt = this.nums[piovt_index];
        while (left < right) {
            while (left < right && this.nums[left] <= piovt) {
                left++;
            }
            while (left < right && this.nums[right] > piovt) {
                right--;
            }
            if (left < right) {
                swap(left, right);
                left++;
                right--;
            }
        }
        if (this.nums[left] > piovt) {
            swap(left - 1, piovt_index);
            return left - 1;
        }
        swap(left, piovt_index);
        return left;
    }

    private int quickSelect(int left, int right, int k) {
        if (left == right) {
            return this.nums[left];
        }
        Random random_num = new Random();
        int piovt_index = left + random_num.nextInt(right - left + 1);
        piovt_index = partition(left, right, piovt_index);
        if (k == piovt_index) {
            return this.nums[k];
        } else if (k < piovt_index) {
            return quickSelect(left, piovt_index - 1, k);
        } else {
            return quickSelect(piovt_index + 1, right, k);
        }
    }

    /**
     * 221. 最大正方形
     */
    public int maximalSquare(char[][] matrix) {
        // 暴力求解,遍历所有1的点
        // int row=matrix.length;
        // int col=matrix[0].length;
        // int max=0;
        // for(int i=0;i<row;++i){
        //     for(int j=0;j<col;++j){
        //         if(matrix[i][j]=='1'){
        //             max=Math.max(max,1);
        //             int maxSide=Math.min(row-i,col-j);
        //             for(int m=1;m<maxSide;++m){
        //                 boolean flag=true;
        //                 if(matrix[i+m][j+m]=='0'){
        //                     break;
        //                 }
        //                 for(int n=0;n<m;++n){
        //                     if(matrix[i+m][j+n]=='0'||matrix[i+n][j+m]=='0'){
        //                         flag=false;
        //                         break;
        //                     }
        //                 }
        //                 if(flag){
        //                     max=Math.max(max,m+1);
        //                 }else{
        //                     break;
        //                 }
        //             }
        //         }
        //     }
        // }
        // return max*max;

        // 动态规划
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] dp = new int[row][col];
        int maxSide = 0;
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(Math.min(dp[i][j - 1], dp[i - 1][j - 1]), dp[i - 1][j]) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        }
        return maxSide * maxSide;
    }

    /**
     * 236. 二叉树的最近公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 递归,秒啊
        // if(root==p || root==q){
        //     return root;
        // }
        // if(root!=null){
        //     TreeNode leftNode=lowestCommonAncestor(root.left,p,q);
        //     TreeNode rightNode=lowestCommonAncestor(root.right,p,q);
        //     if(leftNode!=null && rightNode!=null){
        //         // 两个节点在两端,root为最近祖先
        //         return root;
        //     }else if(leftNode==null){
        //         // 两个都在右子树
        //         return rightNode;
        //     }else{
        //         // 两个都在左子树
        //         return leftNode;
        //     }
        // }
        // return null;

        // 存储父结点
        // dfs 记录所有节点的父节点信息
        dfs(root);
        // 从p节点和q节点向上寻找父结点
        while (p != null) {
            // 记录p访问过的节点
            visited.add(p.val);
            p = parent.get(p.val);
        }
        while (q != null) {
            if (!visited.add(q.val)) {
                return q;
            }
            q = parent.get(q.val);
        }
        return null;
    }

    // 哈希表记录父结点信息
    private Map<Integer, TreeNode> parent = new HashMap<>();
    // set记录访问父结点是的信息
    private Set<Integer> visited = new HashSet<>();

    private void dfs(TreeNode root) {
        if (root.left != null) {
            parent.put(root.left.val, root);
            dfs(root.left);
        }
        if (root.right != null) {
            parent.put(root.right.val, root);
            dfs(root.right);
        }
    }

    /**
     * 238. 除自身以外数组的乘积
     */
    public int[] productExceptSelf(int[] nums) {
        // int len=nums.length;
        // int[] dp=new int[len];
        // dp[0]=1;
        // for(int i=1;i<len;++i){
        //     dp[i]=dp[i-1]*nums[i-1];
        // }
        // int[] dp2=new int[len];
        // dp2[len-1]=1;
        // for(int i=len-2;i>=0;--i){
        //     dp2[i]=dp2[i+1]*nums[i+1];
        // }
        // int[] ret=new int[len];
        // for(int i=0;i<len;++i){
        //     ret[i]=dp[i]*dp2[i];
        // }
        // return ret;

        int len = nums.length;
        int[] ret = new int[len];
        ret[0] = 1;
        for (int i = 1; i < len; ++i) {
            ret[i] = ret[i - 1] * nums[i - 1];
        }
        int temp = 1;
        for (int i = len - 1; i >= 0; --i) {
            ret[i] = ret[i] * temp;
            temp *= nums[i];
        }
        return ret;
    }


}
