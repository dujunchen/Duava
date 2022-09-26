package com.dujc.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _HashTable_217 {
    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 1}));
    }
    public static boolean containsDuplicate(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            if(countMap.containsKey(num)){
                countMap.put(num, countMap.get(num)+1);
            }else{
                countMap.put(num, 1);
            }
        }
        for (Integer value : countMap.values()) {
            if(value>1){
                return true;
            }
        }
        return false;
    }

}
