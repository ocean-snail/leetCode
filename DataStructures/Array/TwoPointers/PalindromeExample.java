public class PalindromeExample {
    // * Time - O(n) | Space - O(1)
    public static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;

        // * Time - O(n)
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("racecar")); // true
        System.out.println(isPalindrome("hello")); // false
        System.out.println(isPalindrome("")); // true
        System.out.println(isPalindrome("q")); // true
    }
}
