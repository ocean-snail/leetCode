import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CandyExample {
    static class Solution {
        public int candy(int[] ratings) {
            int n = ratings.length;
            int[] candies = new int[n];
            Arrays.fill(candies, 1); // rule 1: everyone gets at least 1

            // Pass 1 (left → right): if I'm rated higher than my LEFT neighbor,
            // I must have more candy than them.
            for (int i = 1; i < n; i++) {
                if (ratings[i] > ratings[i - 1]) {
                    candies[i] = candies[i - 1] + 1;
                }
            }

            // Pass 2 (right → left): if I'm rated higher than my RIGHT neighbor,
            // I must have more candy than them. max() keeps pass-1 result valid.
            int total = candies[n - 1];
            for (int i = n - 2; i >= 0; i--) {
                if (ratings[i] > ratings[i + 1]) {
                    candies[i] = Math.max(candies[i], candies[i + 1] + 1);
                }
                total += candies[i];
            }
            return total;
        }
    }

    private final Solution solution = new Solution();

    @ParameterizedTest(name = "ratings={0} → expected={1}")
    @MethodSource("cases")
    void candy(int[] ratings, int expected) {
        assertEquals(expected, solution.candy(ratings));
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new int[] { 1, 0, 2 }, 5), // example 1: valley shape
                Arguments.of(new int[] { 1, 2, 2 }, 4), // example 2: equal neighbors
                Arguments.of(new int[] { 5 }, 1), // single child
                Arguments.of(new int[] { 1, 2 }, 3), // strictly increasing
                Arguments.of(new int[] { 5, 4, 3, 2, 1 }, 15), // strictly decreasing: 5+4+3+2+1
                Arguments.of(new int[] { 3, 3, 3 }, 3), // all equal → all get 1
                Arguments.of(new int[] { 1, 3, 2, 2, 1 }, 7), // peak then plateau then drop
                Arguments.of(new int[] { 1, 2, 87, 87, 87, 2, 1 }, 13) // long plateau in the middle
        );
    }

    @Test
    void largeDescendingInput_runsInLinearTime() {
        int n = 20_000;
        int[] ratings = new int[n];
        for (int i = 0; i < n; i++)
            ratings[i] = n - i;
        // sum 1..20000 = n*(n+1)/2
        assertEquals(n * (n + 1) / 2, solution.candy(ratings));
    }

}
