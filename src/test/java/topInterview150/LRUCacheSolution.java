package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Design a data structure that follows the constraints of a Least Recently Used
 * (LRU) cache.
 * 
 * Implement the LRUCache class:
 * 
 * LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
 * int get(int key) Return the value of the key if the key exists, otherwise
 * return -1.
 * void put(int key, int value) Update the value of the key if the key exists.
 * Otherwise, add the key-value pair to the cache. If the number of keys exceeds
 * the capacity from this operation, evict the least recently used key.
 * The functions get and put must each run in O(1) average time complexity.
 * 
 * 
 * ? Example 1:
 * 
 * Input
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * Output
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 * 
 * Explanation
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // cache is {1=1}
 * lRUCache.put(2, 2); // cache is {1=1, 2=2}
 * lRUCache.get(1); // return 1
 * lRUCache.put(3, 3); // LRU key was 2, evicts key 2, cache is {1=1, 3=3}
 * lRUCache.get(2); // returns -1 (not found)
 * lRUCache.put(4, 4); // LRU key was 1, evicts key 1, cache is {4=4, 3=3}
 * lRUCache.get(1); // return -1 (not found)
 * lRUCache.get(3); // return 3
 * lRUCache.get(4); // return 4
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= capacity <= 3000
 * 0 <= key <= 104
 * 0 <= value <= 105
 * At most 2 * 105 calls will be made to get and put.
 */
public class LRUCacheSolution {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * LRU cache with O(1) average get and put.
     * Slots 0 and 1 are sentinels; slots 2..capacity+1 hold entries.
     */
    static final class LRUCache {

        private static final int HEAD = 0;
        private static final int TAIL = 1;
        private static final int FIRST_ENTRY_SLOT = 2;

        private final int capacity;
        private final int[] keys;
        private final int[] values;
        private final int[] prev;
        private final int[] next;
        private final Map<Integer, Integer> slotByKey;

        /**
         * Allocates every slot up front so that no put ever allocates list storage.
         *
         * * Time: O(capacity) - four int arrays of length capacity + 2 are zero-filled
         * once.
         * * Space: O(capacity) - the four arrays; the map grows to at most capacity
         * entries later.
         *
         * @param capacity maximum number of keys held at the same time (at least 1)
         */
        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.keys = new int[capacity + 2];
            this.values = new int[capacity + 2];
            this.prev = new int[capacity + 2];
            this.next = new int[capacity + 2];
            this.slotByKey = new HashMap<>();
            next[HEAD] = TAIL;
            prev[TAIL] = HEAD;
        }

        /**
         * Returns the stored value and marks the key as most recently used.
         *
         * * Time: O(1) average, O(log n) worst - one HashMap lookup (a treeified bucket
         * is a red-black tree)
         * plus a constant number of array writes in moveToFront.
         * * Space: O(1) - no allocation.
         *
         * @param key key to look up
         * @return the value mapped to key, or -1 when key is absent
         */
        public int get(int key) {
            Integer slot = slotByKey.get(key);
            if (slot == null) {
                return -1;
            }
            moveToFront(slot);
            return values[slot];
        }

        /**
         * Inserts or updates key, evicting the least recently used key when a new key
         * arrives at full capacity.
         *
         * * Time: O(1) average, O(log n) worst - at most one HashMap get, one remove
         * and
         * one put,
         * plus a constant number of array writes.
         * * Space: O(1) - reuses a preallocated slot; the map holds at most capacity
         * entries.
         *
         * @param key   key to insert or update
         * @param value value to store
         */
        public void put(int key, int value) {
            Integer existing = slotByKey.get(key);
            if (existing != null) {
                values[existing] = value;
                moveToFront(existing);
                return;
            }
            int slot;
            if (slotByKey.size() == capacity) {
                slot = prev[TAIL];
                unlink(slot);
                slotByKey.remove(keys[slot]);
            } else {
                slot = FIRST_ENTRY_SLOT + slotByKey.size();
            }
            keys[slot] = key;
            values[slot] = value;
            linkAfterHead(slot);
            slotByKey.put(key, slot);
        }

        /**
         * * Time: O(1) - one unlink and one link.
         * * Space: O(1) - no allocation.
         *
         * @param slot slot currently in the list
         */
        private void moveToFront(int slot) {
            unlink(slot);
            linkAfterHead(slot);
        }

        /**
         * * Time: O(1) - two array writes.
         * * Space: O(1) - no allocation.
         *
         * @param slot slot currently in the list
         */
        private void unlink(int slot) {
            next[prev[slot]] = next[slot];
            prev[next[slot]] = prev[slot];
        }

        /**
         * * Time: O(1) - four array writes.
         * * Space: O(1) - no allocation.
         *
         * @param slot slot not currently in the list
         */
        private void linkAfterHead(int slot) {
            int oldFirst = next[HEAD];
            prev[slot] = HEAD;
            next[slot] = oldFirst;
            prev[oldFirst] = slot;
            next[HEAD] = slot;
        }
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("LeetCode example 1 produces 1, -1, -1, 3, 4 for its five get calls")
    void leetCodeExample_matchesExpectedOutputs() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        assertEquals(1, cache.get(1));
        cache.put(3, 3);
        assertEquals(-1, cache.get(2));
        cache.put(4, 4);
        assertEquals(-1, cache.get(1));
        assertEquals(3, cache.get(3));
        assertEquals(4, cache.get(4));
    }

    @Test
    @DisplayName("A capacity-1 cache evicts the only key whenever a different key is put")
    void capacityOne_keepsOnlyLatestKey() {
        LRUCache cache = new LRUCache(1);
        cache.put(2, 1);
        assertEquals(1, cache.get(2));
        cache.put(3, 2);
        assertEquals(-1, cache.get(2));
        assertEquals(2, cache.get(3));
    }

    @Test
    @DisplayName("get on a cache that has never received a put returns -1")
    void getOnEmptyCache_returnsMinusOne() {
        LRUCache cache = new LRUCache(3);
        assertEquals(-1, cache.get(0));
    }

    @Test
    @DisplayName("Key 0 with value 0 is stored and returned as 0, not confused with a missing key")
    void zeroKeyAndZeroValue_returnsZero() {
        LRUCache cache = new LRUCache(1);
        cache.put(0, 0);
        assertEquals(0, cache.get(0));
    }

    @Test
    @DisplayName("Updating an existing key in a full cache overwrites its value and evicts nothing")
    void updateExistingKeyWhenFull_evictsNothing() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(2, 20);
        assertEquals(1, cache.get(1));
        assertEquals(20, cache.get(2));
    }

    @Test
    @DisplayName("Updating an existing key makes it most recently used, so the other key is evicted next")
    void updateExistingKey_refreshesRecency() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(1, 10);
        cache.put(3, 3);
        assertEquals(10, cache.get(1));
        assertEquals(-1, cache.get(2));
        assertEquals(3, cache.get(3));
    }

    @Test
    @DisplayName("A get for a missing key leaves the recency order unchanged")
    void getMiss_keepsEvictionOrder() {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        assertEquals(-1, cache.get(5));
        cache.put(3, 3);
        assertEquals(-1, cache.get(1));
        assertEquals(2, cache.get(2));
        assertEquals(3, cache.get(3));
    }

    @Test
    @DisplayName("Two consecutive gets of the front key keep the list intact, so the next eviction removes the true LRU key")
    void repeatedGetOfFrontKey_evictsTrueLeastRecentKey() {
        LRUCache cache = new LRUCache(2);
        cache.put(2, 0);
        cache.put(0, 1);
        assertEquals(0, cache.get(2));
        assertEquals(0, cache.get(2));
        assertEquals(1, cache.get(0));
        cache.put(1, 5);
        assertEquals(1, cache.get(0));
        assertEquals(-1, cache.get(2));
        assertEquals(5, cache.get(1));
    }

    @Test
    @DisplayName("After ten puts into capacity 3, only keys 7, 8, 9 remain and key 7 is evicted by the next put")
    void tenPutsIntoCapacityThree_keepsLastThreeKeys() {
        LRUCache cache = new LRUCache(3);
        for (int key = 0; key < 10; key++) {
            cache.put(key, key * 100);
        }
        assertEquals(-1, cache.get(6));
        assertEquals(700, cache.get(7));
        assertEquals(800, cache.get(8));
        assertEquals(900, cache.get(9));
        cache.put(10, 1000);
        assertEquals(-1, cache.get(7));
        assertEquals(800, cache.get(8));
        assertEquals(1000, cache.get(10));
    }
}