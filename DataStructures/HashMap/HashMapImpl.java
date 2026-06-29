
public class HashMapImpl<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Node<K, V>[] table;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public HashMapImpl() {

        this.capacity = DEFAULT_CAPACITY;
        this.table = new Node[capacity];
        this.size = 0;
    }

    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private int hash(K key) {
        if (key == null)
            return 0;
        int h = key.hashCode();
        return Math.abs(h) % capacity;
    }

    public void put(K key, V value) {
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        int index = hash(key);
        Node<K, V> head = table[index];

        Node<K, V> curr = head;
        while (curr != null) {
            if (isKeyEqual(curr.key, key)) {
                curr.value = value;
                return;
            }
            curr = curr.next;
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = head;
        table[index] = newNode;
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        Node<K, V> curr = table[index];

        while (curr != null) {
            if (isKeyEqual(curr.key, key)) {
                return curr.value;
            }
            curr = curr.next;
        }
        return null;
    }

    public boolean remove(K key) {
        int index = hash(key);
        Node<K, V> curr = table[index];
        Node<K, V> prev = null;

        while (curr != null) {
            if (isKeyEqual(curr.key, key)) {
                if (prev == null) {
                    table[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        capacity *= 2;
        Node<K, V>[] newTable = new Node[capacity];

        for (Node<K, V> head : table) {
            Node<K, V> curr = head;
            while (curr != null) {
                Node<K, V> next = curr.next;
                int newIndex = Math.abs(curr.key.hashCode()) % capacity;
                curr.next = newTable[newIndex];
                newTable[newIndex] = curr;
                curr = next;
            }
        }
        table = newTable;
    }

    private boolean isKeyEqual(K a, K b) {
        return (a == null && b == null) || (a != null && a.equals(b));
    }
}