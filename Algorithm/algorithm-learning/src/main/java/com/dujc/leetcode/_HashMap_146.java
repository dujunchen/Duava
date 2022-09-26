package com.dujc.leetcode;

import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

public class _HashMap_146 {
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);

        lruCache.set(2, 1);
        lruCache.set(1, 1);
        lruCache.set(2, 3);
        lruCache.set(4, 1);
        System.out.println(lruCache.get(1));
        System.out.println(lruCache.get(2));
    }
}

class LRUCache {
    static class Node {
        int key;
        int value;
        Node pre;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    Map<Integer, Node> cache;
    int capacity;
    Node dummy;
    Node tail;

    public LRUCache(int capacity) {
        assert capacity > 0 : "capacity must gt 0!";
        cache = new HashMap<>(capacity);
        this.capacity = capacity;
        dummy = new Node(-1, -1);
    }


    public int get(int key) {
        if (cache.containsKey(key)) {
            final Node node = cache.get(key);
            final int value = node.value;
            moveToHead(node);
            return value;
        } else {
            return -1;
        }

    }

    private void moveToHead(Node node) {
        node.pre.next = node.next;
        if (node == tail) {
            tail = node.pre;
        } else {
            node.next.pre = node.pre;
        }

        addToHead(node);
    }

    private void addToHead(Node node) {
        if (dummy.next == null) {
            dummy.next = node;
            node.pre = dummy;
            tail = node;
        } else {
            final Node temp = dummy.next;
            dummy.next = node;
            node.pre = dummy;
            node.next = temp;
            temp.pre = node;
        }
    }

    public void set(int key, int value) {
        if (cache.containsKey(key)) {
            final Node node = cache.get(key);
            node.value = value;
            moveToHead(node);
        } else {
            if (cache.size() >= capacity) {
                int removedKey = removeTail();
                cache.remove(removedKey);
            }
            final Node node = new Node(key, value);
            addToHead(node);
            cache.put(key, node);
        }
    }

    private int removeTail() {
        if (tail != null) {
            final int key = tail.key;
            tail.pre.next = null;
            tail = tail.pre;
            return key;
        }
        return -1;
    }
}

