package com.dujc.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author dujc
 * @date 2022/8/23
 */
public class _Queue_225 {
    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        System.out.println(myStack.top()); // 返回 2
        System.out.println(myStack.pop()); // 返回 2
        System.out.println(myStack.empty()); // 返回 False
    }

    //使用一个队列实现
    private static class MyStack {
        Queue<Integer> queue = new LinkedList<Integer>();

        public MyStack() {
        }

        public void push(int x) {
            final int size = queue.size();
            queue.add(x);
            for (int i = 0; i < size; i++) {
                queue.add(queue.poll());
            }
        }

        public int pop() {
            return queue.poll();
        }

        public int top() {
            return queue.peek();
        }

        public boolean empty() {
            return queue.isEmpty();
        }
    }
}


