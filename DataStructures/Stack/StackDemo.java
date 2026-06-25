import java.util.EmptyStackException;

public class StackDemo {

    public static void main(String[] args) {
        MyStack<Integer> stack = new MyStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println(stack.peek()); // 30
        System.out.println(stack.pop()); // 30
        System.out.println(stack.pop()); // 20

        System.out.println(stack.size()); // 1
        System.out.println(stack.isEmpty()); // false

        System.out.println(stack.pop()); // 10
        System.out.println(stack.isEmpty()); // true
    }

    static class MyStack<T> {

        private static final int DEFAULT_CAPACITY = 10;

        private Object[] elements;
        private int size;

        public MyStack() {
            elements = new Object[DEFAULT_CAPACITY];
            size = 0;
        }

        public void push(T value) {
            ensureCapacity();
            elements[size] = value;
            size++;
        }

        @SuppressWarnings("unchecked")
        public T pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }

            size--;

            T value = (T) elements[size];
            elements[size] = null; // avoid memory leak

            return value;
        }

        @SuppressWarnings("unchecked")
        public T peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }

            return (T) elements[size - 1];
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        private void ensureCapacity() {
            if (size < elements.length) {
                return;
            }

            Object[] newElements = new Object[elements.length * 2];

            for (int i = 0; i < elements.length; i++) {
                newElements[i] = elements[i];
            }

            elements = newElements;
        }
    }
}