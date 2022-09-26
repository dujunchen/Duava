package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _String_151 {
    public static void main(String[] args) {
        System.out.println(reverseWords(" the sky is blue  "));
    }

    public static String reverseWords(String s) {
        char[] cs = s.toCharArray();
        int len = cs.length;
        int fast = 0;
        int slow = 0;
        int pre = -1;
        //去除多余的空格
        while (fast < len) {
            if (cs[fast] == ' ') {
                pre = fast;
                fast++;
                continue;
            }
            if (slow > 0 && pre >= 0 && cs[pre] == ' ') {
                cs[slow] = ' ';
                slow++;
            }
            cs[slow] = cs[fast];
            pre = fast;
            fast++;
            slow++;
        }
        //现将整个字符串进行翻转
        int left = 0;
        int right = slow - 1;
        reverse(cs, left, right);

        //再对每一个单词进行翻转
        left = 0;
        right = 0;
        while (right < slow) {
            if (cs[right] == ' ') {
                reverse(cs, left, right - 1);
                right++;
                left = right;
            } else {
                right++;
            }
        }
        //最后一个单词需要手动翻转一下
        reverse(cs, left, right - 1);

        return new String(cs, 0, slow);
    }

    //翻转
    private static void reverse(char[] cs, int left, int right) {
        while (left < right) {
            char temp = cs[left];
            cs[left] = cs[right];
            cs[right] = temp;
            left++;
            right--;
        }
    }
}
