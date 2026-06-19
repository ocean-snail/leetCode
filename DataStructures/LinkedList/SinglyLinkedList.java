public class SinglyLinkedList {
    private Node head;
    private Node tail;
    private int size;

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public void addFirst(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }

        size++;
    }

    public void addLast(int value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    public int removeFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }

        int removedValue = head.data;
        head = head.next;

        if (head == null) {
            tail = null;
        }

        size--;
        return removedValue;
    }

    public int removeLast() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }

        if (head == tail) {
            int removedValue = head.data;
            head = null;
            tail = null;
            size--;
            return removedValue;
        }

        Node current = head;

        while (current.next != tail) {
            current = current.next;
        }

        int removedValue = tail.data;
        current.next = null;
        tail = current;

        size--;
        return removedValue;
    }

    public boolean contains(int value) {
        Node current = head;

        while (current != null) {
            if (current.data == value) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void print() {
        Node current = head;

        while (current != null) {
            System.out.print(current.data);

            if (current.next != null) {
                System.out.print(" -> ");
            }

            current = current.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();

        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        list.print(); // 10 -> 20 -> 30

        list.addFirst(5);
        list.print(); // 5 -> 10 -> 20 -> 30

        System.out.println(list.get(2));      // 20
        System.out.println(list.contains(30)); // true
        System.out.println(list.size());       // 4

        list.removeFirst();
        list.print(); // 10 -> 20 -> 30

        list.removeLast();
        list.print(); // 10 -> 20
    }
}