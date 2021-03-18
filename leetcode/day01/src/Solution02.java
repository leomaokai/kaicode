public class Solution02 {

    /**
     * 14. 最长公共前缀
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        int len = strs[0].length();
        int p = 0;
        boolean flag;

        for (int i = 0; i < len; ++i) {
            char temp = strs[0].charAt(i);
            p++;
            flag = false;
            for (String str : strs) {
                if (str.length() - 1 >= i && temp == str.charAt(i)) {
                    continue;
                } else {
                    p--;
                    flag = true;
                    break;
                }
            }
            if (flag) break;
        }
        return strs[0].substring(0, p);
    }

    /**
     * 20. 有效的括号
     */
    public boolean isValid(String s) {
        int len = s.length();
        char c = s.charAt(0);
        if (len % 2 != 0 || c == ')' || c == ']' || c == '}') {
            return false;
        }
        char[] chars = s.toCharArray();
        char[] array = new char[len];
        int index = 0;
        for (int i = 0; i < len; ++i) {
            char t1 = chars[i];
            if (index == 0) {
                array[index++] = t1;
            } else {
                char t2 = array[index - 1];
                if ((t2 == '(' && t1 == ')') || (t2 == '[' && t1 == ']') || (t2 == '{' && t1 == '}')) {
                    index--;
                } else {
                    array[index++] = t1;
                }
            }
        }
        if (index == 0) {
            return true;
        }
        return false;
    }

    /**
     * 21. 合并两个有序链表
     */
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode tail = head;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }
        tail.next = l1 == null ? l2 : l1;
        return head.next;

    }

    /**
     * 26. 删除有序数组中的重复项
     */
    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }
        int i = 0;
        for (int j = 1; j < len; ++j) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }

    /**
     * 27. 移除元素
     */
    public int removeElement(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }
        return i;
    }

    /**
     * 28. 实现 strStr()
     */
    public int strStr(String haystack, String needle) {
        if (needle.equals("")) {
            return 0;
        }
        int len1 = haystack.length();
        int len2 = needle.length();
        if (len2 > len1) {
            return -1;
        }

        // 字串比较
        for (int i = 0; i < len1 - len2 + 1; ++i) {
            if (haystack.substring(i, i + len2).equals(needle)) {
                return i;
            }
        }
        return -1;
        // KMP
        // int ret=-1;
        // char [] chars1=haystack.toCharArray();
        // char [] chars2=needle.toCharArray();
        // for(int i=0;i<len1;i++){
        //     int temi=i;
        //     int temp=0;
        //     for(int j=0;j<len2;j++){
        //         if(i<len1 && chars1[i]==chars2[j]){
        //             ret=i;
        //             i++;
        //             temp++;
        //             continue;
        //         }else{
        //             break;
        //         }
        //     }
        //     if(temp == len2){
        //         return ret - len2+1;
        //     }else{
        //         i=temi;
        //     }
        // }
        // return -1;
    }

}
