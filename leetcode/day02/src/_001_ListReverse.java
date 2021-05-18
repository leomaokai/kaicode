/**
 * 链表反转
 * 迭代,递归
 */
public class _001_ListReverse {

    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val, ListNode node) {
            this.val = val;
            this.next = node;
        }
    }

    // 迭代
    public static ListNode iterate(ListNode head) {
        ListNode pre = null;
        ListNode next;
        ListNode curr = head;
        while (curr != null) {
            next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    // 递归
    public static ListNode recursion(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = recursion(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }


    public static void main(String[] args) {
        ListNode node5 = new ListNode(5, null);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode node1 = new ListNode(1, node2);
        printList(node1);
//        ListNode res = iterate(node1);
        ListNode res = recursion(node1);
        printList(res);

    }

    public static void printList(ListNode node) {
        while (node != null) {
            System.out.print(node.val + " ");
            node = node.next;
        }
        System.out.println();
    }
}
