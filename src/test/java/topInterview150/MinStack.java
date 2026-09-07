package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Design a stack that supports push, pop, top, and retrieving the minimum
 * element in constant time.
 * 
 * Implement the MinStack class:
 * 
 * MinStack() initializes the stack object.
 * void push(int value) pushes the element value onto the stack.
 * void pop() removes the element on the top of the stack.
 * int top() gets the top element of the stack.
 * int getMin() retrieves the minimum element in the stack.
 * You must implement a solution with O(1) time complexity for each function.
 * 
 * 
 * 
 * ? Example 1:
 * 
 * Input
 * ["MinStack","push","push","push","getMin","pop","top","getMin"]
 * [[],[-2],[0],[-3],[],[],[],[]]
 * 
 * Output
 * [null,null,null,null,-3,null,0,-2]
 * 
 * Explanation
 * MinStack minStack = new MinStack();
 * minStack.push(-2);
 * minStack.push(0);
 * minStack.push(-3);
 * minStack.getMin(); // return -3
 * minStack.pop();
 * minStack.top(); // return 0
 * minStack.getMin(); // return -2
 * 
 * 
 * ! Constraints:
 * 
 * -231 <= val <= 231 - 1
 * Methods pop, top and getMin operations will always be called on non-empty
 * stacks.
 * At most 3 * 104 calls will be made to push, pop, top, and getMin.
 */
public class MinStack {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    private static final int INITIAL_CAPACITY = 16;

    private int[] values;

    private int[] mins;

    private int size;

    /**
     * Creates an empty stack.
     *
     * * Time: O(1) - allocates two arrays whose length is a compile-time constant.
     * * Space: O(1) - the allocated length does not depend on how many calls will
     * follow.
     */
    public MinStack() {
        values = new int[INITIAL_CAPACITY];
        mins = new int[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * * Time: O(1) amortized - best and average O(1) (two array writes and one
     * increment); worst O(n)
     * on the single call that finds both arrays full and copies all n elements.
     * * Space: O(1) per call - two int slots, so O(n) in total for n stored
     * elements.
     *
     * @param value the element to place on top of the stack
     */
    public void push(int value) {
        if (size == values.length) {
            grow();
        }
        values[size] = value;
        mins[size] = size == 0 ? value : Math.min(mins[size - 1], value);
        size++;
    }

    /**
     * * Time: O(1) - decrements one counter.
     * * Space: O(1) - no allocation; the slots above size are overwritten by the
     * next
     * push.
     */
    public void pop() {
        size--;
    }

    /**
     * * Time: O(1) - one array read.
     * * Space: O(1) - no allocation.
     *
     * @return the element most recently pushed and not yet popped
     */
    public int top() {
        return values[size - 1];
    }

    /**
     * * Time: O(1) - one array read, because the minimum was computed during push.
     * * Space: O(1) - no allocation.
     *
     * @return the smallest element currently stored
     */
    public int getMin() {
        return mins[size - 1];
    }

    /**
     * Doubles the capacity of both arrays.
     *
     * * Time: O(n) - copies the n stored elements into the new arrays.
     * * Space: O(n) - allocates arrays of twice the current length.
     */
    private void grow() {
        int newCapacity = values.length * 2;
        values = Arrays.copyOf(values, newCapacity);
        mins = Arrays.copyOf(mins, newCapacity);
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("The example sequence from the problem returns -3, then 0 for top, then -2 for getMin")
    void leetcodeExample1_returnsMinusThreeThenZeroThenMinusTwo() {
        MinStack stack = new MinStack();
        stack.push(-2);
        stack.push(0);
        stack.push(-3);
        assertEquals(-3, stack.getMin());
        stack.pop();
        assertEquals(0, stack.top());
        assertEquals(-2, stack.getMin());
    }

    @Test
    @DisplayName("When the minimum value is pushed twice, popping one copy leaves the minimum unchanged")
    void duplicateMinimumPushedTwice_minimumSurvivesFirstPop() {
        MinStack stack = new MinStack();
        stack.push(0);
        stack.push(-1);
        stack.push(-1);
        assertEquals(-1, stack.getMin());
        stack.pop();
        assertEquals(-1, stack.getMin());
        stack.pop();
        assertEquals(0, stack.getMin());
        assertEquals(0, stack.top());
    }

    @Test
    @DisplayName("Integer.MIN_VALUE and Integer.MAX_VALUE are stored and reported without arithmetic overflow")
    void integerBounds_minimumTracksExtremeValuesWithoutOverflow() {
        MinStack stack = new MinStack();
        stack.push(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, stack.getMin());
        stack.push(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, stack.getMin());
        assertEquals(Integer.MIN_VALUE, stack.top());
        stack.push(0);
        assertEquals(Integer.MIN_VALUE, stack.getMin());
        stack.pop();
        stack.pop();
        assertEquals(Integer.MAX_VALUE, stack.getMin());
    }

    @Test
    @DisplayName("With strictly increasing pushes the minimum stays at the first element while top changes")
    void strictlyIncreasingPushes_minimumStaysFirstElement() {
        MinStack stack = new MinStack();
        for (int value = 5; value <= 9; value++) {
            stack.push(value);
        }
        assertEquals(5, stack.getMin());
        assertEquals(9, stack.top());
        stack.pop();
        stack.pop();
        assertEquals(5, stack.getMin());
        assertEquals(7, stack.top());
    }

    @Test
    @DisplayName("With strictly decreasing pushes the minimum equals the top and rises again after pops")
    void strictlyDecreasingPushes_minimumEqualsTop() {
        MinStack stack = new MinStack();
        for (int value = 9; value >= 5; value--) {
            stack.push(value);
        }
        assertEquals(5, stack.getMin());
        assertEquals(5, stack.top());
        stack.pop();
        stack.pop();
        assertEquals(7, stack.getMin());
        assertEquals(7, stack.top());
    }

    @Test
    @DisplayName("Pushing 100 values crosses the initial capacity of 16 and keeps every stored value")
    void pushesBeyondInitialCapacity_preserveTopAndMinimum() {
        MinStack stack = new MinStack();
        for (int i = 0; i < 100; i++) {
            stack.push(99 - i);
        }
        assertEquals(0, stack.top());
        assertEquals(0, stack.getMin());
        for (int i = 0; i < 60; i++) {
            stack.pop();
        }
        assertEquals(60, stack.top());
        assertEquals(60, stack.getMin());
    }

    @Test
    @DisplayName("A stack holding one element reports that element as both top and minimum")
    void singleElement_topEqualsMinimum() {
        MinStack stack = new MinStack();
        stack.push(7);
        assertEquals(7, stack.top());
        assertEquals(7, stack.getMin());
    }
}
