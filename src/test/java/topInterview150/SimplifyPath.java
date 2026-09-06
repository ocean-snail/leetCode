package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * You are given an absolute path for a Unix-style file system, which always
 * begins with a slash '/'. Your task is to transform this absolute path into
 * its simplified canonical path.
 * 
 * The rules of a Unix-style file system are as follows:
 * 
 * A single period '.' represents the current directory.
 * A double period '..' represents the previous/parent directory.
 * Multiple consecutive slashes such as '//' and '///' are treated as a single
 * slash '/'.
 * Any sequence of periods that does not match the rules above should be treated
 * as a valid directory or file name. For example, '...' and '....' are valid
 * directory or file names.
 * The simplified canonical path should follow these rules:
 * 
 * The path must start with a single slash '/'.
 * Directories within the path must be separated by exactly one slash '/'.
 * The path must not end with a slash '/', unless it is the root directory.
 * The path must not have any single or double periods ('.' and '..') used to
 * denote current or parent directories.
 * Return the simplified canonical path.
 * 
 * 
 * ? Example 1:
 * 
 * Input: path = "/home/"
 * 
 * Output: "/home"
 * 
 * Explanation:
 * 
 * The trailing slash should be removed.
 * 
 * ? Example 2:
 * 
 * Input: path = "/home//foo/"
 * 
 * Output: "/home/foo"
 * 
 * Explanation:
 * 
 * Multiple consecutive slashes are replaced by a single one.
 * 
 * ? Example 3:
 * 
 * Input: path = "/home/user/Documents/../Pictures"
 * 
 * Output: "/home/user/Pictures"
 * 
 * Explanation:
 * 
 * A double period ".." refers to the directory up a level (the parent
 * directory).
 * 
 * ? Example 4:
 * 
 * Input: path = "/../"
 * 
 * Output: "/"
 * 
 * Explanation:
 * 
 * Going one level up from the root directory is not possible.
 * 
 * ? Example 5:
 * 
 * Input: path = "/.../a/../b/c/../d/./"
 * 
 * Output: "/.../b/d"
 * 
 * Explanation:
 * 
 * "..." is a valid name for a directory in this problem.
 * 
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= path.length <= 3000
 * path consists of English letters, digits, period '.', slash '/' or '_'.
 * path is a valid absolute Unix path.
 */
public class SimplifyPath {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Builds the canonical path with a single left-to-right scan and an
     * array-backed stack.
     *
     * * Time: O(n) - the two inner while loops together advance the index i across
     * each of the n
     * characters exactly once, and every segment is pushed at most once and removed
     * at most once.
     * * Space: O(n) - the segment array holds at most n / 2 references and the
     * StringBuilder holds
     * at most n characters.
     *
     * @param path an absolute Unix-style path that begins with '/'
     * @return the canonical path: a leading '/', single separators, and no trailing
     *         '/' unless
     *         the result is the root directory
     */
    public String simplifyPath(String path) {
        int n = path.length();
        String[] stack = new String[n / 2 + 1];
        int top = 0;
        int i = 0;
        while (i < n) {
            while (i < n && path.charAt(i) == '/') {
                i++;
            }
            int start = i;
            while (i < n && path.charAt(i) != '/') {
                i++;
            }
            if (start == i) {
                break;
            }
            String segment = path.substring(start, i);
            if (segment.equals(".")) {
                continue;
            }
            if (segment.equals("..")) {
                if (top > 0) {
                    top--;
                }
                continue;
            }
            stack[top++] = segment;
        }
        if (top == 0) {
            return "/";
        }
        StringBuilder canonical = new StringBuilder(n + 1);
        for (int k = 0; k < top; k++) {
            canonical.append('/').append(stack[k]);
        }
        return canonical.toString();
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("A single trailing slash is removed from the canonical path")
    void trailingSlash_isRemoved() {
        assertEquals("/home", simplifyPath("/home/"));
    }

    @Test
    @DisplayName("Consecutive slashes collapse into a single separator")
    void consecutiveSlashes_collapseToSingleSeparator() {
        assertEquals("/home/foo", simplifyPath("/home//foo/"));
    }

    @Test
    @DisplayName("A double period removes the directory that precedes it")
    void doubleDot_removesPrecedingDirectory() {
        assertEquals("/home/user/Pictures", simplifyPath("/home/user/Documents/../Pictures"));
    }

    @Test
    @DisplayName("A double period at the root is discarded and the result stays at the root")
    void doubleDotAtRoot_returnsRoot() {
        assertEquals("/", simplifyPath("/../"));
    }

    @Test
    @DisplayName("Three periods form a directory name while single and double periods do not")
    void threePeriods_treatedAsDirectoryName() {
        assertEquals("/.../b/d", simplifyPath("/.../a/../b/c/../d/./"));
    }

    @Test
    @DisplayName("The root path alone is already canonical")
    void rootOnly_returnsRoot() {
        assertEquals("/", simplifyPath("/"));
    }

    @Test
    @DisplayName("A path made only of slashes reduces to the root")
    void onlySlashes_returnsRoot() {
        assertEquals("/", simplifyPath("//////"));
    }

    @Test
    @DisplayName("More double periods than directories still stop at the root")
    void moreDoubleDotsThanDirectories_returnsRoot() {
        assertEquals("/", simplifyPath("/a/../../../"));
    }

    @Test
    @DisplayName("Names that merely start with periods are kept unchanged")
    void namesStartingWithPeriods_arePreserved() {
        assertEquals("/..hidden/.file/....", simplifyPath("/..hidden/.file/...."));
    }

    @Test
    @DisplayName("Four periods and three periods are ordinary names around an empty separator run")
    void fourAndThreePeriods_arePreserved() {
        assertEquals("/..../...", simplifyPath("/....//..././"));
    }

    @Test
    @DisplayName("Digits and underscores are ordinary name characters")
    void digitsAndUnderscores_arePreserved() {
        assertEquals("/a_1/c__", simplifyPath("/a_1/2b/../c__/"));
    }

    @Test
    @DisplayName("A path ending in a double period removes the last directory")
    void pathEndingWithDoubleDot_removesLastDirectory() {
        assertEquals("/", simplifyPath("/a/.."));
    }

    @Test
    @DisplayName("A path ending in a single period keeps the current directory")
    void pathEndingWithSinglePeriod_keepsCurrentDirectory() {
        assertEquals("/a", simplifyPath("/a/."));
    }
}