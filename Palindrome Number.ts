// Palindrome Number

function isPalindrome(num: number): boolean {
  if (num < 0) return false;

  const strNum = num.toString();
  const reversedStrNum = strNum.split("").reverse().join("");

  return strNum === reversedStrNum;
}

console.log(isPalindrome(234));
console.log(isPalindrome(23432));
