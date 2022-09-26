package com.dujc.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Heap_215 {
    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{10, 9, 7, 12, 16, 8, 5}, 4));
    }

    public static int findKthLargest(int[] nums, int k) {
        final int length = nums.length;
        if (length < 2) {
            return nums[0];
        }
        List<Integer> heap = new ArrayList<>();
        for (int num : nums) {
            heap.add(num);
        }
        for (int i = (length - 1 - 1) / 2; i >= 0; i--) {
            heapify(heap, i);
        }
        for (int i = 0; i < k - 1; i++) {
            pop(heap);
        }

        return pop(heap);

    }

    private static int pop(List<Integer> heap) {
        final int pop = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        heapify(heap, 0);
        return pop;
    }

    private static void heapify(List<Integer> heap, int i) {
        int cur = i;
        while (cur < heap.size()) {
            final int rc = heapifyInternal(heap, cur);
            if (rc == -1) return;
            cur = rc;
        }
    }


    //堆化操作
    private static int heapifyInternal(List<Integer> heap, int i) {
        int maxIndex = -1;
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;

        //左节点存在
        if (leftIndex < heap.size()) {
            //右节点存在
            if (rightIndex < heap.size()) {
                if (heap.get(leftIndex).compareTo(heap.get(rightIndex)) >= 0) {
                    maxIndex = leftIndex;
                } else {
                    maxIndex = rightIndex;
                }
            } else {
                maxIndex = leftIndex;
            }
        }
        if (maxIndex == -1)
            return -1;
        //父节点和子节点较大者比较，如果比子节点小，则交换
        if (heap.get(i).compareTo(heap.get(maxIndex)) < 0) {
            int temp = heap.get(i);
            heap.set(i, heap.get(maxIndex));
            heap.set(maxIndex, temp);
            return maxIndex;
        } else {
            return -1;
        }

    }
}
