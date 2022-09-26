package com.dujc.leetcode;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _String_Hash {
    public static void main(String[] args) {
        System.out.println(cmp("abcdef", "cd"));
    }

    public static boolean cmp(String a, String b) {
        final int P = 131;
        final char[] charA = a.toCharArray();
        final int len1 = charA.length;
        long[] pre = new long[len1];
        long[] p = new long[len1];
        for (int i = 1; i < len1; i++) {
            pre[i] = pre[i - 1] * P + (charA[i] - 'a');
        }
        p[0] = 1;
        for (int i = 1; i < len1; i++) {
            p[i] = p[i - 1] * P;
        }

        final char[] charB = b.toCharArray();
        final int len2 = charB.length;
        long hashB = 0;
        for (int i = 1; i < len2; i++) {
            hashB = hashB * P + (charB[i]-'a');
        }

        for (int i = 1; i <len1-len2; i++) {
            if(pre[i + len2 -1]-pre[i-1]*p[i + len2 -i] == hashB){
                return true;
            }
        }
        return  false;



    }


}
