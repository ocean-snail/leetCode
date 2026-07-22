package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Implement the RandomizedSet class:
 * 
 * RandomizedSet() Initializes the RandomizedSet object.
 * bool insert(int val) Inserts an item val into the set if not present. Returns
 * true if the item was not present, false otherwise.
 * bool remove(int val) Removes an item val from the set if present. Returns
 * true if the item was present, false otherwise.
 * int getRandom() Returns a random element from the current set of elements
 * (it's guaranteed that at least one element exists when this method is
 * called). Each element must have the same probability of being returned.
 * You must implement the functions of the class such that each function works
 * in average O(1) time complexity.
 * 
 * 
 * ? Example 1:
 * 
 * Input
 * ["RandomizedSet", "insert", "remove", "insert", "getRandom", "remove",
 * "insert", "getRandom"]
 * [[], [1], [2], [2], [], [1], [2], []]
 * Output
 * [null, true, false, true, 2, true, false, 2]
 * 
 * * Explanation
 * RandomizedSet randomizedSet = new RandomizedSet();
 * randomizedSet.insert(1); // Inserts 1 to the set. Returns true as 1 was
 * inserted successfully.
 * randomizedSet.remove(2); // Returns false as 2 does not exist in the set.
 * randomizedSet.insert(2); // Inserts 2 to the set, returns true. Set now
 * contains [1,2].
 * randomizedSet.getRandom(); // getRandom() should return either 1 or 2
 * randomly.
 * randomizedSet.remove(1); // Removes 1 from the set, returns true. Set now
 * contains [2].
 * randomizedSet.insert(2); // 2 was already in the set, so return false.
 * randomizedSet.getRandom(); // Since 2 is the only number in the set,
 * getRandom() will always return 2.
 * 
 * 
 * ! Constraints:
 * 
 * -231 <= val <= 231 - 1
 * At most 2 * 105 calls will be made to insert, remove, and getRandom.
 * There will be at least one element in the data structure when getRandom is
 * called.
 * 
 */

public class InsertDeleteGetRandomO1 {

    // ---------------------------------------------------------------- solution

    static final class RandomizedSet {
        private final Map<Integer, Integer> indexByVal;
        private final List<Integer> values;
        private final Random random;

        RandomizedSet() {
            this(new Random());
        }

        RandomizedSet(Random random) {
            this.indexByVal = new HashMap<>();
            this.values = new ArrayList<>();
            this.random = random;
        }

        public boolean insert(int val) {
            if (indexByVal.containsKey(val)) {
                return false;
            }
            indexByVal.put(val, values.size());
            return true;
        }

        public boolean remove(int val) {
            Integer idx = indexByVal.remove(val);
            if (idx == null) {
                return false;
            }
            int lastIndex = values.size() - 1;
            int lastVal = values.get(lastIndex);
            if (idx != lastIndex) {
                values.set(idx, lastVal);
                indexByVal.put(lastVal, idx);
            }
            values.remove(lastIndex);
            return true;
        }

        public int getRandom() {
            return values.get(random.nextInt(values.size()));
        }

        int size() {
            return values.size();
        }
    }

    // ------------------------------------------------------------------- tests

    @Test
    @DisplayName("LeetCode example sequence produces the documented outputs")
    void leetCodeExampleSequence() {
        RandomizedSet set = new RandomizedSet();
        assertTrue(set.insert(1));
        assertFalse(set.remove(2));
        assertTrue(set.insert(2));
        int first = set.getRandom();
        assertTrue(first == 1 || first == 2);
        assertTrue(set.remove(1));
        assertFalse(set.insert(2));
        assertEquals(2, set.getRandom());
    }

    @Test
    @DisplayName("insert returns true for a new value and false for a duplicate")
    void insertReportsNoveltyCorrectly() {
        RandomizedSet set = new RandomizedSet();
        assertTrue(set.insert(7));
        assertFalse(set.insert(7));
        assertEquals(1, set.size());
    }

    @Test
    @DisplayName("remove returns false when the value is absent and leaves the set unchanged")
    void removeAbsentValueIsNoOp() {
        RandomizedSet set = new RandomizedSet();
        set.insert(1);
        assertFalse(set.remove(99));
        assertEquals(1, set.size());
        assertEquals(1, set.getRandom());
    }

    @Test
    @DisplayName("remove of the last-indexed element does not corrupt the index map")
    void removeTailElement() {
        RandomizedSet set = new RandomizedSet();
        set.insert(10);
        set.insert(20);
        set.insert(30);
        assertTrue(set.remove(30));
        assertTrue(set.remove(10));
        assertEquals(1, set.size());
        assertEquals(20, set.getRandom());
    }

    @Test
    @DisplayName("removing a middle element keeps the swapped-in element removable")
    void removeMiddleThenSwappedInElement() {
        RandomizedSet set = new RandomizedSet();
        set.insert(1);
        set.insert(2);
        set.insert(3);
        set.insert(4);
        assertTrue(set.remove(2));
        assertTrue(set.remove(4));
        assertEquals(2, set.size());
        assertTrue(set.remove(1));
        assertTrue(set.remove(3));
        assertEquals(0, set.size());
    }

    @Test
    @DisplayName("a value can be re-inserted after being removed")
    void reinsertAfterRemove() {
        RandomizedSet set = new RandomizedSet();
        set.insert(5);
        assertTrue(set.remove(5));
        assertTrue(set.insert(5));
        assertEquals(5, set.getRandom());
    }

    @Test
    @DisplayName("removing every element empties the set")
    void removeAllElements() {
        RandomizedSet set = new RandomizedSet();
        for (int i = 0; i < 5; i++) {
            assertTrue(set.insert(i));
        }
        for (int i = 0; i < 5; i++) {
            assertTrue(set.remove(i));
        }
        assertEquals(0, set.size());
        assertFalse(set.remove(0));
    }

    @Test
    @DisplayName("Integer.MIN_VALUE and MAX_VALUE are ordinary members")
    void handlesIntBoundaryValues() {
        RandomizedSet set = new RandomizedSet();
        assertTrue(set.insert(Integer.MIN_VALUE));
        assertTrue(set.insert(Integer.MAX_VALUE));
        assertFalse(set.insert(Integer.MIN_VALUE));
        assertTrue(set.remove(Integer.MIN_VALUE));
        assertEquals(Integer.MAX_VALUE, set.getRandom());
    }

    @Test
    @DisplayName("negative and zero values behave like any other value")
    void handlesNegativeAndZero() {
        RandomizedSet set = new RandomizedSet();
        assertTrue(set.insert(0));
        assertTrue(set.insert(-1));
        assertFalse(set.insert(0));
        assertTrue(set.remove(0));
        assertEquals(-1, set.getRandom());
    }

    @Test
    @DisplayName("getRandom on a single-element set always returns that element")
    void getRandomSingleElement() {
        RandomizedSet set = new RandomizedSet();
        set.insert(42);
        for (int i = 0; i < 100; i++) {
            assertEquals(42, set.getRandom());
        }
    }

    @Test
    @DisplayName("getRandom only ever returns current members")
    void getRandomReturnsOnlyMembers() {
        RandomizedSet set = new RandomizedSet(new Random(1));
        Set<Integer> expected = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            set.insert(i);
            expected.add(i);
        }
        for (int i = 0; i < 10; i++) {
            set.remove(i);
            expected.remove(i);
        }
        for (int i = 0; i < 1_000; i++) {
            assertTrue(expected.contains(set.getRandom()));
        }
    }

    @Test
    @DisplayName("getRandom is approximately uniform over three elements")
    void getRandomIsUniform() {
        RandomizedSet set = new RandomizedSet(new Random(42));
        set.insert(1);
        set.insert(2);
        set.insert(3);

        int trials = 60_000;
        int[] counts = new int[4];
        for (int i = 0; i < trials; i++) {
            counts[set.getRandom()]++;
        }
        int expected = trials / 3;
        int tolerance = expected / 4;
        for (int v = 1; v <= 3; v++) {
            assertTrue(Math.abs(counts[v] - expected) < tolerance,
                    "value " + v + " sampled " + counts[v] + " times");
        }
    }

    @Test
    @DisplayName("randomised stress test agrees with java.util.HashSet as a reference oracle")
    void stressTestAgainstHashSet() {
        RandomizedSet set = new RandomizedSet(new Random(7));
        Set<Integer> reference = new HashSet<>();
        Random driver = new Random(9);

        for (int i = 0; i < 200_000; i++) {
            int val = driver.nextInt(50);
            if (driver.nextBoolean()) {
                assertEquals(reference.add(val), set.insert(val));
            } else {
                assertEquals(reference.remove(val), set.remove(val));
            }
            assertEquals(reference.size(), set.size());
            if (!reference.isEmpty()) {
                assertTrue(reference.contains(set.getRandom()));
            }
        }
    }
}