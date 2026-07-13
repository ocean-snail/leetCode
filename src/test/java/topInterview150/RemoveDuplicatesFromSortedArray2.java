package topInterview150;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class RemoveDuplicatesFromSortedArray2 {

    // ---------- Solution ----------
    int removeDuplicates(int[] nums) {
        int write = 0;
        for (int value : nums) {
            if (write < 2 || nums[write - 2] != value) {
                nums[write] = value;
                write++;
            }
        }
        return write;
    }

    // ---------- Tests ----------

    @Test
    void example1_tripleCollapsesToDouble() {
        int[] nums = { 1, 1, 1, 2, 2, 3 };
        int k = removeDuplicates(nums);
        assertEquals(5, k);
        assertArrayEquals(new int[] { 1, 1, 2, 2, 3 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void example2_mixedRunLengths() {
        int[] nums = { 0, 0, 1, 1, 1, 1, 2, 3, 3 };
        int k = removeDuplicates(nums);
        assertEquals(7, k);
        assertArrayEquals(new int[] { 0, 0, 1, 1, 2, 3, 3 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void singleElement_returnsOne() {
        int[] nums = { 7 };
        int k = removeDuplicates(nums);
        assertEquals(1, k);
        assertArrayEquals(new int[] { 7 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void twoElements_bothKeptEvenIfEqual() {
        int[] nums = { 4, 4 };
        int k = removeDuplicates(nums);
        assertEquals(2, k);
        assertArrayEquals(new int[] { 4, 4 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void allSameValue_collapsesToTwo() {
        int[] nums = { 5, 5, 5, 5, 5, 5 };
        int k = removeDuplicates(nums);
        assertEquals(2, k);
        assertArrayEquals(new int[] { 5, 5 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void noDuplicates_arrayUnchanged() {
        int[] nums = { 1, 2, 3, 4 };
        int k = removeDuplicates(nums);
        assertEquals(4, k);
        assertArrayEquals(new int[] { 1, 2, 3, 4 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void exactlyTwoOfEach_nothingRemoved() {
        int[] nums = { 1, 1, 2, 2, 3, 3 };
        int k = removeDuplicates(nums);
        assertEquals(6, k);
        assertArrayEquals(new int[] { 1, 1, 2, 2, 3, 3 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void boundaryValues_constraintExtremes() {
        int[] nums = { -10_000, -10_000, -10_000, 0, 10_000, 10_000, 10_000 };
        int k = removeDuplicates(nums);
        assertEquals(5, k);
        assertArrayEquals(new int[] { -10_000, -10_000, 0, 10_000, 10_000 },
                Arrays.copyOfRange(nums, 0, k));
    }
}