public class LikedCycleExample {

    private LikedCycleExample() {
    }

    public static boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}

class ListNode {
    int value;
    ListNode next;

    ListNode(int value) {
        this.value = value;
    }

}
