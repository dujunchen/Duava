package com.dujc.leetcode;

import java.util.Stack;

/**
 * @author dujc
 * @date 2022/08/22
 */
public class _Stack_232 {
    public static void main(String[] args) {
        MyQueue myQueue = new MyQueue();
        myQueue.push(1); // queue is: [1]
        myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
        System.out.println(myQueue.peek()); // return 1
        System.out.println(myQueue.pop()); // return 1, queue is [2]
        System.out.println(myQueue.empty()); // return false
        myQueue.push(3);
        myQueue.push(4);
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());

    }

    private static class MyQueue {
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        public MyQueue() {

        }

        public void push(int x) {
            stack1.push(x);
        }

        public int pop() {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            final Integer pop = stack2.pop();
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            return pop;
        }

        public int peek() {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            final Integer peek = stack2.peek();
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            return peek;
        }

        public boolean empty() {
            return stack1.isEmpty();
        }
    }

}
