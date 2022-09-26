package com.dujc.leetcode;

import java.util.Stack;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Stack_20 {
    public static void main(String[] args) {

        System.out.println(isValid("]"));
    }

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        final char[] chars = s.toCharArray();
        for (char aChar : chars) {
            switch (aChar) {
                case '(':
                case '[':
                case '{':
                    stack.push(aChar);
                    break;

                case ')':
                    if (stack.isEmpty() || stack.pop() != '(') {
                        return false;
                    } else {
                        break;
                    }
                case ']':
                    if (stack.isEmpty() || stack.pop() != '[') {
                        return false;
                    } else {
                        break;
                    }
                case '}':
                    if (stack.isEmpty() || stack.pop() != '{') {
                        return false;
                    } else {
                        break;
                    }
            }
        }
        return stack.isEmpty();
    }
}
