public class DoublyLinkedList<T> {

    private static class Node<T> {
        private T data;
        private Node<T> prev;
        private Node<T> next;

        private Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public DoublyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }

        size++;
    }

    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    public void add(int index, T data) {
        checkPositionIndex(index);

        if (index == 0) {
            addFirst(data);
            return;
        }

        if (index == size) {
            addLast(data);
            return;
        }

        Node<T> current = getNode(index);
        Node<T> previous = current.prev;
        Node<T> newNode = new Node<>(data);

        newNode.prev = previous;
        newNode.next = current;

        previous.next = newNode;
        current.prev = newNode;

        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty.");
        }

        T removedData = head.data;

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }

        size--;

        return removedData;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new IllegalStateException("List is empty.");
        }

        T removedData = tail.data;

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        size--;

        return removedData;
    }

    public T removeAt(int index) {
        checkElementIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        if (index == size - 1) {
            return removeLast();
        }

        Node<T> current = getNode(index);

        Node<T> previous = current.prev;
        Node<T> nextNode = current.next;

        previous.next = nextNode;
        nextNode.prev = previous;

        size--;

        return current.data;
    }

    public boolean remove(T data) {
        Node<T> current = head;

        while (current != null) {
            if (equals(data, current.data)) {
                if (current == head) {
                    removeFirst();
                } else if (current == tail) {
                    removeLast();
                } else {
                    Node<T> previous = current.prev;
                    Node<T> nextNode = current.next;

                    previous.next = nextNode;
                    nextNode.prev = previous;

                    size--;
                }

                return true;
            }

            current = current.next;
        }

        return false;
    }

    public T get(int index) {
        checkElementIndex(index);
        return getNode(index).data;
    }

    public int indexOf(T data) {
        Node<T> current = head;
        int index = 0;

        while (current != null) {
            if (equals(data, current.data)) {
                return index;
            }

            current = current.next;
            index++;
        }

        return -1;
    }

    public boolean contains(T data) {
        return indexOf(data) != -1;
    }

    public void clear() {
        Node<T> current = head;

        while (current != null) {
            Node<T> nextNode = current.next;

            current.prev = null;
            current.next = null;
            current.data = null;

            current = nextNode;
        }

        head = null;
        tail = null;
        size = 0;
    }

    public void printForward() {
        Node<T> current = head;

        System.out.print("Forward: ");

        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }

        System.out.println();
    }

    public void printBackward() {
        Node<T> current = tail;

        System.out.print("Backward: ");

        while (current != null) {
            System.out.print(current.data + " ");
            current = current.prev;
        }

        System.out.println();
    }

    private Node<T> getNode(int index) {
        Node<T> current;

        if (index < size / 2) {
            current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;

            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }

        return current;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private boolean equals(T a, T b) {
        if (a == null) {
            return b == null;
        }

        return a.equals(b);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node<T> current = head;

        sb.append("[");

        while (current != null) {
            sb.append(current.data);

            if (current.next != null) {
                sb.append(", ");
            }

            current = current.next;
        }

        sb.append("]");

        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        list.addFirst(5);

        list.printForward();
        list.printBackward();

        list.add(2, 15);
        System.out.println(list);

        list.removeFirst();
        list.removeLast();
        list.remove(Integer.valueOf(15));

        System.out.println(list);

        System.out.println("Value at index 1: " + list.get(1));
        System.out.println("Contains 20: " + list.contains(20));
        System.out.println("Size: " + list.size());

        list.clear();
        System.out.println("After clear: " + list);
    }
}