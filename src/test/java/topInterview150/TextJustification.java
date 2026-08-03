package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Given an array of strings words and a width maxWidth, format the text such
 * that each line has exactly maxWidth characters and is fully (left and right)
 * justified.
 * 
 * You should pack your words in a greedy approach; that is, pack as many words
 * as you can in each line. Pad extra spaces ' ' when necessary so that each
 * line has exactly maxWidth characters.
 * 
 * Extra spaces between words should be distributed as evenly as possible. If
 * the number of spaces on a line does not divide evenly between words, the
 * empty slots on the left will be assigned more spaces than the slots on the
 * right.
 * 
 * For the last line of text, it should be left-justified, and no extra space is
 * inserted between words.
 * 
 * * Note:
 * 
 * A word is defined as a character sequence consisting of non-space characters
 * only.
 * Each word's length is guaranteed to be greater than 0 and not exceed
 * maxWidth.
 * The input array words contains at least one word.
 * 
 * 
 * ? Example 1:
 * 
 * Input: words = ["This", "is", "an", "example", "of", "text",
 * "justification."], maxWidth = 16
 * Output:
 * [
 * "This is an",
 * "example of text",
 * "justification. "
 * ]
 * 
 * ? Example 2:
 * 
 * Input: words = ["What","must","be","acknowledgment","shall","be"], maxWidth =
 * 16
 * Output:
 * [
 * "What must be",
 * "acknowledgment ",
 * "shall be "
 * ]
 * Explanation: Note that the last line is "shall be " instead of "shall be",
 * because the last line must be left-justified instead of fully-justified.
 * Note that the second line is also left-justified because it contains only one
 * word.
 * 
 * ? Example 3:
 * 
 * Input: words =
 * ["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"],
 * maxWidth = 20
 * Output:
 * [
 * "Science is what we",
 * "understand well",
 * "enough to explain to",
 * "a computer. Art is",
 * "everything else we",
 * "do "
 * ]
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= words.length <= 300
 * 1 <= words[i].length <= 20
 * words[i] consists of only English letters and symbols.
 * 1 <= maxWidth <= 100
 * words[i].length <= maxWidth
 */
public class TextJustification {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Formats words into fully justified lines of exactly maxWidth characters.
     * * Time: O(n * maxWidth) - each word is read once during packing, and every
     * output character is appended exactly once across all lines.
     * * Space: O(n * maxWidth) - the returned list holds one string of length
     * maxWidth per line, and the number of lines is at most n.
     *
     * @param words    the input words, each non-empty and no longer than maxWidth
     * @param maxWidth the exact character count of every output line
     * @return the justified lines in order
     */
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> lines = new ArrayList<>();
        int start = 0;

        while (start < words.length) {
            int lettersInLine = words[start].length();
            int end = start + 1;

            while (end < words.length
                    && lettersInLine + 1 + words[end].length() <= maxWidth) {
                lettersInLine += 1 + words[end].length();
                end++;
            }

            int wordCount = end - start;
            boolean isLastLine = end == words.length;

            if (wordCount == 1 || isLastLine) {
                lines.add(buildLeftJustified(words, start, end, maxWidth));
            } else {
                lines.add(buildFullyJustified(words, start, end, maxWidth));
            }

            start = end;
        }

        return lines;
    }

    /**
     * Joins words[start, end) with exactly one space, then pads the right side.
     * * Time: O(maxWidth) - every character of the produced line is written once.
     * * Space: O(maxWidth) - the StringBuilder holds one line.
     *
     * @param words    the input words
     * @param start    inclusive index of the first word on this line
     * @param end      exclusive index just past the last word on this line
     * @param maxWidth the exact character count of the produced line
     * @return the left-justified line
     */
    private String buildLeftJustified(String[] words, int start, int end, int maxWidth) {
        StringBuilder line = new StringBuilder(words[start]);

        for (int i = start + 1; i < end; i++) {
            line.append(' ').append(words[i]);
        }

        while (line.length() < maxWidth) {
            line.append(' ');
        }

        return line.toString();
    }

    /**
     * Distributes the remaining spaces across gaps, left gaps taking the surplus.
     * * Time: O(maxWidth) - every character of the produced line is written once.
     * * Space: O(maxWidth) - the StringBuilder holds one line.
     *
     * @param words    the input words
     * @param start    inclusive index of the first word on this line
     * @param end      exclusive index just past the last word on this line
     * @param maxWidth the exact character count of the produced line
     * @return the fully justified line
     */
    private String buildFullyJustified(String[] words, int start, int end, int maxWidth) {
        int letters = 0;
        for (int i = start; i < end; i++) {
            letters += words[i].length();
        }

        int gaps = end - start - 1;
        int totalSpaces = maxWidth - letters;

        int baseSpaces = totalSpaces / gaps;

        int surplus = totalSpaces % gaps;

        StringBuilder line = new StringBuilder(words[start]);
        for (int i = start + 1; i < end; i++) {
            int gapIndex = i - start - 1;
            int width = baseSpaces + (gapIndex < surplus ? 1 : 0);
            line.append(" ".repeat(width)).append(words[i]);
        }

        return line.toString();
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("LeetCode example 1: mixed gap widths with left-heavy surplus")
    void leetCodeExampleOne_matchesExpectedLines() {
        String[] words = { "This", "is", "an", "example", "of", "text", "justification." };
        List<String> expected = List.of(
                "This    is    an",
                "example  of text",
                "justification.  ");
        assertEquals(expected, fullJustify(words, 16));
    }

    @Test
    @DisplayName("LeetCode example 2: single-word line is left-justified, not stretched")
    void singleWordLine_isLeftJustified() {
        String[] words = { "What", "must", "be", "acknowledgment", "shall", "be" };
        List<String> expected = List.of(
                "What   must   be",
                "acknowledgment  ",
                "shall be        ");
        assertEquals(expected, fullJustify(words, 16));
    }

    @Test
    @DisplayName("LeetCode example 3: six lines at width 20")
    void leetCodeExampleThree_matchesExpectedLines() {
        String[] words = { "Science", "is", "what", "we", "understand", "well",
                "enough", "to", "explain", "to", "a", "computer.", "Art", "is",
                "everything", "else", "we", "do" };
        List<String> expected = List.of(
                "Science  is  what we",
                "understand      well",
                "enough to explain to",
                "a  computer.  Art is",
                "everything  else  we",
                "do                  ");
        assertEquals(expected, fullJustify(words, 20));
    }

    @Test
    @DisplayName("Single word input produces one left-justified padded line")
    void singleWordInput_producesOnePaddedLine() {
        assertEquals(List.of("a         "), fullJustify(new String[] { "a" }, 10));
    }

    @Test
    @DisplayName("Word length equal to maxWidth produces a line with no padding")
    void wordFillsWidthExactly_producesUnpaddedLine() {
        assertEquals(List.of("abcde"), fullJustify(new String[] { "abcde" }, 5));
    }

    @Test
    @DisplayName("Two words filling the width exactly need no extra spaces")
    void twoWordsFillWidthExactly_singleSpaceBetween() {
        assertEquals(List.of("ab cd"), fullJustify(new String[] { "ab", "cd" }, 5));
    }

    @Test
    @DisplayName("Surplus of one space goes to the leftmost gap")
    void surplusOfOne_goesToLeftmostGap() {
        String[] words = { "a", "b", "c", "tail" };
        List<String> actual = fullJustify(words, 8);
        assertEquals("a   b  c", actual.get(0));
    }

    @Test
    @DisplayName("Multi-word last line keeps single spaces and pads on the right")
    void lastLine_usesSingleSpacesAndRightPadding() {
        String[] words = { "aaaaaaaa", "b", "c", "d" };
        assertEquals(List.of("aaaaaaaa", "b c d   "), fullJustify(words, 8));
    }

    @Test
    @DisplayName("Every produced line has length exactly maxWidth")
    void allLines_haveExactWidth() {
        String[] words = { "Science", "is", "what", "we", "understand", "well",
                "enough", "to", "explain", "to", "a", "computer." };
        for (String line : fullJustify(words, 20)) {
            assertEquals(20, line.length());
        }
    }

    @Test
    @DisplayName("Lines contain the original words in order when spaces are collapsed")
    void collapsingSpaces_recoversOriginalWordSequence() {
        String[] words = { "Science", "is", "what", "we", "understand", "well",
                "enough", "to", "explain", "to", "a", "computer." };
        List<String> lines = fullJustify(words, 20);

        List<String> recovered = new ArrayList<>();
        for (String line : lines) {
            for (String token : line.trim().split("\\s+")) {
                recovered.add(token);
            }
        }
        assertEquals(Arrays.asList(words), recovered);
    }
}