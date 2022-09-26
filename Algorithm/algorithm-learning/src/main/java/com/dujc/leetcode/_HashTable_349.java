package com.dujc.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _HashTable_349 {
    public static void main(String[] args) {
    }
    public int[] intersection(int[] nums1, int[] nums2) {
        int[] hashtable = new int[1001];
        for(int n : nums1){
            hashtable[n] = 1;
        }
        Set<Integer> res = new HashSet<>();
        for(int n : nums2){
            if(hashtable[n] == 1){
                res.add(n);
            }
        }
        return  res.stream().mapToInt(i->i).toArray();

    }

}
