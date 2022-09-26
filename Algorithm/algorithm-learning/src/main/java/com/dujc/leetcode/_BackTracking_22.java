package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _BackTracking_22 {
    public static void main(String[] args) {
        System.out.println(generateParenthesis(8));

    }

    public static List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        backTracking(res, n, 0, 0, "");
        return res;
    }

    /**
     *  回溯生成
     * @param res  保存合法结果
     * @param n   左右括号各有多少个
     * @param left  左括号已使用多少个
     * @param right  右括号已使用多少个
     * @param s  截止当前这次调用，已经生成的括号串
     */
    private static void backTracking(List<String> res, int n, int left, int right, String s) {
        //如果右括号使用次数>左括号，肯定不符合，直接结束
        if (right > left)
            return;
        //如果左右括号都用完了，表示括号已经生成好了
        if (left == right && left == n) {
            res.add(s);
            return;
        }
        //只要还有左括号，就可以加左括号
        if (left < n) {
            backTracking(res, n, ++left, right, s + "(");
            //当递归结束后，需要把left使用数再减回来
            left--;
        }
        //只要还有右括号，就可以加右括号
        if (right < n) {
            backTracking(res, n, left, ++right, s + ")");
            //当递归结束后，需要把right使用数再减回来，不过这行代码是方法最后一行代码，right值也不会再被使用了，栈资源释放，所以可以不写
//            right--;
        }
    }
}
