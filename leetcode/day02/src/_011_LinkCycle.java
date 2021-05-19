import java.util.HashSet;
import java.util.Set;

/**
 * 环形链表
 * 给定一个链表,判断链表中是否有环
 * 存在环返回 true 否则 false
 * 快慢指针
 */
public class _011_LinkCycle {

    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        ListNode node5 = new ListNode(5, null);
        ListNode node4 = new ListNode(5, node5);
        ListNode node3 = new ListNode(5, node4);
        ListNode node2 = new ListNode(5, node3);
        ListNode node1 = new ListNode(1, node2);
//        node5.next = node3;
        System.out.println(hasCycle1(node1));
        System.out.println(hasCycle2(node1));
    }

    // set
    public static boolean hasCycle1(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (!set.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    // 快慢指针
    public static boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode quick = head.next;
        while (slow != quick) {
            if (quick == null && quick.next == null) {
                return false;
            }
            quick = quick.next.next;
            slow = slow.next;
        }
        return true;
    }
}
