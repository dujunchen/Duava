package com.dujc.lintcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dujc
 * @date 2022/09/24
 */
public class _HashMap_460 {
    public static void main(String[] args) {
        LFUCache lfuCache = new LFUCache(2);
        lfuCache.put(1, 1);
        lfuCache.put(2, 2);
        System.out.println(lfuCache.get(1));
        lfuCache.put(3, 3);
        System.out.println(lfuCache.get(2));
        System.out.println(lfuCache.get(3));
        lfuCache.put(4, 4);
        System.out.println(lfuCache.get(1));
        System.out.println(lfuCache.get(3));
        System.out.println(lfuCache.get(4));
    }
}

class LFUCache {

    static class Node {
        int freq;
        int key;
        int value;
        Node pre;
        Node next;

        public Node() {
        }

        public Node(int freq, int key, int value) {
            this.freq = freq;
            this.key = key;
            this.value = value;
        }
    }

    static class LRUList {
        Node dummyHead;
        Node dummyTail;

        public LRUList() {
            dummyHead = new Node();
            dummyTail = new Node();
            dummyHead.next = dummyTail;
            dummyTail.pre = dummyHead;
        }

        public void remove(Node node) {
            node.pre.next = node.next;
            node.next.pre = node.pre;
        }

        public void addToHead(Node node) {
            final Node temp = dummyHead.next;
            dummyHead.next = node;
            node.pre = dummyHead;
            node.next = temp;
            temp.pre = node;
        }

        public int removeTail() {
            final Node tail = dummyTail.pre;
            if (tail == dummyHead) {
                return -1;
            }
            tail.pre.next = dummyTail;
            dummyTail.pre = tail.pre;
            return tail.key;
        }
    }

    Map<Integer, Node> cache;
    Map<Integer, LRUList> freqMap;
    int capacity;
    int minFreq;

    public LFUCache(int capacity) {
        assert capacity > 0 : "capacity must gt 0!";
        cache = new HashMap<>(capacity);
        this.capacity = capacity;
        freqMap = new HashMap<>();
        minFreq = 0;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            final Node node = cache.get(key);
            int oldFreq = node.freq;
            final LRUList oldLRU = freqMap.get(oldFreq);
            oldLRU.remove(node);
            //LRU 空了，需要从freqMap删除
            if (oldLRU.dummyHead.next == oldLRU.dummyTail) {
                freqMap.remove(oldFreq);
                if (oldFreq == minFreq) {
                    minFreq++;
                }
            }

            node.freq = oldFreq + 1;
            freqMap.computeIfAbsent(node.freq, k -> new LRUList()).addToHead(node);
            return node.value;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            final Node node = cache.get(key);
            int oldFreq = node.freq;
            final LRUList oldLRU = freqMap.get(oldFreq);
            oldLRU.remove(node);
            //LRU 空了，需要从freqMap删除
            if (oldLRU.dummyHead.next == oldLRU.dummyTail) {
                freqMap.remove(oldFreq);
                if (oldFreq == minFreq) {
                    minFreq++;
                }
            }
            node.freq = oldFreq + 1;
            node.value = value;
            freqMap.computeIfAbsent(node.freq, k -> new LRUList()).addToHead(node);
        } else {
            if (cache.size() >= capacity) {
                final LRUList lruList = freqMap.get(minFreq);
                int removedKey = lruList.removeTail();
                //LRU 空了，需要从freqMap删除
                if (lruList.dummyHead.next == lruList.dummyTail) {
                    freqMap.remove(minFreq);
                }
                cache.remove(removedKey);
            }
            Node node = new Node(1, key, value);
            cache.put(key, node);
            freqMap.computeIfAbsent(node.freq, k -> new LRUList()).addToHead(node);
            minFreq = 1;
        }
    }
}
